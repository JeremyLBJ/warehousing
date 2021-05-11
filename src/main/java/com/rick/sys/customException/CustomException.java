package com.rick.sys.customException;

import com.rick.sys.enums.ResultEnums;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private Integer code;

    public CustomException(ResultEnums resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public CustomException(Integer code, String message) {
        super(message);
        this.code = code;
    }

}
