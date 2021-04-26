package com.rick.sys.service;

import com.rick.sys.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Rick
 * @since 2021-04-02
 */
public interface ISysUserService extends IService<SysUser> {

    void saveUserRole(Integer uid, Integer[] ids);
}
