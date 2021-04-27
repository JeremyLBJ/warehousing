package com.rick.sys.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rick.sys.common.ActiveUser;
import com.rick.sys.common.Constant;
import com.rick.sys.entity.SysPermission;
import com.rick.sys.entity.SysUser;
import com.rick.sys.service.ISysPermissionService;
import com.rick.sys.service.ISysRolePermissionService;
import com.rick.sys.service.ISysRoleUserService;
import com.rick.sys.service.ISysUserService;
import com.rick.sys.untils.WebUntils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    @Resource
    @Lazy  //只有使用的时候才会加载
    private ISysUserService userService;

    @Resource
    @Lazy
    private ISysPermissionService sysPermissionService;


    @Resource
    @Lazy
    private ISysRoleUserService roleUserService;

    @Resource
    @Lazy
    private ISysRolePermissionService permissionService;


    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * 用户授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        ActiveUser activeUser = (ActiveUser)principalCollection.getPrimaryPrincipal();
        List<String> permissions = activeUser.getPermissions();
        SysUser sysUser = activeUser.getSysUser();
        if (sysUser.getType().equals(Constant.SUPER_USER)){
            authorizationInfo.addStringPermission("*:*");
        }else {
            if(null!=permissions&&permissions.size()>0) {
                authorizationInfo.addStringPermissions(permissions);
            }
        }
        return authorizationInfo;
    }

    /**
     * 用户认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 查询用户 authenticationToken
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("loginname",authenticationToken.getPrincipal().toString());
        SysUser user = this.userService.getOne(queryWrapper);
        if (null != user) {
            ActiveUser activeUser = new ActiveUser();
            activeUser.setSysUser(user);

            QueryWrapper<SysPermission> qw = new QueryWrapper<>();
            qw.eq("type", Constant.PERMISSION);
            qw.eq("available",Constant.AVAILABLE);
            List<SysPermission> list = new ArrayList<>();
            if (Constant.SUPER_USER.equals(user.getType())) {
                // 超级管理员
                list = this.sysPermissionService.list(qw);
            }else {
                // 普通管理者
                //根据用户ID查询对应的角色ID
                List<Integer> integers = this.roleUserService.queryRidByUid(user.getId());
                if (integers.size() > 0){
                    //根据角色ID取到权限和菜单ID
                    Set<Integer> pids=new HashSet<>();
                    for (Integer rid : integers) {
                        //根据角色ID查询对应角色所拥有的的菜单和权限
                        List<Integer> permissionIds = permissionService.queryRolePermissionIdsByRid(rid);
                        pids.addAll(permissionIds);
                    }
                    //根据角色ID查询权限
                    if(pids.size()>0) {
                        qw.in("id", pids);
                        list = sysPermissionService.list(qw);
                    }
                }
            }
            List<String> percode = new ArrayList<>();
            for (SysPermission sysPermission: list) {
                percode.add(sysPermission.getPercode());
            }
            activeUser.setPermissions(percode);
            ByteSource credentialSalt= ByteSource.Util.bytes(user.getSalt());
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activeUser,user.getPwd(),credentialSalt,this.getName());
            return info;
        }
        return null;
    }
}
