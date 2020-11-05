package com.offcn.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @ClassName OssTemplate
 * @Deacription TODO
 * @Author 葛言超
 * @Date 2020/11/3 16:47
 * version:1.0 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class OssTemplate {
    private String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    private String bucketDomain = "gyc1012.oss-cn-beijing.aliyuncs.com";
    private String accessKeyId = "LTAI4GK8qey6Dq78FmdznRjR";
    private String accessKeySecret = "r8JHnicr7bXFb1GeL8DzpiV4YBXOXz";
    private String bucketName = "gyc1012";

    public String upload(InputStream inputStream, String fileName){

        System.out.println("fileName : " + fileName);

        //1、加工文件夹和文件名
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String folderName = sdf.format(new Date());
        fileName = UUID.randomUUID().toString().replace("-","")+"_"+fileName;

        //2、创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        //3、// 上传文件流，指定bucket的名称
        ossClient.putObject(bucketName,"pic/"+folderName+"/"+fileName,inputStream);

        //4、关闭资源
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ossClient.shutdown();
        String url= "https://"+bucketDomain+"/pic/"+folderName+"/"+fileName;
        System.out.println("上传文件访问路径:"+url);
        return url;
    }
}
