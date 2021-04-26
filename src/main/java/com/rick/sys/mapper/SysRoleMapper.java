package com.rick.sys.mapper;

import com.rick.sys.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Rick
 * @since 2021-04-02
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    void deleteRolePermissionByRid(Serializable id);

    void deleteRoleUserByRid(Serializable id);

    List<Integer> queryIdsByRoleId(Integer roleId);

    void deleteRoleUserByUid(Integer uid);

    void insertUserRole(Integer uid, Integer rid);
}
