import com.imooc.o2o.dao.AreaDao;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.service.AreaService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

public class AreaTest extends BaseTest{
    @Autowired
    private AreaService areaService;
    @Resource(name = "areaDao")
    private AreaDao areaDao;

    @Test
    public void addAreaTest(){
        Area area = new Area();
        area.setAreaName("西苑");
        area.setCreateTime(new Date());
        area.setLastEditTime(new Date());
        area.setPriority(1);
        int i = areaService.addArea(area);
        Assert.assertEquals(1,i);
    }

    @Test
    public void queryAreaTest(){
        List<Area> areas = areaService.queryArea();
        for (Area area : areas) {
            System.out.println(area.getAreaName());
        }
        Assert.assertEquals(4,areas.size());
    }
//    @Test
//    public void queryAreaByIdTest(){
//        Area area1 = new Area();
//        area1.setAreaId(1);
//        Area areas = areaDao.queryAreaById(area1);
//        System.out.println(areas.getAreaName());
//    }
}
