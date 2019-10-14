package com.imooc.o2o.util;

public class PageCalculator {
    /**
     * 将前端展示在页面的数据量转化成数据库中的条数
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public static int calculateRowIndex(int pageIndex,int pageSize){
        return (pageIndex>0)?(pageIndex-1)*pageSize:0;
    }
}
