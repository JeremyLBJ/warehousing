package com.rick.sys.service;

import com.rick.sys.entity.SysRole;
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
public interface ISysRoleService extends IService<SysRole> {

    List<Integer> queryIdsByRoleId(Integer roleId);
}
