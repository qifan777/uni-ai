package io.qifan.infrastructure.generator.processor.model.front;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FormItem extends ItemField {
    public FormItem() {
        super();
    }

    public FormItem(ItemField itemField) {
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
