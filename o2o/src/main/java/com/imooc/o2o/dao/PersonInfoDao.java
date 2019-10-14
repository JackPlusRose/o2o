package com.imooc.o2o.dao;

import com.imooc.o2o.entity.PersonInfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository("personInfoDao")
public interface PersonInfoDao {
    /**
     * 根据用户id查找记录
     * @param userId
     * @return
     */
    @Select("select * from tb_person_info where userId=#{userId}")
    PersonInfo queryPersonInfoById(Long userId );
}
