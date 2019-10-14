package com.imooc.o2o.dao;

import com.imooc.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("productCategoryDao")
public interface ProductCategoryDao {
    /**
     * 通过shopId查询
     * @param shopId
     * @return
     */
    @Select("select * from tb_product_category where shopId=#{shopId} order by priority desc")
    List<ProductCategory> queryProductCategoryList(long shopId);

}
