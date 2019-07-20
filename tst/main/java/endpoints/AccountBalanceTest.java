package main.java.endpoints;

import main.java.model.Account;
import main.java.model.AccountFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AccountFactory.class)
public class AccountBalanceTest {

    AccountBalance testee;

    @Before
    public void setUp() {
        testee = new AccountBalance();
        PowerMockito.mockStatic(AccountFactory.class);
    }

    @Test
    public void testGetBalanceEndpointInvalidId() {
        when(AccountFactory.retrieveAccount(any(String.class))).thenReturn(Optional.empty());

        Response response = testee.getBalanceForAccount("dummy");

        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo().toEnum());
    }

    @Test
    public void testGetBalanceEndpointValidId() {
        Account mockAccount = Mockito.mock(Account.class);
        when(AccountFactory.retrieveAccount(any(String.class))).thenReturn(Optional.of(mockAccount));

        Response response = testee.getBalanceForAccount("dummy");

        assertEquals(Response.Status.OK, response.getStatusInfo().toEnum());
    }
}
