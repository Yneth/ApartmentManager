package ua.abond.lab4.config.core.bean;

import org.apache.log4j.Logger;
import ua.abond.lab4.config.core.BeanPostProcessor;
import ua.abond.lab4.config.core.ConfigurableBeanFactory;
import ua.abond.lab4.config.core.annotation.Prop;
import ua.abond.lab4.config.core.annotation.Value;
import ua.abond.lab4.config.core.exception.ImproperlyConfiguredException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class PropAnnotationBeanPostProcessor implements BeanPostProcessor {
    private static final Logger logger = Logger.getLogger(PropAnnotationBeanPostProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(ConfigurableBeanFactory factory, Object bean, String beanName) {
        Class<?> type = bean.getClass();
        if (type.isAnnotationPresent(Prop.class)) {
            logger.debug(String.format("Found bean '%s' to inject properties to.", beanName));
            Prop prop = type.getAnnotation(Prop.class);

            Properties properties = new Properties();
            Arrays.stream(prop.value()).
                    peek(path -> logger.debug(String.format("Loading property '%s' for '%s'", path, beanName))).
                    forEach(path -> load(properties, path));

            List<Field> collect = Arrays.stream(type.getDeclaredFields()).
                    filter(field -> field.isAnnotationPresent(Value.class)).
                    collect(Collectors.toList());

            if (collect.isEmpty()) {
                logger.warn(String.format(
                        "You have annotated bean '%s' wih @Prop annotation but there is no fields to inject values to.",
                        beanName
                ));
            }
            collect.forEach(f -> inject(f, bean, beanName, properties));
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(ConfigurableBeanFactory factory, Object bean, String simpleName) {
        return bean;
    }

    private void load(Properties properties, String path) {
        try {
            String relativePath = path.startsWith("/") ? path : "/" + path;
            InputStream inputStream = this.getClass().getResourceAsStream(relativePath);
            if (inputStream == null) {
                throw new FileNotFoundException(String.format("Could not find property file: %s", path));
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new ImproperlyConfiguredException("Failed to load property.", e);
        }
    }

    private void inject(Field f, Object bean, String beanName, Properties prop) {
        logger.debug(String.format("Setting field '%s' of '%s' bean.", f.getName(), beanName));
        Value annotation = f.getAnnotation(Value.class);
        try {
            if (!f.isAccessible()) {
                f.setAccessible(true);
            }
            f.set(bean, prop.get(annotation.value()));
        } catch (IllegalAccessException e) {
            logger.error(String.format("Failed to set field %s of %s.", f.getName(), beanName), e);
        }
    }
}
