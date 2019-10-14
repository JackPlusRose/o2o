package com.imooc.o2o.controller.shopadmin;

import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.service.ProductCategoryService;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 返回从数据库中查询的结果
 */
@Controller
@RequestMapping("/shopAdmin")
public class ProductCategoryManagementController {
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     *根据店铺查询该店铺下商品的种类
     * @param request
     * @return
     */
    @RequestMapping(path ="/getProductCategoryList",method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductCategoryList(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Shop currentShop = new Shop();
        //可能有问题
        currentShop.setShopId(HttpServletRequestUtil.getLong(request,"shopId"));
        //设置当前的shop
//        request.getSession().setAttribute("currentShop",shop);
        //获取当前的shop
//        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if(currentShop!=null && currentShop.getShopId()>0){
            List<ProductCategory> productCategories = productCategoryService.queryProductCategoryList(currentShop.getShopId());
            modelMap.put("productCategories",productCategories);
            modelMap.put("success",true);
        }
        else{
            modelMap.put("success",false);
            modelMap.put("errMsg","店铺id不存在");
        }
        return modelMap;
    }
}
