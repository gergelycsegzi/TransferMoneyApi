package main.java.model;

import java.math.BigDecimal;

public interface Account {

    boolean subtract(BigDecimal amount);

    void add(BigDecimal amount);

    BigDecimal getBalance();
}
