import com.imooc.o2o.dao.ShopCategoryDao;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.service.ShopCategoryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

public class ShopCategoryTest extends BaseTest {
//    @Resource(name = "shopCategoryDao")
//    private ShopCategoryDao shopCategoryDao;
    @Autowired
    ShopCategoryService shopCategoryService;
    @Resource(name="shopCategoryDao")
    ShopCategoryDao shopCategoryDao;
    @Test
    public void testShopCategory(){
        List<ShopCategory> shopCategories = shopCategoryService.queryShopCategory(new ShopCategory());
        for (ShopCategory shopCategory : shopCategories) {
            System.out.println(shopCategory.getShopCategoryName());
        }
    }

    @Test
    public void testById(){
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(2L);
        ShopCategory shopCategory1 = shopCategoryDao.queryShopCategoryById(1);
        System.out.println(shopCategory1+ shopCategory1.getShopCategoryName());
        System.out.println(shopCategory1.getParent()+shopCategory1.getParent().getShopCategoryName());
    }
}
