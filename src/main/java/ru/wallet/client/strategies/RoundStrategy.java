package ru.wallet.client.strategies;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.wallet.server.Deposit;
import ru.wallet.server.User;
import ru.wallet.server.WalletServiceGrpc;


public abstract class RoundStrategy {

    public final WalletServiceGrpc.WalletServiceBlockingStub blockService;
    public final WalletServiceGrpc.WalletServiceStub service;
    Logger log = LoggerFactory.getLogger(RoundStrategy.class.getName());


    public RoundStrategy(ManagedChannel channel) {
        blockService = WalletServiceGrpc.newBlockingStub(channel);
        service = WalletServiceGrpc.newStub(channel);
    }

    public User createUser() {
        return blockService.createUser(Empty.newBuilder().build());
    }

    public void putDeposit(User user, String amount, String currency) {
        blockService.putDeposit(Deposit.newBuilder()
                .setUser(user)
                .setAmount(amount)
                .setCurrency(currency)
                .build());
        log.info("Put deposit {} {}  %n%n", amount, currency);
        log.info("-----");
    }

    public void takeDeposit(User user, String amount, String currency) {
        try {
            blockService.takeDeposit(Deposit.newBuilder()
                    .setUser(user)
                    .setAmount(amount)
                    .setCurrency(currency)
                    .build());
            log.info("Withdraw deposit {} {} %n%n", amount, currency);
            log.info("-----");
        } catch (StatusRuntimeException e) {
            log.error("Withdraw deposit {} {} error  %n%n", amount, currency);
            log.error("-----");

        }
    }

    public void getBalance(User user) {
        blockService.getBalance(user).toString();
        log.info(blockService.getBalance(user).toString());
        log.info("-----");
    }

    public abstract void run();
}
