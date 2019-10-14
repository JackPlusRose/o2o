package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.ShopDao;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopSateEnum;
import com.imooc.o2o.exception.ShopOperationException;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PageCalculator;
import com.imooc.o2o.util.PathUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("shopService")
public class ShopServiceImpl implements ShopService {
    @Resource(name = "shopDao")
   private ShopDao shopDao;

    /**
     * 根据shopCondition 分页返回相应店铺列表
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        //将第几页的数据转化成数据库中的行数，便于再次查询
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        //返回查询满足条件的店铺集合
        List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
        //返回所有满足条件的店铺的个数
        int count = shopDao.queryShopCount(shopCondition);
        //用于返回查询的状态
        ShopExecution se = new ShopExecution();
        if(shopList!=null){
            se.setShopList(shopList);
            se.setCount(count);
        }else{
            se.setState(ShopSateEnum.INNER_ERROR.getState());
        }
        return se;
    }

    /**
     * 添加店铺
     * @param shop
     * @param shopImg
     * @return
     */
    @Transactional
    public ShopExecution addShop(Shop shop, MultipartFile shopImg) {
        //空值判断
        if(shop==null){
            return new ShopExecution(ShopSateEnum.NULL_SHOP);//这样可以返回enums类中枚举出来的类
        }
        try {
            //设置初始值
            shop.setEnableStatus(0);
            shop.setAdvice("正在审核中呢..");
            shop.setPriority(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            //添加店铺信息
            int i = shopDao.insertShop(shop);//并将图片的主键写入实体shopid中
            if(i<=0){
                throw new ShopOperationException("店铺创建失败！");
            }else{
                if(shopImg!=null){
                    //存储图片
                    try {
                        addShopImg(shop,shopImg);//图片地址保存到shopImg属性中
                    } catch (Exception e) {
                        throw new ShopOperationException("addShopImg error"+e.getMessage());//ShopOperationException可以终止事务的回滚
                    }
//                    更新图片地址
                    i= shopDao.updateShop(shop);
                    if(i<=0){
                        throw new ShopOperationException("更新图片失败");
                    }
                }
            }
        } catch (Exception e) {
            throw new ShopOperationException("addShop error!  "+e.getMessage());
        }
        return new ShopExecution(ShopSateEnum.CHECK,shop);
    }

    /**
     * 把图片添加到shop的shopImg属性值
     * @param shop
     * @param shopImg
     */
    private void addShopImg(Shop shop, MultipartFile shopImg) {
        //获取shop图片目录的相对值路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        //添加水印
        String shopImageAddr = ImageUtil.generateThumbnail(shopImg, dest);
        shop.setShopImg(shopImageAddr);
    }

    /**
     * 获取店铺信息，为更新做准备
     * @param shopId
     * @return
     */
    public Shop queryShopById(Long shopId) {
        return shopDao.queryShopById(shopId);
    }

    /**
     * 更心用户信息
     * @param shop
     * @param multipartFile
     * @return
     */
    public ShopExecution modifyShop(Shop shop,MultipartFile multipartFile)throws ShopOperationException{
        if(shop==null || shop.getShopId()==null){//店铺不存在则更新失败
            return new ShopExecution(ShopSateEnum.NULL_SHOP);
        }else{//是否更新图片
            try {
                if(multipartFile!=null) {
                    Shop shop1 = shopDao.queryShopById(shop.getShopId());//获取数据库中店铺信息，为了删除图片目录
                    if (shop1.getShopImg() != null) {//将数据库中的图片目录删除
                        System.out.println(shop1.getShopImg());
//                        删除该文件夹下所有图片
//                        ImageUtil.deleteFilrOrPath(PathUtil.getShopImagePath(shop1.getShopId()));
//                        只删除传过去的图片的地址
                        ImageUtil.deleteFilrOrPath(shop1.getShopImg());
                    }
                    addShopImg(shop, multipartFile);//将传过来的图片加水印，并将图片相对地址写入传过来的shop的shopimg属性中
                }
                    //                将新的店铺数据进行更新
                    shop.setLastEditTime(new Date());
                    int i = shopDao.updateShop(shop);
                    if(i<=0){
                        return new ShopExecution(ShopSateEnum.INNER_ERROR);//插入失败
                    }else{
                        shop =shopDao.queryShopById(shop.getShopId());
                        return new ShopExecution(ShopSateEnum.SUCCESS,shop);//插入成功，并返回二个参数
                    }
            } catch (Exception e) {
                throw new ShopOperationException("modifyShop error"+e.getMessage());//异常则不会将信息写入数据库中
            }
        }
    }
}
