package com.rick.sys.cache;

import com.rick.sys.VO.DeptVO;
import com.rick.sys.entity.SysDept;
import com.sun.org.apache.xpath.internal.operations.Bool;
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
public class CacheAspect {


    //声明一个容器
    private Map<String,Object> CACHE_POOL = new HashMap<>();

    //声明切面
    private static final String POINTCUT_DEPT_UPDATE = "execution(* com.rick.sys.service.impl.SysDeptServiceImpl.updateById(..))";
    private static final String POINTCUT_DEPT_REMOVE = "execution(* com.rick.sys.service.impl.SysDeptServiceImpl.removeById(..))";
    private static final String POINTCUT_DEPT_GET = "execution(* com.rick.sys.service.impl.SysDeptServiceImpl.getOne(..))";

    private static final String POINTCUT_PROFIX = "dept:";

    @Around(value = POINTCUT_DEPT_GET)
    private Object cacheDeptGet (ProceedingJoinPoint joinPoint) throws Throwable {
        Object object = joinPoint.getArgs()[0];
        Object o2 = CACHE_POOL.get(POINTCUT_PROFIX + object);
        if (o2 != null) {
            return o2;
        } else {
            SysDept proceed = (SysDept) joinPoint.proceed();
            CACHE_POOL.put(POINTCUT_PROFIX + proceed.getId(),proceed);
            return proceed;
        }
    }


    @Around(value = POINTCUT_DEPT_UPDATE)
    private Object cacheDeptUpdate (ProceedingJoinPoint joinPoint) throws Throwable {
        DeptVO vo = (DeptVO) joinPoint.getArgs()[0];
        Boolean succes = (Boolean) joinPoint.proceed();
        if (succes) {
            SysDept o2 = (SysDept)CACHE_POOL.get(POINTCUT_PROFIX + vo.getId());
            if (null == o2) {
                o2 = new SysDept();
                BeanUtils.copyProperties(vo,o2);
                CACHE_POOL.put(POINTCUT_PROFIX + o2.getId(),o2);
            }
        }
        return succes;
    }



    @Around(value = POINTCUT_DEPT_REMOVE)
    private Object cacheDeptRemove (ProceedingJoinPoint joinPoint) throws Throwable {
        Integer id = (Integer)joinPoint.getArgs()[0];
        Boolean success = (Boolean) joinPoint.proceed();
        if (success) {
            CACHE_POOL.remove(POINTCUT_PROFIX+id);
        }
        return success;
    }
}
