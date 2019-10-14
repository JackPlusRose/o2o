package com.imooc.o2o.service;

import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import org.springframework.web.multipart.MultipartFile;

public interface ShopService {
    /**
     * 注册店铺
     * @param shop
     * @param shopImg
     * @return
     */
    ShopExecution addShop(Shop shop,MultipartFile shopImg);

    /**
     * 通过店铺Id查询店铺信息
     * @param shopId
     * @return
     */
    Shop queryShopById(Long shopId);

    /**
     * 更新店铺信息
     * @param shop
     * @param multipartFile
     * @return
     */
    ShopExecution modifyShop(Shop shop,MultipartFile multipartFile);

    /**
     * 根据shopCondition分页返回相应店铺列表
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
}
