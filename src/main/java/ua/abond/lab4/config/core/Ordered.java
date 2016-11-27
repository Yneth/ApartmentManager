package ua.abond.lab4.config.core;

public interface Ordered {
    int HIGHEST_PRECEDENCE = Integer.MAX_VALUE;

    int LOWEST_PRECEDENCE = Integer.MAX_VALUE;

    int getOrder();
}
