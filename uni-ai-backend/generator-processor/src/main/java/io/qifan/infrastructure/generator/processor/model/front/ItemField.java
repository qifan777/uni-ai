package io.qifan.infrastructure.generator.processor.model.front;

import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.infrastructure.generator.processor.model.common.FileModel;
import io.qifan.infrastructure.generator.processor.model.common.Type;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@Accessors(chain = true)
public class ItemField extends FileModel {

    private Type entityType;
    private String label;
    private String bind;
    private String fieldName;
    private String dictEnName;
    private ItemType itemType;
    private Boolean nullable;

    public ItemField() {
        label = "";
        bind = "";
        fieldName = "";
        dictEnName = "";
        itemType = ItemType.INPUT_TEXT;
    }

    public ItemField(Type entityType,
                     String label,
                     String bind,
                     String fieldName,
                     String dictEnName,
                     ItemType itemType,
                     Boolean nullable) {
        this.entityType = entityType;
        this.label = label;
        this.bind = bind;
        this.fieldName = fieldName;
        this.dictEnName = dictEnName;
        this.itemType = itemType;
        this.nullable = nullable;
    }

    public String bindToGetter() {
        String[] split = bind.split("\\.");
        List<String> list = Arrays.stream(split).map(s -> "get" + StringUtils.capitalize(s) + "()")
                .toList();
        return StringUtils.collectionToDelimitedString(list, ".");
    }


    @Override
    public Set<Type> getImportTypes() {
        return null;
    }

    @Override
    public String getFileName() {
        return "";
    }
}
