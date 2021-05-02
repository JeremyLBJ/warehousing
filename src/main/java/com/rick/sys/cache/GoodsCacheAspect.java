package com.rick.sys.cache;

import com.rick.sys.VO.DeptVO;
import com.rick.sys.VO.GoodsVO;
import com.rick.sys.entity.BusGoods;
import com.rick.sys.entity.SysDept;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@EnableAspectJAutoProxy
public class GoodsCacheAspect {


    private Log log =  LogFactory.getLog(CacheAspect.class);


    //声明一个容器
    private Map<String,Object> CACHE_POOL = new HashMap<>();

    //声明切面
    private static final String POINTCUT_GOODS_UPDATE = "execution(* com.rick.sys.service.impl.BusGoodsServiceImpl.updateById(..))";
    private static final String POINTCUT_GOODS_REMOVE = "execution(* com.rick.sys.service.impl.BusGoodsServiceImpl.removeById(..))";
    private static final String POINTCUT_GOODS_GET = "execution(* com.rick.sys.service.impl.BusGoodsServiceImpl.getOne(..))";
    private static final String POINTCUT_GOODS_SAVE = "execution(* com.rick.sys.service.impl.BusGoodsServiceImpl.save(..))";

    private static final String POINTCUT_PROFIX = "goods:";

    @Around(value = POINTCUT_GOODS_GET)
    private Object cacheDeptGet (ProceedingJoinPoint joinPoint) throws Throwable {
        Object object = joinPoint.getArgs()[0];
        Object o2 = CACHE_POOL.get(POINTCUT_PROFIX + object);
        if (o2 != null) {
            return o2;
        } else {
            BusGoods proceed = (BusGoods) joinPoint.proceed();
            log.info("查询商品信息");
            CACHE_POOL.put(POINTCUT_PROFIX + proceed.getId(),proceed);
            return proceed;
        }
    }


    @Around(value = POINTCUT_GOODS_UPDATE)
    private Object cacheDeptUpdate (ProceedingJoinPoint joinPoint) throws Throwable {
        GoodsVO vo = (GoodsVO) joinPoint.getArgs()[0];
        Boolean succes = (Boolean) joinPoint.proceed();
        if (succes) {
            log.info("商品信息从缓存中获取数据");
            BusGoods o2 = (BusGoods)CACHE_POOL.get(POINTCUT_PROFIX + vo.getId());
            if (null == o2) {
                o2 = new BusGoods();
                BeanUtils.copyProperties(vo,o2);
                log.info("商品信息从数据库中获取数据,并设置缓存");
                CACHE_POOL.put(POINTCUT_PROFIX + o2.getId(),o2);
            }
        }
        return succes;
    }



    @Around(value = POINTCUT_GOODS_REMOVE)
    private Object cacheDeptRemove (ProceedingJoinPoint joinPoint) throws Throwable {
        Integer id = (Integer)joinPoint.getArgs()[0];
        Boolean success = (Boolean) joinPoint.proceed();
        if (success) {
            log.info("删除商品信息");
            CACHE_POOL.remove(POINTCUT_PROFIX+id);
        }
        return success;
    }

    @Around(value = POINTCUT_GOODS_SAVE)
    private Object cacheDeptSave (ProceedingJoinPoint joinPoint) throws Throwable {
        BusGoods goods = (BusGoods)joinPoint.getArgs()[0];
        Boolean success = (Boolean) joinPoint.proceed();
        if (success) {
            log.info("添加商品信息");
            CACHE_POOL.put(POINTCUT_PROFIX+goods.getId(),goods);
        }
        return success;
    }




}
