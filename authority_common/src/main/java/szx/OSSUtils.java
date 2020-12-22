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



}
