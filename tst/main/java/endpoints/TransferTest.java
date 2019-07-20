package main.java.endpoints;

import main.java.model.Account;
import main.java.model.AccountFactory;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
@PrepareForTest({AccountFactory.class, NumberUtils.class})
public class TransferTest {

    private static final String DUMMY_1 = "dummy1";
    private static final String DUMMY_2 = "dummy2";
    private static final String VALID_AMOUNT_STRING = "10.5";
    private static final BigDecimal VALID_AMOUNT = new BigDecimal(VALID_AMOUNT_STRING);

    Transfer testee;

    @Before
    public void setUp() {
        testee = new Transfer();
        PowerMockito.mockStatic(AccountFactory.class);
        PowerMockito.mockStatic(NumberUtils.class);
    }

    @Test
    public void testTransferEndpointNotParsableAmount() {
        when(NumberUtils.isParsable(any(String.class))).thenReturn(false);

        Response response = testee.transfer(DUMMY_1, DUMMY_2, "dummy");

        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo().toEnum());
    }

    @Test
    public void testTransferEndpointInvalidIds() {
        when(NumberUtils.isParsable(any(String.class))).thenReturn(true);
        when(AccountFactory.retrieveAccount(any(String.class))).thenReturn(Optional.empty());

        Response response = testee.transfer(DUMMY_1, DUMMY_2, VALID_AMOUNT_STRING);

        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo().toEnum());
    }

    @Test
    public void testTransferEndpointValidParamsInvalidTransfer() {
        Account mockAccount = Mockito.mock(Account.class);

        when(NumberUtils.isParsable(any(String.class))).thenReturn(true);
        when(AccountFactory.retrieveAccount(any(String.class))).thenReturn(Optional.of(mockAccount));
        when(mockAccount.subtract(VALID_AMOUNT)).thenReturn(false);

        Response response = testee.transfer(DUMMY_1, DUMMY_2, VALID_AMOUNT_STRING);

        assertEquals(Response.Status.CONFLICT, response.getStatusInfo().toEnum());
    }

    @Test
    public void testTransferEndpointValidParamsValidTransfer() {
        Account mockAccount = Mockito.mock(Account.class);

        when(NumberUtils.isParsable(any(String.class))).thenReturn(true);
        when(AccountFactory.retrieveAccount(any(String.class))).thenReturn(Optional.of(mockAccount));
        when(mockAccount.subtract(VALID_AMOUNT)).thenReturn(true);

        Response response = testee.transfer(DUMMY_1, DUMMY_2, VALID_AMOUNT_STRING);

        assertEquals(Response.Status.OK, response.getStatusInfo().toEnum());
    }
}
