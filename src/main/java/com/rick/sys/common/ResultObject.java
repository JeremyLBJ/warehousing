package com.rick.sys.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultObject {

    public static final ResultObject LOGIN_SUCCESS = new ResultObject(Constant.OK,"登录成功");
    public static final ResultObject LOGIN_ERROR = new ResultObject(Constant.ERROR,"登录失败,用户名或密码错误");
    public static final ResultObject LOGIN_CODE_ERROR = new ResultObject(Constant.ERROR,"登录失败,验证码错误");


    private Integer code;
    private String msg;
}
