import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//抽取测试的一些共同信息
@ContextConfiguration(locations = {"classpath:spring/Spring-service.xml","classpath:spring/Spring-dao.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseTest {
//    @Resource(name = "userService")
//    public IUserService userService;
}
