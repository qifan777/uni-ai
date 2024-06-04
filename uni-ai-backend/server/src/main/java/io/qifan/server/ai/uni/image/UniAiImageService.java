package io.qifan.server.ai.uni.image;

import org.springframework.ai.image.ImageModel;

import java.util.Map;

public interface UniAiImageService {
    ImageModel getImageMode(Map<String, Object> options);
}
