package main.java.endpoints;

import main.java.model.Account;
import main.java.model.AccountFactory;
import org.apache.commons.lang3.math.NumberUtils;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Optional;

@Path("transfer")
public class Transfer {

    @Path("from/{accountFrom}/to/{accountTo}/amount/{amount}")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response transfer(@PathParam("accountFrom") String accountFrom, @PathParam("accountTo") String accountTo,
                             @PathParam("amount") String amountString) {

        if (!NumberUtils.isParsable(amountString)) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(),
                    "ERROR: Amount provided is not correct number format").build();
        }

        if (accountFrom.equals(accountTo)) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(),
                    "ERROR: Same account provided for source and target").build();
        }

        BigDecimal amount = new BigDecimal(amountString);

        Optional<Account> accountFromObject = AccountFactory.retrieveAccount(accountFrom);
        Optional<Account> accountToObject = AccountFactory.retrieveAccount(accountTo);

        if (accountFromObject.isPresent() && accountToObject.isPresent()) {

            boolean transferPossible = accountFromObject.get().subtract(amount);

            if (transferPossible) {
                accountToObject.get().add(amount);
                return Response.ok("SUCCESS").build();
            } else {
                return Response.status(Response.Status.CONFLICT.getStatusCode(),
                        "ERROR: Transfer cannot be carried out").build();
            }

        } else {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(),
                    "ERROR: One or both account IDs provided are incorrect").build();
        }
    }
}

