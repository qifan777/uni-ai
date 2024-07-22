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
public class Details extends FileModel {
    private Type entityType;
    private List<FormItem> formItems;

    @Override
    public String getFileName() {
        return "/front/" + entityType.toFrontNameCase() + "/" + entityType.toFrontNameCase()
               + "-details-view.vue";
    }
}
