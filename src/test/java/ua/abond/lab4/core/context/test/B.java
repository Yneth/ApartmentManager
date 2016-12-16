package ua.abond.lab4.core.context.test;

import ua.abond.lab4.core.annotation.Component;
import ua.abond.lab4.core.annotation.Inject;

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
