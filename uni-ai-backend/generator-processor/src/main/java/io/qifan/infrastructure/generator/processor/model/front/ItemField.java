package io.qifan.infrastructure.generator.processor.model.front;

import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.infrastructure.generator.processor.model.common.FileModel;
import io.qifan.infrastructure.generator.processor.model.common.Type;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ItemField extends FileModel {

    private Type entityType;
    private Type associationType;
    private String en;
    private String label;
    private String prop;
    private Integer order;
    private String fieldName;
    private String dictEnName;
    private ItemType itemType;
    private Boolean nullable;

    public ItemField() {
        label = "";
        prop = "";
        fieldName = "";
        dictEnName = "";
        itemType = ItemType.INPUT_TEXT;
    }

    public ItemField(Type entityType,
                     Type associationType,
                     String label,
                     String prop,
                     String fieldName,
                     String dictEnName,
                     ItemType itemType,
                     Boolean nullable) {
        this.entityType = entityType;
        this.associationType = associationType;
        this.label = label;
        this.prop = prop;
        this.fieldName = fieldName;
        this.dictEnName = dictEnName;
        this.itemType = itemType;
        this.nullable = nullable;
    }


    @Override
    public String getFileName() {
        return "";
    }
}
