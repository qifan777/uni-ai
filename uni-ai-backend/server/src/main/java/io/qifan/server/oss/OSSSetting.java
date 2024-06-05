package io.qifan.server.oss;

import io.qifan.server.dict.model.DictConstants;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
public class OSSSetting {
    DictConstants.OssType type;
    Map<String, Object> options;
}
