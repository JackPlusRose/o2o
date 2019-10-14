package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.ShopCategoryDao;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.service.ShopCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service("shopCategoryService")
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Resource(name = "shopCategoryDao")
    ShopCategoryDao shopCategoryDao;
    public List<ShopCategory> queryShopCategory(ShopCategory shopCategoryCondition) {
        return shopCategoryDao.queryShopCategory(shopCategoryCondition);
    }
}
