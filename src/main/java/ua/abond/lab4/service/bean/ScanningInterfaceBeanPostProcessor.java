package ua.abond.lab4.service.bean;

import ua.abond.lab4.core.BeanPostProcessor;
import ua.abond.lab4.core.ConfigurableBeanFactory;
import ua.abond.lab4.core.bean.BeanDefinition;
import ua.abond.lab4.core.exception.ImproperlyConfiguredException;
import ua.abond.lab4.core.util.reflection.ReflectionUtil;

import java.util.List;
import java.util.Map;

public abstract class ScanningInterfaceBeanPostProcessor<S, I> implements BeanPostProcessor {
    protected final Class<S> serviceType;
    protected final Class<I> interfaceLookup;

    public ScanningInterfaceBeanPostProcessor(Class<S> serviceType, Class<I> interfaceLookup) {
        this.serviceType = serviceType;
        this.interfaceLookup = interfaceLookup;
    }

    @Override
    public Object postProcessBeforeInitialization(ConfigurableBeanFactory factory, Object bean, String beanName) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(ConfigurableBeanFactory factory, Object bean, String simpleName) {
        Class<?> beanType = bean.getClass();
        if (interfaceLookup.isAssignableFrom(beanType)) {
            S service = getService(factory);
            List<Class<?>> genericTypeClasses = ReflectionUtil.getGenericTypeClasses(beanType);
            if (genericTypeClasses.isEmpty() || genericTypeClasses.size() > 1) {

            } else {
                register(service, simpleName, interfaceLookup.cast(bean), genericTypeClasses.get(0));
            }
        }
        return bean;
    }

    @SuppressWarnings("unchecked")
    private S getService(ConfigurableBeanFactory factory) {
        Map.Entry<String, BeanDefinition> entry =
                factory.getBeanDefinitionsOfType(serviceType).entrySet().stream().
                        findFirst().
                        orElseThrow(() -> new ImproperlyConfiguredException(""));
        return (S) factory.createBean(entry.getKey(), entry.getValue());
    }

    protected abstract void register(S service, String beanName, I bean, Class<?> type);
}
