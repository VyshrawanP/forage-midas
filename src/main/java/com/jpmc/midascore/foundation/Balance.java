package com.jpmc.midascore.foundation;

import java.math.BigDecimal;

public class Balance {
    private String userId;
    private BigDecimal balance;

    public Balance(String userId, BigDecimal balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public String getUserId() {
        return userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "userId='" + userId + '\'' +
                ", balance=" + balance +
                '}';
    }
}
