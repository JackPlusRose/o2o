import com.imooc.o2o.dao.PersonInfoDao;
import com.imooc.o2o.entity.PersonInfo;
import org.junit.Test;

import javax.annotation.Resource;

public class PersonInfoTest extends BaseTest{
    @Resource(name="personInfoDao")
    PersonInfoDao personInfoDao;

    @Test
    public void testById(){
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(2L);
        PersonInfo personInfo1 = personInfoDao.queryPersonInfoById(personInfo.getUserId());
        System.out.println(personInfo1.getName());
    }
}
