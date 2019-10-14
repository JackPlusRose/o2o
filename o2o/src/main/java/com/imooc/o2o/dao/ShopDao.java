package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Shop;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("shopDao")
public interface ShopDao {
    /**
     * 分页查询店铺，可输入的条件有：店铺名（模糊查询），店铺状态，店铺类别
     * 区域Id  owner
     * @param shopCondition
     * @param rowIndex 从数据库中第几行开始查询数据
     * @param pageSize 返回数据的条数
     * @return
     */
    @Results(id="resultMap1",value = {
            @Result(id=true,property = "shopId",column = "shopId"),
            @Result(property = "shopName",column = "shopName"),
            @Result(property = "shopDesc",column = "shopDesc"),
            @Result(property = "shopAddr",column = "shopAddr"),
            @Result(property = "phone",column = "phone"),
            @Result(property = "shopImg",column = "shopImg"),
            @Result(property = "priority",column = "priority"),
            @Result(property = "createTime",column = "createTime"),
            @Result(property = "lastEditTime",column = "lastEditTime"),
            @Result(property = "enableStatus",column = "enableStatus"),
            @Result(property = "advice",column = "advice"),
            @Result(property = "area",column = "areaId",many=@Many(select = "com.imooc.o2o.dao.AreaDao.queryAreaById",fetchType = FetchType.EAGER)),
            @Result(property = "owner",column = "userId",many=@Many(select = "com.imooc.o2o.dao.PersonInfoDao.queryPersonInfoById",fetchType = FetchType.EAGER)),
            @Result(property = "shopCategory",column = "shopCategoryId",many = @Many(select = "com.imooc.o2o.dao.ShopCategoryDao.queryShopCategoryById",fetchType = FetchType.EAGER))
    })
    @Select("<script>" +
            "select * from tb_shop " +
            "<where>" +
            "<if test=\"shopCondition.shopCategory!=null and shopCondition.shopCategory.shopCategoryId!=null\">and shopCategoryId=#{shopCondition.shopCategory.shopCategoryId}</if>" +
            "<if test=\"shopCondition.area!=null and shopCondition.area.areaId!=null\"> and areaId=#{shopCondition.area.areaId}</if>" +
            "<if test=\"shopCondition.owner!=null and shopCondition.owner.userId!=null\"> and userId=#{shopCondition.owner.userId}</if>" +
            "<if test=\"shopCondition.enableStatus!=null\"> and enableStatus=#{shopCondition.enableStatus}</if>" +
            "<if test=\"shopCondition.shopName!=null\"> and shopName like #{shopCondition.shopName}</if>" +
            "</where>order by priority desc limit #{rowIndex},#{pageSize}" +
            "</script> ")
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,@Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);

    /**
     * 用于返回分页查询结果的总数
     * @param shopCondition
     * @return
     */
    @Select("<script>" +
            "select count(*) from tb_shop " +
            "<where>" +
            "<if test=\"shopCondition.shopCategory!=null and shopCondition.shopCategory.shopCategoryId!=null\">and shopCategoryId=#{shopCondition.shopCategory.shopCategoryId}</if>" +
            "<if test=\"shopCondition.area!=null and shopCondition.area.areaId!=null\"> and areaId=#{shopCondition.area.areaId}</if>" +
            "<if test=\"shopCondition.owner!=null and shopCondition.owner.userId!=null\"> and userId=#{shopCondition.owner.userId}</if>" +
            "<if test=\"shopCondition.enableStatus!=null\"> and enableStatus=#{shopCondition.enableStatus}</if>" +
            "<if test=\"shopCondition.shopName!=null\"> and shopName like #{shopCondition.shopName}</if>" +
            "</where>" +
            "</script> ")
    int queryShopCount(@Param("shopCondition") Shop shopCondition);


    /**
     * 新增店铺
     * @param shop
     * @return
     */
    @Options(useGeneratedKeys = true,keyProperty ="shopId" ,keyColumn ="shopId" )
    @Insert("insert into " +
            "tb_shop(shopName,shopDesc,shopAddr,phone,shopImg,priority,createTime,lastEditTime,enableStatus,advice,userId,areaId,shopCategoryId) " +
            "values(#{shopName},#{shopDesc},#{shopAddr},#{phone},#{shopImg},#{priority},#{createTime},#{lastEditTime},#{enableStatus},#{advice},#{owner.userId},#{area.areaId},#{shopCategory.shopCategoryId})" )
    int insertShop(Shop shop);

    /**
     * 更新店铺信息
     * @param shop
     * @return
     */
    @Update("<script>update tb_shop " +
            "<set>" +
            "<if test=\"shopName!=null\">shopName=#{shopName},</if>" +
            "<if test=\"shopDesc!=null\">shopDesc=#{shopDesc},</if>" +
            "<if test=\"shopAddr!=null\">shopAddr=#{shopAddr},</if>"+
            "<if test=\"shopImg!=null\">shopImg=#{shopImg},</if>"+
            "<if test=\"priority!=null\">priority=#{priority},</if>"+
            "<if test=\"lastEditTime!=null\">lastEditTime=#{lastEditTime},</if>"+
            "<if test=\"advice!=null\">advice=#{advice},</if>"+
            "<if test=\"phone!=null\">phone=#{phone},</if>"+
            "<if test=\"area.areaId!=null\">areaId=#{area.areaId}</if>"+
//            "<if test=\"shopCategory.shopCategoryId!=null\">shopCategoryId=#{shopCategory.shopCategoryId}</if>"+
            "</set> where shopId=#{shopId}</script>")
    int updateShop(Shop shop);

    /**
     * 注解四表联查
     * @param shopId
     * @return
     */
    @Results(id = "resultMap",value = {
            @Result(id = true,property = "shopId",column = "shopId"),
            @Result(property = "shopName",column = "shopName"),
            @Result(property = "shopDesc",column = "shopDesc"),
            @Result(property = "shopAddr",column = "shopAddr"),
            @Result(property = "phone",column = "phone"),
            @Result(property = "shopImg",column = "shopImg"),
            @Result(property = "advice",column = "advice"),
            @Result(property = "priority",column = "priority"),
            @Result(property = "createTime",column = "createTime"),
            @Result(property = "lastEditTime",column = "lastEditTime"),
            @Result(property = "enableStatus",column = "enableStatus"),
            @Result(property = "owner",column = "userId",one =@One(select = "com.imooc.o2o.dao.PersonInfoDao.queryPersonInfoById",fetchType = FetchType.EAGER)),
            @Result(property = "area",column = "areaId",one =@One(select = "com.imooc.o2o.dao.AreaDao.queryAreaById",fetchType = FetchType.EAGER)),
            @Result(property = "shopCategory",column = "shopCategoryId",one =@One(select = "com.imooc.o2o.dao.ShopCategoryDao.queryShopCategoryById",fetchType = FetchType.EAGER))
    })
    @Select("select * from tb_shop where shopId=#{shopId}")
    Shop queryShopById(Long shopId);
}
