package com.imooc.o2o.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Description: 图片处理工具类
 *
 * @author tyronchen
 * @date 2018年4月10日
 */
public class ImageUtil {
    // 获取classpath的绝对值路径
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    // 时间格式化的格式
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    // 随机数
    private static final Random r = new Random();

    /**
     * 处理商铺缩略图
     *
     * @param thumbnail  Spring自带的文件处理对象
     * @param targetAddr 图片存储路径
     * @return
     */
    public static String generateThumbnail(MultipartFile thumbnail, String targetAddr) {
        // 获取随机文件名，防止文件重名
        String realFileName = getRandomFileName();
        // 获取文件扩展名
        String extension = getFileExtension(thumbnail);
        // 在文件夹不存在时创建
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;//图片保存的路径
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);//PathUtil.getImgBasePath() + relativeAddr  基本路径+图片保存路径
        try {
            Thumbnails.of(thumbnail.getInputStream()).size(200, 200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "water.PNG")), 0.5f)
                    .outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            throw new RuntimeException("创建缩略图失败：" + e.toString());
        }
        return relativeAddr;//返回图片保存的相对路径(/upload/item/shop" + shopId/图片名称)
    }

    /**
     * 处理首页头图
     *
     * @param thumbnail  Spring自带的文件处理对象
     * @param targetAddr 图片存储路径
     * @return
     */
//    public static String generateHeadImg(MultipartFile thumbnail, String targetAddr) {
//        // 获取随机文件名，防止文件重名
//        String realFileName = getRandomFileName();
//        // 获取文件扩展名
//        String extension = getFileExtension(thumbnail);
//        // 在文件夹不存在时创建
//        makeDirPath(targetAddr);
//        String relativeAddr = targetAddr + realFileName + extension;
//        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
//        try {
//            Thumbnails.of(thumbnail.getInputStream()).size(400, 300).outputQuality(0.9f).toFile(dest);
//        } catch (IOException e) {
//            throw new RuntimeException("创建缩略图失败：" + e.toString());
//        }
//        return relativeAddr;
//    }

    /**
     * 处理商品分类图
     *
     * @param thumbnail  Spring自带的文件处理对象
     * @param targetAddr 图片存储路径
     * @return
     */
//    public static String generateShopCategoryImg(MultipartFile thumbnail, String targetAddr) {
//        // 获取随机文件名，防止文件重名
//        String realFileName = getRandomFileName();
//        // 获取文件扩展名
//        String extension = getFileExtension(thumbnail);
//        // 在文件夹不存在时创建
//        makeDirPath(targetAddr);
//        String relativeAddr = targetAddr + realFileName + extension;
//        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
//        try {
//            Thumbnails.of(thumbnail.getInputStream()).size(50, 50).outputQuality(0.9f).toFile(dest);
//        } catch (IOException e) {
//            throw new RuntimeException("创建缩略图失败：" + e.toString());
//        }
//        return relativeAddr;
//    }

    /**
     * 处理商品缩略图
     *
     * @param thumbnail  Spring自带的文件处理对象
     * @param targetAddr 图片存储路径
     * @return
     */
//    public static String generateProductImg(MultipartFile thumbnail, String targetAddr) {
//        // 获取随机文件名，防止文件重名
//        String realFileName = getRandomFileName();
//        // 获取文件扩展名
//        String extension = getFileExtension(thumbnail);
//        // 在文件夹不存在时创建
//        makeDirPath(targetAddr);
//        String relativeAddr = targetAddr + realFileName + extension;
//        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
//        try {
//            Thumbnails.of(thumbnail.getInputStream()).size(337, 640)
//                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "water.jpg")), 0.5f)
//                    .outputQuality(0.9f).toFile(dest);
//        } catch (IOException e) {
//            throw new RuntimeException("创建缩略图失败：" + e.toString());
//        }
//        return relativeAddr;
//    }

    /**
     * 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
     */
    private static String getRandomFileName() {
        // 获取随机五位数
        int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000;
        // 当前时间
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr + rannum;
    }

    /**
     * 获取输入流的文件扩展名
     *
     * @param thumbnail
     * @return
     */
    private static String getFileExtension(MultipartFile thumbnail) {
        String originalFileName = thumbnail.getOriginalFilename();// 返回客户端文件系统中的原始文件名
        return originalFileName.substring(originalFileName.lastIndexOf("."));
    }

    /**
     * 创建目标路径上所涉及的目录
     *
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        // 目录文件不存在
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    //删除目录和目录下的文件
    public static void deleteFilrOrPath(String storePath){
        File file = new File(PathUtil.getImgBasePath()+storePath);//将传过来的字符串转成文件形式
        if(file.exists()){//如果文件已经存在
            if (file.isDirectory()){//并且是目录的话
                File[] files = file.listFiles();//列出目录下的所有文件
                for (int i=0;i<files.length;i++){
                    files[i].delete();//并删除
                }
            }
            file.delete();//再删除目录
        }
    }

    /**
     * filePath to MultipartFile
     *
     * @param filePath
     * @throws IOException
     */
//    public static MultipartFile path2MultipartFile(String filePath) throws IOException {
//        File file = new File(filePath);
//        FileInputStream input = new FileInputStream(file);
//        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain",
//                IOUtils.toByteArray(input));
//        return multipartFile;
//    }

    /**
     * https://github.com/coobird/thumbnailator/wiki/Examples
     */
//    public static void main(String[] args) {
//        try {
//            Thumbnails.of(new File("F:\\luffy.jpg")).size(400, 300)
//                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "watermark.jpg")), 0.5f)
//                    .outputQuality(0.8).toFile(new File("F:\\luffy-with-watermark.jpg"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
//import net.coobird.thumbnailator.Thumbnails;
//import net.coobird.thumbnailator.geometry.Positions;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.imageio.ImageIO;
//import java.io.File;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Random;
//
//public class ImgUtil {
//    private static String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
//    private static final SimpleDateFormat SIMPLE_DATE_FORMAT=new SimpleDateFormat("yyyyMMddHHmmss");
//    private static final Random randomFileName=new Random();
//
//    public static String generateThumbnail(MultipartFile thumbnail, String targetAddr){
//        String realFileName=getRandomFileName();
//        String extension=getFileExtension(thumbnail);
//        makeDirPath(targetAddr);
//        String relativeAddr=targetAddr+realFileName+extension;
//        File dest=new File(PathUtil.getImgBasePath()+relativeAddr);
//        try {
//            Thumbnails.of(thumbnail.getInputStream())
//                    .size(200,200)
//                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(path+"/water.PNG")),0.5f )
//                    .outputQuality(0.8).toFile(dest);
//        } catch (IOException e) {
//            throw new RuntimeException("创建图片失败："+e.toString());
//        }
//        return relativeAddr;
//    }
//
//    private static void makeDirPath(String targetAddr) {
//        String relFileParentPath=PathUtil.getImgBasePath()+targetAddr;
//        File dirPath=new File(relFileParentPath);
//        if (!dirPath.exists()){
//            dirPath.exists();
//        }
//    }
//
//    private static String getFileExtension(MultipartFile thumbnail) {
//        String originalFilename = thumbnail.getOriginalFilename();
//        return originalFilename.substring(originalFilename.lastIndexOf("."));
//    }
//    public static String getRandomFileName() {
//        int ramdomNumber=randomFileName.nextInt(89999)+10000;
//        String nowTime=SIMPLE_DATE_FORMAT.format(new Date());
//        return ramdomNumber+ nowTime;
//    }
//
//    public static void main(String[] args) {
//        try {
//            Thumbnails.of(new File("E:\\exercise\\o2oimg\\example.PNG"))
//                    .size(997, 639)
//                    .watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(path+"/water.PNG")),0.9f)
//                    .outputQuality(0.6f).toFile("E:\\exercise\\o2oimg\\examplenew3.PNG");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
