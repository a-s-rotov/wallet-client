package ru.wallet.client.strategies;

import io.grpc.ManagedChannel;
import ru.wallet.server.User;

/**
 * • Get Balance
 * • Deposit 100 USD
 * • Deposit 100 USD
 * • Withdraw 100 USD
 * • Depsoit 100 USD
 * • Get Balance
 * • Withdraw 200 USD
 * • Get Balance
 */

public class RoundC extends RoundStrategy {

    public User user;

    public RoundC(ManagedChannel channel) {
        super(channel);
    }

    @Override
    public void run() {
        user = createUser();

        getBalance(user);
        putDeposit(user, "100", "USD");
        putDeposit(user, "100", "USD");
        takeDeposit(user, "100", "USD");
        getBalance(user);
        takeDeposit(user, "200", "USD");
        getBalance(user);

    }
}

