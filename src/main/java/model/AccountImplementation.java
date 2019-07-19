package main.java.model;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

public class AccountImplementation implements Account{

    AtomicReference<BigDecimal> balance;
    String accountId;

    public AccountImplementation(String accountId) {
        this.accountId = accountId;
        balance = new AtomicReference<>();
        balance.set(BigDecimal.ZERO);
    }

    @Override
    public boolean subtract(BigDecimal amount) {
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
    public void add(BigDecimal amount) {
        balance.accumulateAndGet(amount, BigDecimal::add);
    }

    @Override
    public BigDecimal getBalance() {
        return balance.get();
    }

    private boolean subtractIfBalanceStaysTheSame(BigDecimal balanceAtInvocation, BigDecimal amount) {
        return balance.compareAndSet(balanceAtInvocation, balanceAtInvocation.subtract(amount));
    }
}
