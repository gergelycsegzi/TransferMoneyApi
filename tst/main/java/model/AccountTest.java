package main.java.model;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccountTest {

    private static final BigDecimal NEGATIVE_TEN = BigDecimal.TEN.negate();

    Account testee;

    @Before
    public void setUp() {
        testee = new AccountImplementation("dummy");
    }

    @Test
    public void testGetBalanceAndAddValid() {
        assertEquals(BigDecimal.ZERO, testee.getBalance());

        BigDecimal toAdd = BigDecimal.TEN;

        testee.add(toAdd);

        assertEquals(toAdd, testee.getBalance());
    }

    @Test
    public void testAddInvalid() {
        assertFalse(testee.add(NEGATIVE_TEN));
    }

    @Test
    public void testSubtractInvalid() {
        assertFalse(testee.subtract(NEGATIVE_TEN));

        BigDecimal moreThanStartingBalance = BigDecimal.TEN;
        assertFalse(testee.subtract(moreThanStartingBalance));
    }

    @Test
    public void testSubtractValid() {
        testee.add(BigDecimal.TEN);
        assertTrue(testee.subtract(BigDecimal.ONE));

        assertEquals(new BigDecimal(9), testee.getBalance());
    }

}
