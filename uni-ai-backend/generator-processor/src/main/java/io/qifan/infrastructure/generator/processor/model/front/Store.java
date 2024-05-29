package io.qifan.infrastructure.generator.processor.model.front;

import io.qifan.infrastructure.generator.processor.model.common.FileModel;
import io.qifan.infrastructure.generator.processor.model.common.Type;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class Store extends FileModel {

  Query query;
  private Type entityType;

    @Override
  public String getFileName() {

    return "/front/"+ entityType.toFrontNameCase() + "/store/" + entityType.toFrontNameCase()
        + "-store.ts";
  }
}
