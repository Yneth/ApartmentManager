package ua.abond.lab4.service;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public interface CurrencyConverterService {
    Money convert(Money money, CurrencyUnit unit);
}
