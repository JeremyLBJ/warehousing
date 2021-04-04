package com.rick.sys.controller;

import com.rick.sys.common.ResultObject;
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

@RequestMapping("/login")
@Controller
public class SysLoginController {


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public ResultObject Login(String loginname, String pwd){
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token = new UsernamePasswordToken(loginname,pwd);
        try {
            subject.login(token);
            return ResultObject.LOGIN_SUCCESS;
        }catch (AuthenticationException e) {
            e.printStackTrace();
            return ResultObject.LOGIN_ERROR;
        }
    }
}
