package com.rick.sys.controller;

import com.rick.sys.common.ActiveUser;
import com.rick.sys.common.ResultObject;
import com.rick.sys.entity.SysLogLogin;
import com.rick.sys.entity.SysUser;
import com.rick.sys.service.ISysLogLoginService;
import com.rick.sys.untils.WebUntils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.sql.Date;
import java.time.LocalDateTime;

/**
 * @author Rick
 */
@RequestMapping("/login")
@Controller
public class SysLoginController {

    @Resource
    private ISysLogLoginService iSysLogLoginService;


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public ResultObject Login(String loginname, String pwd){
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token = new UsernamePasswordToken(loginname,pwd);
        try {
            subject.login(token);
            ActiveUser user = (ActiveUser) subject.getPrincipal();
            System.out.println(user);
            WebUntils.getSession().setAttribute("user",user.getSysUser());

            // 登录日志
            SysLogLogin entry = new SysLogLogin();
            entry.setLoginname(user.getSysUser().getName()+"-"+user.getSysUser().getLoginname());
            entry.setLogintime(LocalDateTime.now());
            entry.setLoginip(WebUntils.getRequest().getRemoteAddr());
            this.iSysLogLoginService.save(entry);
            return ResultObject.LOGIN_SUCCESS;
        }catch (AuthenticationException e) {
            e.printStackTrace();
            return ResultObject.LOGIN_ERROR;
        }
    }
}
