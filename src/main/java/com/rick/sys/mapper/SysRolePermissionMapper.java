package com.rick.sys.mapper;

import com.rick.sys.entity.SysRolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Rick
 * @since 2021-04-02
 */
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {

    void saveRolePermission(@Param("rid") Integer rid, @Param("pid") Integer id);


    void deleteRolePermissionByRid(Integer rid);
}
