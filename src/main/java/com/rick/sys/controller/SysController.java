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
}
