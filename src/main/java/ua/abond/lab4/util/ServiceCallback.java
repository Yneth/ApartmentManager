package ua.abond.lab4.util;

import ua.abond.lab4.service.exception.ServiceException;

public interface ServiceCallback<T> {
    T call() throws ServiceException;
}
