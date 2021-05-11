package com.rick.sys.enums;

import lombok.Getter;

@Getter
public enum ResultEnums {

    SUCCESS(200,"成功"),

    FAILURE(-1,"系统异常"),

    PARAM_ERROR(300,"参数异常"),

    RESULT_NOT_EXIST(301, "查询结果不存在");

    private Integer code;

    private String message;

    ResultEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
