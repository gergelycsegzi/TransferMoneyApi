package main.java.endpoints;

import main.java.model.Account;
import main.java.model.AccountFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("account")
public class AccountBalance {

    @Path("{accountId}/balance")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getBalanceForAccount(@PathParam("accountId") String accountId) {

        Optional<Account> accountObject = AccountFactory.retrieveAccount(accountId);

        if (accountObject.isPresent()) {
            return Response.ok(accountObject.get().getBalance()).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(),
                    "ERROR: Account ID provided is incorrect").build();
        }
    }
}
