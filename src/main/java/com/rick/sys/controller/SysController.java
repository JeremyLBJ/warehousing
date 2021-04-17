package com.rick.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sys")
public class SysController {

    /**
     * 用户登录页面
     *
     */
    @RequestMapping("/toLogin")
    public String toLogin () {
        return "system/index/login";
    }


    @RequestMapping("/index")
    public String index(){

        return "system/index/index";
    }

    @RequestMapping(value = "toMainPage")
    public String toMainPage () {
        return "system/index/main";
    }



    @RequestMapping("/toLogLoginManager")
    public String toLogLoginManager () {
        return "system/loginfo/loginfoManager";
    }


    @RequestMapping(value = "/toNoticeManager")
    public String toNoticeManager () {
        return "system/notice/noticeManager";
    }


    @RequestMapping(value = "/toDeptManager")
    public String toDeptManager () {
        return "system/dept/deptManager";
    }


    @RequestMapping(value = "/toLeftTree")
    public String toLeftTree () {
        return "system/dept/leftTree";
    }

    @RequestMapping(value = "/rightData")
    public String rightData () {
        return "system/dept/rightData";
    }


    @RequestMapping(value = "/toMenuManager")
    public String toMenuManager () {
        return "system/menu/menuManager";
    }
}
