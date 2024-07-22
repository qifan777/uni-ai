package io.qifan.infrastructure.generator.processor.processor;

import io.qifan.infrastructure.generator.processor.model.front.Router;
import io.qifan.infrastructure.generator.processor.utils.TypeUtils;
import io.qifan.infrastructure.generator.processor.writer.ModelWriter;

import java.util.List;
import java.util.Set;

public class RouterCreateProcessor {
    public void porocess(Set<Class<?>> entities, String outputPath) {
        ModelWriter modelWriter = new ModelWriter(outputPath);
        Router router = Router.builder()
                .entityList(entities.stream().map(TypeUtils::getType)
                        .toList())
                .build();
        modelWriter.writeModel(router, false);
    }
}
