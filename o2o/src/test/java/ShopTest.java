import com.imooc.o2o.dao.ShopDao;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopSateEnum;
import com.imooc.o2o.exception.ShopOperationException;
import com.imooc.o2o.service.ShopService;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ShopTest extends BaseTest{
    @Resource(name="shopService")
    private ShopService shopService;
    @Resource(name = "shopDao")
    private ShopDao shopDao;
    @Test
    public void testInsertShop(){
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);

        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(1L);

        Area area = new Area();
        area.setAreaId(1);

        Shop shop = new Shop();
        shop.setShopName("波司登");
        shop.setShopDesc("男人的衣柜");
        shop.setShopAddr("江苏苏州");
        shop.setPhone("18625123694");
        shop.setShopImg("");
        shop.setPriority(2);
        shop.setCreateTime(new Date());
        shop.setLastEditTime(new Date());
        shop.setEnableStatus(0);
        shop.setAdvice("审核中...");

        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        int i = shopDao.insertShop(shop);
    }

    @Test
    public void testUpdateShop(){
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(11L);

//        Area area = new Area();
//        area.setAreaId(2);

        Shop shop = new Shop();
        shop.setShopId(1L);
        shop.setShopDesc("女人的衣柜");
        shop.setShopAddr("江苏常州");
        shop.setShopImg("");
        shop.setPriority(1);
        shop.setLastEditTime(new Date());
        shop.setAdvice("正在审核中呢...");

//        shop.setArea(area);
//        shop.setShopCategory(shopCategory);
        int i = shopDao.updateShop(shop);
        Assert.assertEquals(1,i);
    }

    @Test
    public void shopServiceTest() {
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);

        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(1L);

        Area area = new Area();
        area.setAreaId(2);

        Shop shop = new Shop();
        shop.setShopName("波司登14");
        shop.setShopDesc("男人的衣柜1");
        shop.setShopAddr("江苏苏州1");
        shop.setPhone("186251236941");
        shop.setPriority(2);
        shop.setAdvice("审核中1...");
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);//E:/exercise/o2oimg
        String path="E:\\exercise\\o2oimg\\example.PNG";
        ShopExecution shopExecution = null;
        try {
            shopExecution = shopService.addShop(shop, path2MultipartFile(path));
        } catch (IOException e) {
            throw new ShopOperationException("文件转化出错"+e.getMessage());
        }
        Assert.assertEquals(ShopSateEnum.CHECK.getState(),shopExecution.getState());
    }
    private MultipartFile path2MultipartFile(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain",
                IOUtils.toByteArray(input));
        return multipartFile;
    }

    @Test
    public void testByShopId(){
        Shop shop1 = shopService.queryShopById(2L);
        System.out.println(shop1);
        System.out.println(shop1.getOwner());
        System.out.println(shop1.getArea());
        System.out.println("sdf"+"id:"+shop1.getShopCategory().getShopCategoryId()+"name:"+shop1.getShopCategory().getShopCategoryName());
        System.out.println(shop1.getShopCategory().getParent());
    }

    @Test
    public void modifyTest()throws Exception{
        Shop shop = new Shop();
        shop.setShopId(4L);
        shop.setShopName("哈哈哈啊2");

        Area area = new Area();
        area.setAreaId(4);
        shop.setArea(area);
        String path="E:\\exercise\\o2oimg\\example1.PNG";
        ShopExecution shopExecution = shopService.modifyShop(shop, path2MultipartFile(path));
        Assert.assertEquals(shopExecution.getState(),ShopSateEnum.SUCCESS.getState());
    }
    @Test
    public void manyTest(){
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(2L);

        Shop shop = new Shop();
        shop.setEnableStatus(0);
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(2L);
        shop.setShopCategory(shopCategory);
//        shop.setShopName("%衣%");
        shop.setOwner(personInfo);
        List<Shop> shopList = shopDao.queryShopList(shop, 0, 3);
        int i = shopDao.queryShopCount(shop);
        for (Shop shop1 : shopList) {
            System.out.println(shop1);
        }
        System.out.println("一共"+i+"条数据！");
    }

    @Test
    public void manyServiceTest(){
//        PersonInfo personInfo = new PersonInfo();
//        personInfo.setUserId(2L);
//        ShopCategory shopCategory = new ShopCategory();
//        shopCategory.setShopCategoryId(1L);
//        Area area = new Area();
//        area.setAreaId(1);

        Shop shop = new Shop();
//        shop.setShopCategory(shopCategory);
//        shop.setOwner(personInfo);
//        shop.setArea(area);
        shop.setEnableStatus(0);
        ShopExecution se = shopService.getShopList(shop, 2, 2);
        List<Shop> shopList = se.getShopList();
        for (Shop shop1 : shopList) {
            System.out.println(shop1);
        }
        System.out.println("数据一共有"+se.getCount()+"条!");
    }
}
