package ru.wallet.client.strategies;

import io.grpc.ManagedChannel;
import ru.wallet.server.User;

/**
 * • Deposit 100 USD
 * • Withdraw 200 USD
 * • Deposit 100 EUR
 * • Get Balance
 * • Withdraw 100 USD
 * • Get Balance
 * • Withdraw 100 USD
 */

public class RoundA extends RoundStrategy {

    public User user;

    public RoundA(ManagedChannel channel) {
        super(channel);
    }


    @Override
    public void run() {

        user = createUser();
        putDeposit(user, "100", "USD");
        takeDeposit(user, "200", "USD");
        putDeposit(user, "100", "USD");
        getBalance(user);
        takeDeposit(user, "100", "USD");
        getBalance(user);
        takeDeposit(user, "100", "USD");
    }
}
