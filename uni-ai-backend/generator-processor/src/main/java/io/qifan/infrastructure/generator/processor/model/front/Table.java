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
public class Table extends FileModel {

    private Type entityType;
    private List<TableItem> tableItems;

    @Override
    public String getFileName() {
        return "/front/" + entityType.toFrontNameCase() + "/components/" + entityType.toFrontNameCase() + "-table.vue";
    }
}