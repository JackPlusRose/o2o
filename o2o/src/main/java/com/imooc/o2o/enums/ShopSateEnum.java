package com.imooc.o2o.enums;

/**
 * 店铺状态枚举
 */
public enum ShopSateEnum {
    CHECK(0,"审核中"),OFFLINE(-1,"非法店铺"),SUCCESS(1,"操作成功"),
    PASS(2,"通过认证"),INNER_ERROR(-1001,"内部系统错误"),
    NULL_SHOPID(-1002,"shopId为空"),NULL_SHOP(-1003,"shop信息为空");
    private int state;
    private String stateInfo;
    private ShopSateEnum(int state,String stateInfo){
        this.state=state;
        this.stateInfo=stateInfo;
    }
    public static ShopSateEnum stateOf(int state){//根据输入的值获取对象
        for(ShopSateEnum shopSateEnum: values()){
            if(shopSateEnum.getState()==state){
                return shopSateEnum;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static void main(String[] args) {
//        ShopSateEnum shopSateEnum = ShopSateEnum.stateOf(1);
//        System.out.println(shopSateEnum.getState());
//        System.out.println(shopSateEnum.getStateInfo());
        ShopSateEnum success = ShopSateEnum.SUCCESS;
        System.out.println(success.getStateInfo());
        System.out.println(success.getState());
    }
}
