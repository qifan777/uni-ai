package io.qifan.server.oss;

import io.qifan.infrastructure.oss.service.OSSService;

import java.util.Map;

public interface UniOSSService {
    OSSService getOSSService(Map<String, Object> oss);
}
