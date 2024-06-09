package io.qifan.server.ai.plugin.entity.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Parameter {
    String type;
    Map<String, Property> properties;
    List<String> required;

    @Data
    public static class Property {
        String type;
        String description;
    }
}
