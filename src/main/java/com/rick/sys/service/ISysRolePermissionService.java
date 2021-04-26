package com.rick.sys.service;

import com.rick.sys.entity.SysRolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Rick
 * @since 2021-04-02
 */
public interface ISysRolePermissionService extends IService<SysRolePermission> {

    void saveRolePermission(Integer rid, Integer[] ids);

    List<Integer> queryPidByRids(List<Integer> rid);

    List<Integer> queryRolePermissionIdsByRid(Integer rid);
}
