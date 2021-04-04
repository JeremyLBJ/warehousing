package com.rick.sys.common;

import com.rick.sys.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveUser {
    // 用户对象
    private SysUser sysUser;

    // 角色列表
    private List<String> roles;

    // 权限列表
    private List<String> permissions;
}
