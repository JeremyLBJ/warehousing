package com.rick.sys.cache;

import com.rick.sys.VO.CustomerVO;
import com.rick.sys.entity.BusCustomer;
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
public class CustomerAspect {

    private Log log =  LogFactory.getLog(CacheAspect.class);


    //声明一个容器
    private Map<String,Object> CACHE_POOL = new HashMap<>();

    //声明切面
    private static final String POINTCUT_CUSTOMER_UPDATE = "execution(* com.rick.sys.service.impl.BusCustomerServiceImpl.updateById(..))";
    private static final String POINTCUT_CUSTOMER_REMOVE = "execution(* com.rick.sys.service.impl.BusCustomerServiceImpl.removeById(..))";
    private static final String POINTCUT_CUSTOMER_GET = "execution(* com.rick.sys.service.impl.BusCustomerServiceImpl.getOne(..))";
    private static final String POINTCUT_CUSTOMER_SAVE = "execution(* com.rick.sys.service.impl.BusCustomerServiceImpl.save(..))";
    private static final String POINTCUT_CUSTOMER_REMOVEIDS = "execution(* com.rick.sys.service.impl.BusCustomerServiceImpl.removeByIds(..))";
   
    private static final String POINTCUT_PROFIX = "customer:";


    @Around(value = POINTCUT_CUSTOMER_GET)
    private Object cacheCUSTOMERGet (ProceedingJoinPoint joinPoint) throws Throwable {
        Object object = joinPoint.getArgs()[0];
        Object o2 = CACHE_POOL.get(POINTCUT_PROFIX + object);
        if (o2 != null) {
            return o2;
        } else {
            BusCustomer proceed = (BusCustomer) joinPoint.proceed();
            log.info("查询客户信息");
            CACHE_POOL.put(POINTCUT_PROFIX + proceed.getId(),proceed);
            return proceed;
        }
    }

    @Around(value = POINTCUT_CUSTOMER_UPDATE)
    private Object cacheCustomerUpdate (ProceedingJoinPoint joinPoint) throws Throwable {
        CustomerVO vo = (CustomerVO) joinPoint.getArgs()[0];
        Boolean succes = (Boolean) joinPoint.proceed();
        if (succes) {
            log.info("部门从缓存中获取数据");
            BusCustomer o2 = (BusCustomer)CACHE_POOL.get(POINTCUT_PROFIX + vo.getId());
            if (null == o2) {
                o2 = new BusCustomer();
                BeanUtils.copyProperties(vo,o2);
                log.info("部门从数据库中获取数据,并设置缓存");
                CACHE_POOL.put(POINTCUT_PROFIX + o2.getId(),o2);
            }
        }
        return succes;
    }


}
