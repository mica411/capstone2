package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Balance {

    BigDecimal balance;

    public Balance() {

    }

    public Balance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Balance: $" + balance;
    }
}
