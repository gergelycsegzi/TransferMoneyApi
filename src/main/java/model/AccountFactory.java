package main.java.model;

import com.google.common.collect.ImmutableSet;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

public class AccountFactory {

    // These are example IDs as I don't implement account creation
    private static Set<String> accountIds = ImmutableSet.of(
        "123GC",
        "ABC",
        "testAccount",
        "456"
    );

    // These are example accounts for the sake of manual testing
    private static Map<String, Account> accounts;
    static{
        Random rand = new Random();
        accounts = new HashMap<>();
        for (String accountId : accountIds) {
            Account account = createAccount(accountId);
            // set random starting balance above 10 to guarantee test transfer
            account.add(new BigDecimal(rand.nextDouble() * 100 + 10));
        }
    }

    /**
     * This could be a call to the DB, but in our case it is only going
     * to check against a list of accounts kept in memory.
     * @param accountId
     * @return account for given id
     */
    public static Optional<Account> retrieveAccount(String accountId) {
        if (accounts.containsKey(accountId)) {
            return Optional.of(accounts.get(accountId));
        }

        return Optional.empty();
    }

    // This method is not exposed in the API
    public static Account createAccount(String accountId) {
        Account account = new AccountImplementation(accountId);
        accounts.put(accountId, account);
        return account;
    }

}
