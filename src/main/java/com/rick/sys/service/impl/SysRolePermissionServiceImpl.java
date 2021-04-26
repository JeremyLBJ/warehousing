package com.rick.sys.service.impl;

import com.rick.sys.entity.SysRolePermission;
import com.rick.sys.mapper.SysRolePermissionMapper;
import com.rick.sys.service.ISysRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rick.sys.service.ISysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements ISysRolePermissionService {


    @Override
    public void saveRolePermission(Integer rid, Integer[] ids) {
        SysRolePermissionMapper baseMapper = this.getBaseMapper();
        //根据rid删除
        baseMapper.deleteRolePermissionByRid(rid);
        if (null != ids && ids.length > 0) {
            for (Integer id: ids) {
                baseMapper.saveRolePermission(rid,id);
            }
        }
    }

    @Override
    public List<Integer> queryPidByRids(List<Integer> integers) {
        SysRolePermissionMapper baseMapper = this.getBaseMapper();
        baseMapper.queryPidByRids(integers);
        return null;
    }

    @Override
    public List<Integer> queryRolePermissionIdsByRid(Integer rid) {
        SysRolePermissionMapper baseMapper = this.getBaseMapper();
        List<Integer> permissionIds = baseMapper.queryRolePermissionIdsByRid(rid);
        return permissionIds;
    }
}
