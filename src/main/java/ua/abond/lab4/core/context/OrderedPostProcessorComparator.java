package ua.abond.lab4.core.context;

import ua.abond.lab4.core.Ordered;

import java.util.Comparator;

public class OrderedPostProcessorComparator<T> implements Comparator<T> {

    @Override
    public int compare(T b1, T b2) {
        boolean isFirstOrdered = isImplementing(b1, Ordered.class);
        boolean isSecondOrdered = isImplementing(b2, Ordered.class);
        int o1 = isFirstOrdered ? ((Ordered) b1).getOrder() : 1;
        int o2 = isSecondOrdered ? ((Ordered) b2).getOrder() : -1;
        return Integer.compare(o1, o2);
    }

    private boolean isImplementing(T t, Class<?> type) {
        return type.isAssignableFrom(t.getClass());
    }
}
