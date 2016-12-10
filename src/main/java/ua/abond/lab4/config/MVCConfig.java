package ua.abond.lab4.config;

import ua.abond.lab4.config.core.annotation.Bean;
import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.config.core.annotation.ComponentScan;
import ua.abond.lab4.service.bean.RequestMapperBeanPostProcessor;
import ua.abond.lab4.service.bean.ValidatorBeanPostProcessor;

@Component
@ComponentScan({"ua.abond.lab4.service.impl", "ua.abond.lab4.web"})
public class MVCConfig {

    @Bean
    public RequestMapperBeanPostProcessor getRequestMapperBeanPostProcessor() {
        return new RequestMapperBeanPostProcessor();
    }

    @Bean
    public ValidatorBeanPostProcessor getValidatorBeanPostProcessor() {
        return new ValidatorBeanPostProcessor();
    }
}
