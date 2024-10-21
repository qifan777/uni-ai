package io.qifan.infrastructure.oss.aliyun;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.model.*;
import io.qifan.infrastructure.oss.service.OSSService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class AliYunOSSService implements OSSService {
    private final AliYunOSSProperties aliYunOSSProperties;

    public AliYunOSSService(AliYunOSSProperties aliYunOSSProperties) {
        this.aliYunOSSProperties = aliYunOSSProperties;
    }

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
        String objectName = new URL(url).getPath();
        getAliYunOSS().deleteObject(aliYunOSSProperties.getBucketName(), objectName);
        return true;
    }

    public String basicUpload(String objectName, InputStream inputStream) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(aliYunOSSProperties.getBucketName(),
                objectName,
                inputStream);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        metadata.setObjectAcl(CannedAccessControlList.PublicRead);
        putObjectRequest.setMetadata(metadata);
        PutObjectResult putObjectResult = getAliYunOSS().putObject(putObjectRequest);
        return "https://" + aliYunOSSProperties.getBucketName() + "." +
               aliYunOSSProperties.getEndpoint().replace("https://", "") +
               "/" + objectName;
    }

    public OSS getAliYunOSS() {
        return new OSSClientBuilder().build(aliYunOSSProperties.getEndpoint(),
                aliYunOSSProperties.getAccessKeyId(),
                aliYunOSSProperties.getAccessKeySecret());
    }
}
