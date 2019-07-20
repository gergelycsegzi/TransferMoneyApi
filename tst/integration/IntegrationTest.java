package integration;

import main.java.Launcher;
import main.java.model.Account;
import main.java.model.AccountFactory;
import org.apache.catalina.Server;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {
    private static final String ACCOUNT_FROM = "from";
    private static final String ACCOUNT_TO = "to";
    private static final String STARTING_BALANCE = "15.2";
    private static final String LARGER_THAN_STARTING_BALANCE = "16";

    private Server server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
       setUpServer();
       setUpAccounts();
    }

    public void setUpServer() throws Exception {
        server = new Launcher().start();
        Client c = ClientBuilder.newClient();
        target = c.target("http://localhost:8080/");
    }

    public void setUpAccounts() {
        Account from = AccountFactory.createAccount(ACCOUNT_FROM);
        AccountFactory.createAccount(ACCOUNT_TO);

        from.add(new BigDecimal(STARTING_BALANCE));
    }

    @Test
    public void testTransferApi() {
        String balance = target.path("account/" + ACCOUNT_FROM + "/balance").request().get().readEntity(String.class);

        assertEquals(STARTING_BALANCE, balance);

        Response.Status transferResponseStatus = target.path(transferPathFor(LARGER_THAN_STARTING_BALANCE)).request()
                .post(Entity.json(null)).getStatusInfo().toEnum();

        assertEquals(Response.Status.CONFLICT, transferResponseStatus);

        balance = target.path("account/" + ACCOUNT_FROM + "/balance").request().get().readEntity(String.class);

        assertEquals(STARTING_BALANCE, balance);

        transferResponseStatus = target.path(transferPathFor(STARTING_BALANCE)).request().post(Entity.json(null))
                .getStatusInfo().toEnum();

        assertEquals(Response.Status.OK, transferResponseStatus);

        String accountToBalance = target.path("account/" + ACCOUNT_TO + "/balance").request().get()
                .readEntity(String.class);
        String accountFromBalance = target.path("account/" + ACCOUNT_FROM + "/balance").request().get()
                .readEntity(String.class);

        assertEquals(STARTING_BALANCE, accountToBalance);
        assertEquals(0, BigDecimal.ZERO.compareTo(new BigDecimal(accountFromBalance)));

    }

    private static String transferPathFor(String amount) {
        return "transfer/from/" + ACCOUNT_FROM + "/to/" + ACCOUNT_TO + "/amount/" + amount;
    }
}
