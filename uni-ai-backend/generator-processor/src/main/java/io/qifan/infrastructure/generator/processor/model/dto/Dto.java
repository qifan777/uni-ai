package io.qifan.infrastructure.generator.processor.model.dto;

import io.qifan.infrastructure.generator.processor.model.common.Field;
import io.qifan.infrastructure.generator.processor.model.common.FileModel;
import io.qifan.infrastructure.generator.processor.model.common.Type;
import io.qifan.infrastructure.generator.processor.model.front.ItemField;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class Dto extends FileModel {

    private String sourcePath;
    private Type type;
    private Type entityType;
    private List<ItemField> fields;

    @Override
    public String getFileName() {
        return sourcePath + "/dto/" + entityType.getTypeName() + ".dto";
    }
}
