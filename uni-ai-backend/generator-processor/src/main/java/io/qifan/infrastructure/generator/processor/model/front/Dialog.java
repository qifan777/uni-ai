package io.qifan.infrastructure.generator.processor.model.front;

import io.qifan.infrastructure.generator.processor.model.common.FileModel;
import io.qifan.infrastructure.generator.processor.model.common.Type;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class Dialog extends FileModel {

  private Type entityType;

    @Override
  public String getFileName() {
    return  "/front/"+ entityType.toFrontNameCase() + "/components/" + entityType.toFrontNameCase() + "-dialog.vue";
  }
}
