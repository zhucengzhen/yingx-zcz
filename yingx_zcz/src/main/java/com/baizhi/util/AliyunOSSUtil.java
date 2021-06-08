package com.baizhi.util;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class AliyunOSSUtil {

    // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
    // Endpoint以杭州为例，其它Region请按实际情况填写。
    private static String endpoint = "https://oss-cn-beijing.aliyuncs.com";

    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。
    // 强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
    private static String accessKeyId = "LTAI5tRzCFzzLPHuqre4UKXN";
    private static String accessKeySecret = "z696bEaloD2bbAEJHFsBCK6vc8OaKz";


    /**
     *将本地文件上传至阿里云
     *  参数:
     *  bucketName:指定存储空间名
     *  fileName:文件名
     *  localPath:本地文件路径
    * */
    public static void uploadlocalFileAliyun(String bucketName,String fileName,String localPath){

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。
        // 填写Bucket名称、Object完整路径和本地文件的完整路径。Object完整路径中不能包含Bucket名称。
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, new File(localPath));

        // 上传文件。
        ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     *将文件以转为字节数组上传至阿里云
     *  参数:
     *  bucketName(String):指定存储空间名
     *  fileName(String):文件名
     *  headImg(MultipartFile):MultipartFile类型的文件
     * */
    public static void uploadByteFileAliyun(String bucketName, String objectName, MultipartFile headImg){

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId,accessKeySecret);

        // 填写Byte数组。

        byte[] bytes = new byte[0];
        try {
            bytes = headImg.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。
        ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));

        // 关闭OSSClient。
        ossClient.shutdown();

    }

    /**
     *将网络文件上传至阿里云 网络地址
     *  参数:
     *  bucketName(String):指定存储空间名
     *  fileName(String):文件名
     *  netPath(String):网络地址  https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png
     * */
    public static void internetFileUploads(String bucketName,String fileName,String netPath) throws IOException {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 填写网络流地址。
        InputStream inputStream = new URL(netPath).openStream();
        // 填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。
        ossClient.putObject(bucketName, fileName, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
    }


    /**
     *视频截取帧
     *  参数:
     *  bucketName(String):指定存储空间名
     *  fileName(String):文件名
     * */
    public static URL interceptCover(String bucketName,String objectName){

        // 填写视频文件所在的Bucket名称。
        //String bucketName = "yingx-2010";
        // 填写视频文件的完整路径。若视频文件不在Bucket根目录，需携带文件访问路径，例如examplefolder/videotest.mp4。
        //String objectName = "video/1622451747384-2020宣传视频.mp4";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 使用精确时间模式截取视频50s处的内容，输出为JPG格式的图片，宽度为800，高度为600。
        String style = "video/snapshot,t_2000,f_jpg,w_800,h_600";
        // 指定过期时间为10分钟。
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10 );
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, objectName, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        URL signedUrl = ossClient.generatePresignedUrl(req);
        System.out.println(signedUrl);

        // 关闭OSSClient。
        ossClient.shutdown();

        return signedUrl;
    }

    /**
     *视频截取帧并上传
     *  参数:
     *  bucketName(String):指定存储空间名
     *  objectName(String):要截取的视频的名字
     *  fileName(String):保存封面的名字
     * */
    public static URL interceptCoverUpload(String bucketName,String objectName,String fileName) throws IOException {

        // 填写视频文件所在的Bucket名称。
        //String bucketName = "yingx-2010";
        // 填写视频文件的完整路径。若视频文件不在Bucket根目录，需携带文件访问路径，例如examplefolder/videotest.mp4。
        //String objectName = "video/1622451747384-2020宣传视频.mp4";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 使用精确时间模式截取视频50s处的内容，输出为JPG格式的图片，宽度为800，高度为600。
        String style = "video/snapshot,t_2000,f_jpg,w_800,h_600";
        // 指定过期时间为10分钟。
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10 );
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, objectName, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        URL signedUrl = ossClient.generatePresignedUrl(req);
        System.out.println(signedUrl);

        // 填写网络流地址。
        InputStream inputStream = new URL(signedUrl.toString()).openStream();
        // 填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。
        ossClient.putObject(bucketName, fileName, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();

        return signedUrl;
    }

    public static void main(String[] args) {
        String bucketName="zhuc"; //指定存储空间名
        String fileName="video/1.jpg"; //文件名
        String localPath="C:\\Users\\86131\\Pictures\\Saved Pictures\\1.jpg";    //本地文件路径
        uploadlocalFileAliyun(bucketName,fileName,localPath);
    }
}
