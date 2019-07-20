package main.java.model;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(ConcurrentTestRunner.class)
public class AccountAddThreadSafetyTest {

    private static final String ACCOUNT_ID = "dummy";
    private final static int THREAD_COUNT = 10;
    private final static int REPEATS = 100;
    private static final int TO_ADD = 15;

    Account testee;

    @Before
    public void setUp() {
        testee = new AccountImplementation(ACCOUNT_ID);
    }

    @Test
    @ThreadCount(THREAD_COUNT)
    public void addWithMultipleThreads() {
        // Use repeats to increase likelihood of incorrect interleaving
        for (int i = 0; i < REPEATS; i++) {
            testee.add(new BigDecimal(TO_ADD));
        }
    }

    @After
    public void testAddResultThreadSafe() {
        assertEquals(new BigDecimal(TO_ADD * REPEATS * THREAD_COUNT), testee.getBalance());
    }
}
