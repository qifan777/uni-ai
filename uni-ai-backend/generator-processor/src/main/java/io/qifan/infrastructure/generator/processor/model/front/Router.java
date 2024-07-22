package io.qifan.infrastructure.generator.processor.model.front;

import io.qifan.infrastructure.generator.processor.model.common.FileModel;
import io.qifan.infrastructure.generator.processor.model.common.Type;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Builder
@EqualsAndHashCode(callSuper = true)
@Data
public class Router extends FileModel {
    List<Type> entityList;

    @Override
    public String getFileName() {
        return "/front/router.ts";
    }
}
