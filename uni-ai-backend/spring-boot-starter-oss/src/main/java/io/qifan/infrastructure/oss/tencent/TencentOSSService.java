package io.qifan.infrastructure.oss.tencent;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import io.qifan.infrastructure.oss.service.OSSService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@AllArgsConstructor
public class TencentOSSService implements OSSService {

    private final TencentOSSProperties properties;

    @SneakyThrows
    @Override
    public String upload(MultipartFile multipartFile) {
        String filename = multipartFile.getOriginalFilename();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String objectName = sdf.format(new Date()) + filename;
        return basicUpload(objectName, multipartFile.getInputStream());
    }

    @Override
    public String upload(String objectName, InputStream inputStream) {
        return basicUpload(objectName, inputStream);
    }

    @SneakyThrows
    @Override
    public Boolean delete(String url) {
        String objectName = new URL(url).getPath().substring(1);
        getTencentOSS().deleteObject(properties.getBucket(), objectName);
        return true;
    }

    public String basicUpload(String objectName, InputStream inputStream) {
        // 指定要上传的文件
        // 指定文件将要存放的存储桶
        String bucketName = properties.getBucket();
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为 folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream, new ObjectMetadata());
        COSClient tencentOSS = getTencentOSS();
        PutObjectResult putObjectResult = tencentOSS.putObject(putObjectRequest);
        return tencentOSS.getObjectUrl(bucketName, objectName).toString();
    }

    public COSClient getTencentOSS() {
        COSCredentials cred = new BasicCOSCredentials(properties.getSecretId(), properties.getSecretKey());
        Region region = new Region(properties.getRegion());
        ClientConfig clientConfig = new ClientConfig(region);
        return new COSClient(cred, clientConfig);
    }
}
