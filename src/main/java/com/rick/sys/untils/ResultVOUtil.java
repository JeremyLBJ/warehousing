package com.rick.sys.untils;

import com.rick.sys.VO.ResultVO;
import com.rick.sys.enums.ResultEnums;

public class ResultVOUtil {

    public static ResultVO success(Object object){
        ResultVO vo = new ResultVO();
        vo.setData(object);
        vo.setCode(ResultEnums.SUCCESS.getCode());
        vo.setMsg(ResultEnums.SUCCESS.getMessage());
        return vo;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

    public static ResultVO error(ResultEnums resultEnum) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(resultEnum.getCode());
        resultVO.setMsg(resultEnum.getMessage());
        return resultVO;
    }

}
