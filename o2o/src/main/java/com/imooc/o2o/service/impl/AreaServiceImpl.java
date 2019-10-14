package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.AreaDao;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.service.AreaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service("areaService")
public class AreaServiceImpl implements AreaService {
    @Resource(name = "areaDao")
    private AreaDao areaDao;
    public int addArea(Area area) {
        return areaDao.addArea(area);
    }

    public List<Area> queryArea() {
        return areaDao.queryArea();
    }
}
