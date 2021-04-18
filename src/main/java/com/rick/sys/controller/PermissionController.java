package com.rick.sys.controller;

import com.rick.sys.service.ISysPermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author Rick
 * 权限前端控制器
 */
@RequestMapping("/permission")
@Controller
public class PermissionController {

    @Resource
    private ISysPermissionService iSysPermissionService;
}
