package ru.wallet.client.strategies;

import io.grpc.ManagedChannel;
import ru.wallet.server.User;

/**
 * • Withdraw 100 GBP
 * • Deposit 300 GPB
 * • Withdraw 100 GBP
 * • Withdraw 100 GBP
 * • Withdraw 100 GBP
 */

public class RoundB extends RoundStrategy {

    public User user;

    public RoundB(ManagedChannel channel) {
        super(channel);
    }

    @Override
    public void run() {
        user = createUser();

        takeDeposit(user, "100", "GBP");
        putDeposit(user, "300", "GBP");
        takeDeposit(user, "100", "GBP");
        takeDeposit(user, "100", "GBP");
        takeDeposit(user, "100", "GBP");

    }
}

