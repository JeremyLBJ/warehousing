package com.rick.sys.service.impl;

import com.rick.sys.entity.SysRole;
import com.rick.sys.mapper.SysRoleMapper;
import com.rick.sys.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Rick
 * @since 2021-04-02
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {


    @Override
    public boolean removeById(Serializable id) {
        //根据角色ID删除sys_role_permission
        this.getBaseMapper().deleteRolePermissionByRid(id);
        //根据角色ID删除sys_role_user
        this.getBaseMapper().deleteRoleUserByRid(id);
        return super.removeById(id);
    }

    @Override
    public List<Integer> queryIdsByRoleId(Integer roleId) {
        List<Integer> ids = this.getBaseMapper().queryIdsByRoleId(roleId);
        return ids;
    }
}
