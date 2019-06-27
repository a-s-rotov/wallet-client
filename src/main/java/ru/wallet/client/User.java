package ru.wallet.client;

import io.grpc.ManagedChannel;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class User {


    public void run(int roundPerThread, int threadPoolSize, ManagedChannel channel, CountDownLatch cdl) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < threadPoolSize; i++) {
            executorService.submit(new Task(roundPerThread, channel, cdl));
        }
        executorService.shutdown();
    }
}
