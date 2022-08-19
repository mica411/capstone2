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

    //Needs more work
    @Override
    public String toString() {
        return String.valueOf(balance);
    }
}
