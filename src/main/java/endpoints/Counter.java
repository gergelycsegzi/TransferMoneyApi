package main.java.endpoints;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicInteger;

@Path("counter")
public class Counter {
    private static AtomicInteger i = new AtomicInteger(0);

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String addOne() {
        return Integer.toString(i.incrementAndGet());
    }

    public int getCount() {
        return i.get();
    }
}
