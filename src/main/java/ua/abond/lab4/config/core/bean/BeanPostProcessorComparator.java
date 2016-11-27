package ua.abond.lab4.config.core.bean;

import ua.abond.lab4.config.core.BeanPostProcessor;
import ua.abond.lab4.config.core.Ordered;

import java.util.Comparator;

public class BeanPostProcessorComparator implements Comparator<BeanPostProcessor> {

    @Override
    public int compare(BeanPostProcessor b1, BeanPostProcessor b2) {
        boolean isFirstOrdered = isImplementing(b1, Ordered.class);
        boolean isSecondOrdered = isImplementing(b2, Ordered.class);
        if (isFirstOrdered && isSecondOrdered) {
            Ordered o1 = (Ordered) b1;
            Ordered o2 = (Ordered) b2;
            return Integer.compare(o1.getOrder(), o2.getOrder());
        } else if (isFirstOrdered) {
            return -1;
        } else if (isSecondOrdered) {
            return 1;
        }
        return 0;
    }

    private boolean isImplementing(BeanPostProcessor bpp, Class<?> type) {
        return type.isAssignableFrom(bpp.getClass());
    }
}
