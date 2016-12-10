package ua.abond.lab4.service.bean;

import ua.abond.lab4.service.RequestMapperService;
import ua.abond.lab4.web.mapper.RequestMapper;

public class RequestMapperBeanPostProcessor
        extends ScanningInterfaceBeanPostProcessor<RequestMapperService, RequestMapper> {

    public RequestMapperBeanPostProcessor() {
        super(RequestMapperService.class, RequestMapper.class);
    }

    @Override
    protected void register(RequestMapperService service, String beanName, RequestMapper bean, Class<?> type) {
        service.register(type, bean);
    }
}
