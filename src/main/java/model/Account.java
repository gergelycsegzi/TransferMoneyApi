package main.java.model;

import java.math.BigDecimal;

public interface Account {

    boolean subtract(BigDecimal amount);

    boolean add(BigDecimal amount);

    BigDecimal getBalance();
}
