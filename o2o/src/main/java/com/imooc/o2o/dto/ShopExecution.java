package com.imooc.o2o.dto;

import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopSateEnum;

import java.util.List;

/**
 * 店铺状态实体
 */
public class ShopExecution {
    //结果标识
     private int state;
//     状态标识
     private String stateInfo;
//    店铺数量
    private int count;
//      操作 shop(增删改使用)
    private Shop shop;
//    shop列表查询店铺列表时使用
    private List<Shop> shopList;

    public ShopExecution(){//空参构造函数

    }
//    失败时处理的构造器
    public ShopExecution(ShopSateEnum shopSateEnum){
        state=shopSateEnum.getState();
        stateInfo=shopSateEnum.getStateInfo();
    }
    //    成功时处理的构造器
    public ShopExecution(ShopSateEnum shopSateEnum,Shop shop){
        state=shopSateEnum.getState();
        stateInfo=shopSateEnum.getStateInfo();
        this.shop=shop;
    }
    //    成功时处理的构造器
    public ShopExecution(ShopSateEnum shopSateEnum,List<Shop> shopList){
        state=shopSateEnum.getState();
        stateInfo=shopSateEnum.getStateInfo();
        this.shopList=shopList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
