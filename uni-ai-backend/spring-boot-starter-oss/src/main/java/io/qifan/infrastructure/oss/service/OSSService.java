package io.qifan.infrastructure.oss.service;

import java.io.InputStream;
import org.springframework.web.multipart.MultipartFile;

public interface OSSService {
    String upload(MultipartFile multipartFile);
    String upload(String objectName, InputStream inputStream);
}
