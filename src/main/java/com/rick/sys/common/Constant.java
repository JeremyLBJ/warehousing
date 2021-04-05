package com.rick.sys.common;

public interface Constant {

    /**
     * 存放相关状态码
     */
     Integer OK = 200;
     Integer ERROR = -1;


    /**
     * 是否为超级管理员
     */
    Integer SUPER_USER = 0;
    Integer AVERAGE_USER = 1;


    /**
     * 菜单和权限
     */
    String MENU = "menu";
    String PERMISSION = "permission";


    /**
     * 是否展开
     */
    Integer IS_OPEN = 1;
    Integer NOT_OPEN = 0;


    /**
     * 是否可用
     */
    Integer AVAILABLE = 1;
    Integer DISABLE = 0;
}
