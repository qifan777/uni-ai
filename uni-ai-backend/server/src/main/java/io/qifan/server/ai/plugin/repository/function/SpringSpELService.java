package io.qifan.server.ai.plugin.repository.function;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.function.Function;

@Slf4j
public class SpringSpELService implements Function<SpringSpELService.Request, SpringSpELService.Response> {

    @Override
    public Response apply(Request request) {
        log.info("SpringSpELService apply request: {}", request);
        SpelExpressionParser parser = new SpelExpressionParser();
        return new Response(parser.parseExpression(request.expression).getValue().toString());
    }

    public record Request(
            @JsonProperty(required = true, value = "expression") @JsonPropertyDescription(value = "表达式字符串") String expression) {
    }

    public record Response(String result) {
    }
}
