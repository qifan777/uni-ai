package io.qifan.ai.dashscope.api;

import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesis;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisParam;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisResult;
import com.alibaba.dashscope.utils.Constants;
import lombok.SneakyThrows;

public class DashScopeAiImageApi {
    public DashScopeAiImageApi(String apiKey) {
        Constants.apiKey = apiKey;
    }

    @SneakyThrows
    public ImageSynthesisResult imageGeneration(ImageSynthesisParam param) {
        ImageSynthesis is = new ImageSynthesis();
        return is.call(param);
    }
}
