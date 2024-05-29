package io.qifan.infrastructure.generator.processor.model.front;

import io.qifan.infrastructure.generator.processor.model.common.FileModel;
import io.qifan.infrastructure.generator.processor.model.common.Type;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class UpdateForm extends FileModel {

    private Type entityType;
    private List<FormItem> formItems;

    @Override
    public String getFileName() {
        return "/front/" + entityType.toFrontNameCase() + "/components/" + entityType.toFrontNameCase()
                + "-update-form.vue";
    }
}
