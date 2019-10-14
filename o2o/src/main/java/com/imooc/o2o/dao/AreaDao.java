package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Area;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("areaDao")
public interface AreaDao {
    /**
     * 插入一条记录，并返回记录的id存放到实体中
     * @param area
     * @return
     */
    @Insert("insert into tb_area(areaName,priority,createTime,lastEditTime) values(#{areaName},#{priority},#{createTime},#{lastEditTime})")
    @Options(useGeneratedKeys = true,keyProperty = "areaId",keyColumn = "areaId")//用于返回插入的id
    int addArea(Area area);

    /**
     * 查询所有记录
     * @return
     */
    @Select("select * from tb_area order by priority desc")
    List<Area> queryArea();

    /**
     * 根据id查询一条记录
     * @param area
     * @return
     */
    @Select("select * from tb_area where areaId=#{area}")
    Area queryAreaById(int area);
}
