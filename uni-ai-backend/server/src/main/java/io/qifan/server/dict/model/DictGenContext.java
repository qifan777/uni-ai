package io.qifan.server.dict.model;

import io.qifan.server.dict.entity.Dict;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class DictGenContext {

    private List<String> dictTypes;
    private Map<String, List<Dict>> dictMap;
}
