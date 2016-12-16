package ua.abond.lab4.service.bean;

import ua.abond.lab4.service.ValidationService;
import ua.abond.lab4.service.Validator;

public class ValidatorBeanPostProcessor
        extends ScanningInterfaceBeanPostProcessor<ValidationService, Validator> {

    public ValidatorBeanPostProcessor() {
        super(ValidationService.class, Validator.class);
    }

    @Override
    protected void register(ValidationService service, String beanName, Validator bean, Class<?> type) {
        service.register(type, bean);
    }
}
