package ua.abond.lab4.core.web.annotation;

import ua.abond.lab4.core.web.support.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnException {
    String value();

    RequestMethod method() default RequestMethod.GET;
}
