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

    private static Map<String, Account> accounts;
    static{
        Random rand = new Random();
        accounts = new HashMap<>();
        for (String accountId : accountIds) {
            Account account = new AccountImplementation(accountId);
            account.add(new BigDecimal(rand.nextDouble() * 100));
            accounts.put(accountId, account);
        }
    }

    /**
     * This could be a call to the DB, but in our case it is only going
     * to check against a list of accounts kept in memory.
     * @param accountId
     * @return account for given id
     */
    public static Optional<Account> retrieveAccount(String accountId) {
        if (accountIds.contains(accountId)) {
            return Optional.of(accounts.get(accountId));
        }

        return Optional.empty();
    }

}
