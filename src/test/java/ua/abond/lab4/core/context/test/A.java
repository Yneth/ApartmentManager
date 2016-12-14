package ua.abond.lab4.core.context.test;

import ua.abond.lab4.core.annotation.Component;

@Component
public class A {
    private int a = 10;

    public A() {

    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }
}
