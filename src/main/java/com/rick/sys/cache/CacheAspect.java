package com.rick.sys.cache;

import com.rick.sys.VO.DeptVO;
import com.rick.sys.VO.UserVO;
import com.rick.sys.entity.SysDept;
import com.rick.sys.entity.SysUser;
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
public class CacheAspect {

    private Log log =  LogFactory.getLog(CacheAspect.class);


    //声明一个容器
    private Map<String,Object> CACHE_POOL = new HashMap<>();

    //声明切面
    private static final String POINTCUT_DEPT_UPDATE = "execution(* com.rick.sys.service.impl.SysDeptServiceImpl.updateById(..))";
    private static final String POINTCUT_DEPT_REMOVE = "execution(* com.rick.sys.service.impl.SysDeptServiceImpl.removeById(..))";
    private static final String POINTCUT_DEPT_GET = "execution(* com.rick.sys.service.impl.SysDeptServiceImpl.getOne(..))";
    private static final String POINTCUT_DEPT_SAVE = "execution(* com.rick.sys.service.impl.SysDeptServiceImpl.save(..))";

    private static final String POINTCUT_PROFIX = "dept:";

    @Around(value = POINTCUT_DEPT_GET)
    private Object cacheDeptGet (ProceedingJoinPoint joinPoint) throws Throwable {
        Object object = joinPoint.getArgs()[0];
        Object o2 = CACHE_POOL.get(POINTCUT_PROFIX + object);
        if (o2 != null) {
            return o2;
        } else {
            SysDept proceed = (SysDept) joinPoint.proceed();
            log.info("查询部门信息");
            CACHE_POOL.put(POINTCUT_PROFIX + proceed.getId(),proceed);
            return proceed;
        }
    }


    @Around(value = POINTCUT_DEPT_UPDATE)
    private Object cacheDeptUpdate (ProceedingJoinPoint joinPoint) throws Throwable {
        DeptVO vo = (DeptVO) joinPoint.getArgs()[0];
        Boolean succes = (Boolean) joinPoint.proceed();
        if (succes) {
            log.info("部门从缓存中获取数据");
            SysDept o2 = (SysDept)CACHE_POOL.get(POINTCUT_PROFIX + vo.getId());
            if (null == o2) {
                o2 = new SysDept();
                BeanUtils.copyProperties(vo,o2);
                log.info("部门从数据库中获取数据,并设置缓存");
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
            log.info("删除部门信息");
            CACHE_POOL.remove(POINTCUT_PROFIX+id);
        }
        return success;
    }

    @Around(value = POINTCUT_DEPT_SAVE)
    private Object cacheDeptSave (ProceedingJoinPoint joinPoint) throws Throwable {
        SysDept dept = (SysDept)joinPoint.getArgs()[0];
        Boolean success = (Boolean) joinPoint.proceed();
        if (success) {
            log.info("添加部门信息");
            CACHE_POOL.put(POINTCUT_PROFIX+dept.getId(),dept);
        }
        return success;
    }



    //声明切面
    private static final String POINTCUT_USER_UPDATE = "execution(* com.rick.sys.service.impl.SysUserServiceImpl.updateById(..))";
    private static final String POINTCUT_USER_REMOVE = "execution(* com.rick.sys.service.impl.SysUserServiceImpl.removeById(..))";
    private static final String POINTCUT_USER_GET = "execution(* com.rick.sys.service.impl.SysUserServiceImpl.getOne(..))";
    private static final String POINTCUT_USER_SAVE = "execution(* com.rick.sys.service.impl.SysUserServiceImpl.save(..))";

    private static final String POINTCUT_PROFIX_USER = "user:";



    @Around(value = POINTCUT_USER_GET)
    private Object cacheUserGet (ProceedingJoinPoint joinPoint) throws Throwable {
        Object object = joinPoint.getArgs()[0];
        Object o2 = CACHE_POOL.get(POINTCUT_PROFIX_USER + object);
        if (o2 != null) {
            log.info("用户信息已存在");
            return o2;
        } else {
            SysUser proceed = (SysUser) joinPoint.proceed();
            CACHE_POOL.put(POINTCUT_PROFIX_USER + proceed.getId(),proceed);
            log.info("用户信息从缓存中取出");
            return proceed;
        }
    }


    @Around(value = POINTCUT_USER_UPDATE)
    private Object cacheUserUpdate (ProceedingJoinPoint joinPoint) throws Throwable {
        UserVO vo = (UserVO) joinPoint.getArgs()[0];
        Boolean succes = (Boolean) joinPoint.proceed();
        if (succes) {
            SysUser o2 = (SysUser)CACHE_POOL.get(POINTCUT_PROFIX_USER + vo.getId());
            if (null == o2) {
                o2 = new SysUser();
                BeanUtils.copyProperties(vo,o2);
                CACHE_POOL.put(POINTCUT_PROFIX_USER + o2.getId(),o2);
                log.info("用户信息已经更新");
            }
        }
        return succes;
    }



    @Around(value = POINTCUT_USER_REMOVE)
    private Object cacheUserRemove (ProceedingJoinPoint joinPoint) throws Throwable {
        Integer id = (Integer)joinPoint.getArgs()[0];
        Boolean success = (Boolean) joinPoint.proceed();
        if (success) {
            CACHE_POOL.remove(POINTCUT_PROFIX_USER+id);
            log.info("用户信息已从缓存中移除");
        }
        return success;
    }

    @Around(value = POINTCUT_USER_SAVE)
    private Object cacheUserSave (ProceedingJoinPoint joinPoint) throws Throwable {
        SysUser user = (SysUser)joinPoint.getArgs()[0];
        Boolean success = (Boolean) joinPoint.proceed();
        if (success) {
            CACHE_POOL.put(POINTCUT_PROFIX_USER+user.getId(),user);
            log.info("添加用户信息到数据库");
        }
        return success;
    }

}
