package ua.abond.lab4.service.impl;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import ua.abond.lab4.service.CurrencyConverterService;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyConverterServiceImpl implements CurrencyConverterService {

    @Override
    public Money convert(Money money, CurrencyUnit unit) {
        return money.convertedTo(unit, new BigDecimal("21"), RoundingMode.HALF_UP);
    }
}
