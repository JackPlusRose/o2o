package com.imooc.o2o.controller.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopSateEnum;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.service.ShopCategoryService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 返回从数据库中查询的结果
 */
@Controller
@RequestMapping(path="/shopAdmin")
public class ShopManagermentController {
    @Resource(name = "shopService")
     private ShopService shopService;
    @Resource(name = "shopCategoryService")
    private ShopCategoryService shopCategoryService;
    @Resource(name = "areaService")
    private AreaService areaService;

    /**
     * 用于检查用户获取店铺信息的权限
     * @param request
     * @return
     */
    @RequestMapping(path = "/getShopManagementInfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopManagementInfo(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if(shopId<=0){
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            if (currentShopObj==null){
                modelMap.put("redirect",true);
                //后期可能有问题
                modelMap.put("url","/o2o/shopadmin/shopList");
            }else {
                Shop currentShop= (Shop) currentShopObj;
                modelMap.put("redirect",false);
                modelMap.put("shopId",currentShop.getShopId());
            }
        }else{
            Shop shop = new Shop();
            shop.setShopId(shopId);
            request.getSession().setAttribute("currentShop",shop);
            modelMap.put("redirect",false);
        }
        return modelMap;
    }

    /**
     * 供用户、商家、管理员查找店铺信息
     * @param request
     * @return
     */
    @RequestMapping(path = "/getShopList",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopList(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<String, Object>();
//      PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("user");
//      personInfo.setUserId(1L);
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(2L);
        personInfo.setName("test");

        Shop shop = new Shop();
        shop.setOwner(personInfo);
        try {
            ShopExecution se = shopService.getShopList(shop, 0, 100);
            //返回该用户下所有的店铺信息
            modelMap.put("shopList",se.getShopList());
            //返回店铺数量
            modelMap.put("count",se.getCount());
            //返回用户信息
            modelMap.put("user",personInfo);
            modelMap.put("success",true);
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }

    /**
     * 根据id获取用户修改店铺的信息
     * @param request
     * @return
     */
    @RequestMapping(path = "/getShopById",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopById(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if(shopId>-1){
            try {
                Shop shop= shopService.queryShopById(shopId);
                List<Area> areas = areaService.queryArea();
                modelMap.put("shop",shop);
                modelMap.put("areaList",areas);
                modelMap.put("success",true);
            } catch (Exception e) {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
            }
        }
        else{
            modelMap.put("success",false);
            modelMap.put("errMsg","empty shopId");
        }
        return modelMap;
    }

    /**
     * 用于更新用户提交的信息
     * @param request
     * @return
     */
    @RequestMapping(path = "/modifyShop",method = RequestMethod.POST)
    @ResponseBody//用于封装json数据
    private Map<String,Object> modifyShop(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //先验证验证码，如果错误就不提交数据
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","验证码验证失败！");
            return modelMap;
        }
//        接收并转化相应的参数,包括店铺信息以及图片信息,并封装到对应的bean中
//        数据封装到bean中
//        后台提交的数据格式{"shopName":"海澜之家","shopAddr":"苏州","phone":"18625122508","shopDesc":"男人的衣柜","shopCategory":{"shopCategoryId":1},"area":{"areaId":3}}
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper objectMapper = new ObjectMapper();
        Shop shop1=null;
        try {//将json数据封装到bean中
            shop1 = objectMapper.readValue(shopStr, Shop.class);
        } catch (IOException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
//        用于获取上传的文件
        CommonsMultipartFile shopImg=null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());//文件解析器
        if (commonsMultipartResolver.isMultipart(request)){//判断请求中是否有上传的文件流
            MultipartHttpServletRequest request1 = (MultipartHttpServletRequest) request;//把请求强制转化成文件请求，便可以获取上传的文件
            shopImg = (CommonsMultipartFile) request1.getFile("shopImg");//将文件转化成CommonsMultipartFile类型
        }
        //修改店铺信息
        if(shop1!=null && shop1.getShopId()!=null){
            ShopExecution shopExecution = shopService.modifyShop(shop1, shopImg);
            if(shopExecution.getState()== ShopSateEnum.SUCCESS.getState()){
                modelMap.put("success",true);
            }
            else{
                modelMap.put("success",false);
                modelMap.put("errMsg","修改店铺失败");
            }
            return modelMap;//返回结果

        }else{
            modelMap.put("success",false);
            modelMap.put("errmsg","请填写店铺id");
            return modelMap;
        }
    }


    /**
     * 初始化注册店铺信息
     * @return
     */
    @RequestMapping(path = "/getShopInitInfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> areaService(){
        Map<String,Object> modelMap=new HashMap<String, Object>();
        List<ShopCategory> shopCategoryList = null;
        List<Area> areaList = null;
        try {
            shopCategoryList = shopCategoryService.queryShopCategory(new ShopCategory());
            areaList = areaService.queryArea();
            modelMap.put("shopCategoryList",shopCategoryList);
            modelMap.put("areaList",areaList);
            modelMap.put("success",true);
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg","信息初始化失败！");
        }
        return modelMap;
    }

    /**
     * 用户注册
     * @param request
     * @return
     */
    @RequestMapping(path = "/registerShop",method = RequestMethod.POST)
    @ResponseBody//用于封装json数据
    private Map<String,Object> registerShop(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<String, Object>();
//        验证码验证
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","验证码验证失败");
            return modelMap;
        }

//        接收并转化相应的参数,包括店铺信息以及图片信息,并封装到对应的bean中
//        数据封装到bean
//        后台提交的数据格式{"shopName":"海澜之家","shopAddr":"苏州","phone":"18625122508","shopDesc":"男人的衣柜","shopCategory":{"shopCategoryId":1},"area":{"areaId":3}}
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper objectMapper = new ObjectMapper();
        Shop shop=null;
        try {
            shop = objectMapper.readValue(shopStr, Shop.class);//将json数据封装到bean中
        } catch (IOException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
//        用于获取上传的文件
        CommonsMultipartFile shopImg=null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());//文件解析器
        if (commonsMultipartResolver.isMultipart(request)){//判断请求中是否有上传的文件流
            MultipartHttpServletRequest request1 = (MultipartHttpServletRequest) request;//把请求强制转化成文件请求，便可以获取上传的文件
            shopImg = (CommonsMultipartFile) request1.getFile("shopImg");//将文件转化成CommonsMultipartFile类型
        }else{
            modelMap.put("success",false);
            modelMap.put("errmsg","上传图片不能为空");
            return modelMap;
        }

        if(shop!=null && shopImg!=null){
            //获取用户登录的信息
//            PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("user");
//            personInfo.setUserId(1L);
            PersonInfo personInfo = new PersonInfo();
            personInfo.setUserId(2L);
            shop.setOwner(personInfo);


            ShopExecution shopExecution = shopService.addShop(shop, shopImg);
            if(shopExecution.getState()== ShopSateEnum.CHECK.getState()){
                modelMap.put("success",true);

                //用于存储该用户可以操作的店铺列表
                List<Shop> shopList= (List<Shop>) request.getSession().getAttribute("shopList");
                if (shopList==null || shopList.size()==0){
                    shopList=new ArrayList<Shop>();
                }
                shopList.add(shopExecution.getShop());
                request.getSession().setAttribute("shopList",shopList);
            }
            else{
                modelMap.put("success",false);
                modelMap.put("errMsg","创建店铺失败");
            }
            return modelMap;//返回结果
        }else{
            modelMap.put("success",false);
            modelMap.put("errmsg","请填写信息");
            return modelMap;
        }
    }
}
