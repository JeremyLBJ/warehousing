package com.rick.sys.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.plaf.PanelUI;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultObject {

    /**
     * 登录返回状态
     */
    public static final ResultObject LOGIN_SUCCESS = new ResultObject(Constant.OK,"登录成功");
    public static final ResultObject LOGIN_ERROR = new ResultObject(Constant.ERROR,"登录失败,用户名或密码错误");
    public static final ResultObject LOGIN_CODE_ERROR = new ResultObject(Constant.ERROR,"登录失败,验证码错误");


    /**
     * 删除状态码
     */
    public static final ResultObject DELETE_SUCCESS = new ResultObject(Constant.OK,"删除成功");
    public static final ResultObject DELETE_ERROR = new ResultObject(Constant.ERROR,"删除失败");

    /**
     * 添加状态码
     */
    public static final ResultObject SAVE_SUCCESS = new ResultObject(Constant.OK,"添加成功");
    public static final ResultObject SAVE_ERROR = new ResultObject(Constant.ERROR,"添加失败");

    /**
     * 修改状态码
     */
    public static final ResultObject UPDATE_SUCCESS = new ResultObject(Constant.OK,"修改成功");
    public static final ResultObject UPDATE_ERROR = new ResultObject(Constant.ERROR,"修改失败");


    private Integer code;
    private String msg;
}
