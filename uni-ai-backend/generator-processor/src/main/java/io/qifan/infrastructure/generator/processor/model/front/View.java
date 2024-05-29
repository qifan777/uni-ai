package io.qifan.infrastructure.generator.processor.model.front;

import io.qifan.infrastructure.generator.processor.model.common.FileModel;
import io.qifan.infrastructure.generator.processor.model.common.Type;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class View extends FileModel {

    private Type entityType;

    @Override
    public String getFileName() {
        return "/front/" + entityType.toFrontNameCase() + "/" + entityType.toFrontNameCase() + "-view.vue";
    }
}
