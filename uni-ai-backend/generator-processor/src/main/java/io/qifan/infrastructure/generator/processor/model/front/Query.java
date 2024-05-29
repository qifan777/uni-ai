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
public class Query extends FileModel {

  private Type entityType;
  private List<QueryItem> queryItems;

    @Override
  public String getFileName() {
    return "/front/"+ entityType.toFrontNameCase() + "/components/" + entityType.toFrontNameCase() + "-query.vue";
  }
}
