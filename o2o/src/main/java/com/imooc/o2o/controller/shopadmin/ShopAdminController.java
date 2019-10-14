package com.imooc.o2o.controller.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//用于测试跳转到店铺注册页面
@Controller
@RequestMapping(path = "/shopadmin")
/**
 * 定义从浏览器可以访问页面的url
 */
public class ShopAdminController {
    @RequestMapping(path = "/shopOperation")
    public String shopOperation(){
        return "shop/shopoperation";
    }
    @RequestMapping(path = "/shopList")
    public String shopList(){
        return "shop/shoplist";
    }
    @RequestMapping(path = "/shopManagement")
    public String shopManagement(){
        return "shop/shopmanagement";
    }
    @RequestMapping(path = "/productcategorymanagement")
    public String productCategoryManagement(){
        return "shop/productcategorymanagement";
    }
}
