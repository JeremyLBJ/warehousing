package com.rick.sys.controller;

import com.rick.sys.common.ResultObject;
import com.rick.sys.service.IBusOutportService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping(value = "/outport")
@RestController
public class OutPortController {

    @Resource
    private IBusOutportService outportService;

    @RequestMapping(value = "/addOutport",method = RequestMethod.POST)
    public ResultObject addOutport (Integer id, Integer number,String remark) {
        try {
            this.outportService.addOutport(id,number,remark);
            return ResultObject.RETURNGOODS_SUCCESS;
        }catch (Exception e) {
            e.printStackTrace();
            return ResultObject.RETURNGOODS_ERROR;
        }
    }
}
