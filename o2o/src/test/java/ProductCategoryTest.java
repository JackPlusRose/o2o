import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.service.ProductCategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductCategoryTest extends BaseTest{
    @Autowired
    private ProductCategoryService productCategoryService;
    @Test
    public void queryProductCategoryByShopId(){
        List<ProductCategory> productCategories = productCategoryService.queryProductCategoryList(1L);
        for (ProductCategory productCategory : productCategories) {
            System.out.println(productCategory.getProductCategoryId()+"  "+productCategory.getShopId());
        }
        Assert.assertEquals(productCategories.size(),2);
    }
}
