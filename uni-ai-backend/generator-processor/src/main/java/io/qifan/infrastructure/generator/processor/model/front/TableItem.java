package io.qifan.infrastructure.generator.processor.model.front;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TableItem extends ItemField {
    public TableItem() {
        super();

    }

    public TableItem(ItemField itemField) {
        super(itemField.getEntityType(),
                itemField.getAssociationType(),
                itemField.getLabel(),
                itemField.getProp(),
                itemField.getFieldName(),
                itemField.getDictEnName(),
                itemField.getItemType(),
                itemField.getNullable());
    }
}
