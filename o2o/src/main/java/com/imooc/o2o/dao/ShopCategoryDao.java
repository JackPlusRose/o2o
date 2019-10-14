package com.imooc.o2o.dao;

import com.imooc.o2o.entity.ShopCategory;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("shopCategoryDao")
public interface ShopCategoryDao {
    /**
     * 查询所有二级店铺信息（即parentId不为空）
     * @param shopCategoryCondition
     * @return
     */
    @Select("<script>select * from tb_shop_category " +
            "<where>" +
            "<if test=\"shopCategoryCondition!=null\">and parentId is not null </if>" +//店铺是放在二级页面，所以他的parent不能为空，且店铺要存在
            "<if test=\"shopCategoryCondition.parent!=null\">and parentId=#{shopCategoryCondition.parent.shopCategoryId} </if>" +
            "</where>order by priority desc</script>")
    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition")ShopCategory shopCategoryCondition);

    /**
     * 根据id查询店铺信息
     * @param shopCategory
     * @return
     */
    @Results(id = "resultMap",value = {
            @Result(id = true,property = "shopCategoryId",column = "shopCategoryId"),
            @Result(property = "shopCategoryName",column = "shopCategoryName"),
            @Result(property = "shopCategoryDesc",column = "shopCategoryDesc"),
            @Result(property = "shopCategoryImg",column = "shopCategoryImg"),
            @Result(property = "priority",column = "priority"),
            @Result(property = "createTime",column = "createTime"),
            @Result(property = "lastEditTime",column = "lastEditTime"),
            @Result(property = "parent",column = "parentId",one=@One(select ="com.imooc.o2o.dao.ShopCategoryDao.queryShopCategoryById",fetchType = FetchType.EAGER))
    })
    @Select("select * from tb_shop_category where shopCategoryId=#{shopCategory}")
    ShopCategory queryShopCategoryById(int shopCategory);
}
