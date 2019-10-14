package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.ProductCategoryDao;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.service.ProductCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service("productCategoryService")
public class ProductCategoryImpl implements ProductCategoryService {
    @Resource(name = "productCategoryDao")
    private ProductCategoryDao productCategoryDao;
    /**
     * 通过shopId查询
     * @param shopId
     * @return
     */
    public List<ProductCategory> queryProductCategoryList(long shopId) {
        List<ProductCategory> productCategories = productCategoryDao.queryProductCategoryList(shopId);
        return productCategories;
    }
}
