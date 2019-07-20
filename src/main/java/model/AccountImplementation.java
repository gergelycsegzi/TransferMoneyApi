package main.java.model;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

public class AccountImplementation implements Account{

    String accountId;
    AtomicReference<BigDecimal> balance;

    public AccountImplementation(String accountId) {
        this.accountId = accountId;
        balance = new AtomicReference<>();
        balance.set(BigDecimal.ZERO);
    }

    @Override
    public boolean subtract(BigDecimal amount) {
        if (amount.signum() < 0) {
            return false; // This really should be throwing an exception
        }

        BigDecimal currentBalance = balance.get();
        boolean enoughFunds = currentBalance.compareTo(amount) >= 0;

        while (enoughFunds) {
            if (subtractIfBalanceStaysTheSame(currentBalance, amount)) {
                return true;
            } else {
                currentBalance = balance.get();
                enoughFunds = currentBalance.compareTo(amount) >= 0;
            }
        }

        return false;
    }

    @Override
    public boolean add(BigDecimal amount) {
        if (amount.signum() < 0) {
            return false;
        }
        balance.accumulateAndGet(amount, BigDecimal::add);
        return true;
    }

    @Override
    public BigDecimal getBalance() {
        return balance.get();
    }

    private boolean subtractIfBalanceStaysTheSame(BigDecimal balanceAtInvocation, BigDecimal amount) {
        return balance.compareAndSet(balanceAtInvocation, balanceAtInvocation.subtract(amount));
    }
}
