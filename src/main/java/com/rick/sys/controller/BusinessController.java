package com.rick.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Rick
 */

@RequestMapping(value = "/bus")
@Controller
public class BusinessController {


    @RequestMapping(value = "/toCustomerManager")
    public String toCustomerManager () {
        return "business/customer/customerManager";
    }

    @RequestMapping(value = "/toProviderManager")
    public String toProviderManager () {
        return "business/provider/providerManager";
    }

    @RequestMapping(value = "/toGoodsManager")
    public String toGoodsManager (){
        return "/business/goods/goodsManager";
    }

    @RequestMapping(value = "/toInportManager")
    public String toInportManager () {
        return "/business/inport/inportManaget";
    }


    @RequestMapping(value = "/toOutportManager")
    public String toOutportManager () {
        return "/business/outport/outportManager";
    }
}
