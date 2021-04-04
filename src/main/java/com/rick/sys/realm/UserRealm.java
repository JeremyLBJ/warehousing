package com.rick.sys.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rick.sys.common.ActiveUser;
import com.rick.sys.entity.SysUser;
import com.rick.sys.service.ISysUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

public class UserRealm extends AuthorizingRealm {

    @Resource
    private ISysUserService userService;


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
        return null;
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
            ByteSource credentialSalt= ByteSource.Util.bytes(user.getSalt());
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activeUser,user.getPwd(),credentialSalt,this.getName());
            return info;
        }
        return null;
    }
}
