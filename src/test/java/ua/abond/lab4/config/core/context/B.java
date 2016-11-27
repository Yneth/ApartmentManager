package ua.abond.lab4.config.core.context;

import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.config.core.annotation.Inject;

@Component
public class B {
    @Inject
    private A a1;
    private A a;

    @Inject
    public B(A a) {
        this.a = a;
    }

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    public A getA1() {
        return a1;
    }
}
