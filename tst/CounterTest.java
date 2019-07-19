import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;
import main.java.Launcher;
import main.java.endpoints.Counter;
import org.apache.catalina.Server;
import org.glassfish.hk2.api.ServiceLocatorFactory;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.inject.hk2.ImmediateHk2InjectionManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import static org.junit.Assert.assertEquals;

@RunWith(ConcurrentTestRunner.class)
public class CounterTest {
    private Server server;
    private WebTarget target;
    private Counter counter = new Counter();
    private final static int THREAD_COUNT = 7;
    private final static int REPEATS = 100;

    @Before
    public void setUp() throws Exception {
        server = new Launcher().start();
        Client c = ClientBuilder.newClient();
        target = c.target("http://localhost:8080/transfer/");
    }
    @Test
    @ThreadCount(THREAD_COUNT)
    public void testAddOne() {
        // Increase likelihood of incorrect interleaving
        for (int i = 0; i < REPEATS; i++) {
            target.path("counter").request().post(Entity.json(null), String.class);
//            String responseMsg = target.path("counter").request().post(Entity.json(null), String.class);
//            System.out.println(responseMsg);
        }
    }
    @After
    public void testCount() throws Exception {
        assertEquals("Value wrong", THREAD_COUNT * REPEATS, counter.getCount());
        server.stop();
    }
}
