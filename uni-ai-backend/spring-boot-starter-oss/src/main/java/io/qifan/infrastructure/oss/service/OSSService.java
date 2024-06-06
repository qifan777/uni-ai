package io.qifan.infrastructure.oss.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface OSSService {
    String upload(MultipartFile multipartFile);
    String upload(String objectName, InputStream inputStream);
}
