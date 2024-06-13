package io.qifan.server.ai.plugin.service.function;

import org.springframework.ai.model.function.FunctionCallbackContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class FunctionRegistry {

    @Bean
    @Description(value = "执行符合SpringSpEL语法的表达式")
    public Function<SpringSpELService.Request, SpringSpELService.Response> springSpELFunction() {
        return new SpringSpELService();
    }

    @Bean
    public FunctionCallbackContext springAiFunctionManager(ApplicationContext context) {
        FunctionCallbackContext manager = new FunctionCallbackContext();
        manager.setApplicationContext(context);
        return manager;
    }
}
