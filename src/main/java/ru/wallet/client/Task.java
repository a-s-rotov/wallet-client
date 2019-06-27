package ru.wallet.client;

import io.grpc.ManagedChannel;
import ru.wallet.client.strategies.RoundA;
import ru.wallet.client.strategies.RoundB;
import ru.wallet.client.strategies.RoundC;
import ru.wallet.client.strategies.RoundStrategy;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

public class Task implements Runnable {
    private CountDownLatch latch;
    private Queue<RoundStrategy> strategyQueue;


    public Task(int size, ManagedChannel channel, CountDownLatch cdl) {
        strategyQueue = new ArrayDeque<>();
        latch = cdl;

        for (int i = 0; i < size; i++) {
            switch (i % 3) {
                case 0:
                    strategyQueue.add(new RoundA(channel));
                    break;
                case 1:
                    strategyQueue.add(new RoundB(channel));
                    break;

                case 2:
                    strategyQueue.add(new RoundC(channel));
                    break;
            }
        }
    }

    @Override
    public void run() {
        for (RoundStrategy roundStrategy : strategyQueue) {
            roundStrategy.run();
        }
        latch.countDown();

    }
}
