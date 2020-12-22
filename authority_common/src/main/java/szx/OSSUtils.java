package szx;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class OSSUtils  {
    // Endpoint以杭州为例，其它Region请按实际情况填写。
    static final String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
    static final String accessKeyId = "LTAI4GGXkoF4SKiRZNLs2TuP";
    static final String accessKeySecret = "gto8a60dAhI9wKyyy5KpSLBao87Iyt";
    static final String bucketName = "cut192623";


    public void  ossUpload(String fileName,MultipartFile file) throws Exception{
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName,fileName,file.getInputStream());
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    public boolean ifExist(String fileName){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 判断文件是否存在。doesObjectExist还有一个参数isOnlyInOSS，如果为true则忽略302重定向或镜像；如果为false，则考虑302重定向或镜像。
        boolean found = ossClient.doesObjectExist(bucketName, fileName);
        System.out.println(found);

        // 关闭OSSClient。
        ossClient.shutdown();
        return found;
    }

    public void delete(String fileName){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, fileName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }



}
