package ua.abond.lab4.domain;

import java.util.Objects;

public class Bill {
    private int payedAmount;
    private int amountToPay;

    public Bill(int payedAmount, int amountToPay) {
        this.payedAmount = payedAmount;
        this.amountToPay = amountToPay;
    }

    public int getPayedAmount() {
        return payedAmount;
    }

    public void setPayedAmount(int payedAmount) {
        this.payedAmount = payedAmount;
    }

    public int getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(int amountToPay) {
        this.amountToPay = amountToPay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bill)) return false;
        Bill bill = (Bill) o;
        return getPayedAmount() == bill.getPayedAmount() &&
                getAmountToPay() == bill.getAmountToPay();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPayedAmount(), getAmountToPay());
    }
}
