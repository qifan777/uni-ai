package io.qifan.ai.dashscope;

import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisParam;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisResult;
import io.qifan.ai.dashscope.api.DashScopeAiImageApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.image.*;
import org.springframework.ai.model.ModelOptionsUtils;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class DashScopeAiImagModel implements ImageModel {
    private final DashScopeAiImageApi imageApi;
    private final DashScopeAiImageOptions defaultOptions;

    @Override
    public ImageResponse call(ImagePrompt request) {
        return toResponse(imageApi.imageGeneration(toParam(request)));
    }

    public ImageResponse toResponse(ImageSynthesisResult result) {
        List<ImageGeneration> generations = result.getOutput().getResults().stream().map(row -> {
            String url = row.get("url");
            return new ImageGeneration(new Image(url, null));
        }).toList();
        return new ImageResponse(generations);
    }

    public ImageSynthesisParam toParam(ImagePrompt prompt) {
        DashScopeAiImageOptions options = new DashScopeAiImageOptions();
        if (defaultOptions != null) {
            options = ModelOptionsUtils.merge(defaultOptions, options, DashScopeAiImageOptions.class);
        }
        if (prompt.getOptions() != null) {
            options = ModelOptionsUtils.merge(prompt.getOptions(), options, DashScopeAiImageOptions.class);
        }
        return ImageSynthesisParam.builder()
                .model(options.getModel())
                .n(options.getN())
                .size(options.getHeight() + "*" + options.getWidth())
                .prompt(prompt.getInstructions().get(0).getText())
                .build();
    }
}
