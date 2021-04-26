package com.rick.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.rick.sys.entity.SysUser;
import com.rick.sys.mapper.SysRoleMapper;
import com.rick.sys.mapper.SysUserMapper;
import com.rick.sys.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Rick
 * @since 2021-04-02
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {


    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public boolean save(SysUser entity) {
        return super.save(entity);
    }

    @Override
    public SysUser getOne(Wrapper<SysUser> queryWrapper) {
        return super.getOne(queryWrapper);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean updateById(SysUser entity) {
        return super.updateById(entity);
    }

    @Override
    public void saveUserRole(Integer uid, Integer[] ids) {
        //根据用户ID删除sys_role_user里面的数据
        this.sysRoleMapper.deleteRoleUserByUid(uid);
        if(null!=ids&&ids.length>0) {
            for (Integer rid : ids) {
                this.sysRoleMapper.insertUserRole(uid,rid);
            }
        }
    }
}
