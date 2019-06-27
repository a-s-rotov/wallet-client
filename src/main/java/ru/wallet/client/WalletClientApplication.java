package ru.wallet.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class WalletClientApplication {

    public static void main(String... args) {

        if (args.length == 0) {
            menu();
        } else if (args.length != 3) {
            throw new IllegalStateException("Wrong input parameters");
        } else if (args.length == 3) {
            run(Integer.valueOf(args[0]), Integer.valueOf(args[1]), Integer.valueOf(args[2]));
        }


    }

    public static void menu() {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter number of concurrent users emulated:");
        int users = in.nextInt();


        System.out.println("Enter number of concurrent requests a user will make:");
        int threads = in.nextInt();

        System.out.println("Enter number of rounds each thread is executing:");
        int rounds = in.nextInt();

        in.close();
        run(users, threads, rounds);
    }

    public static void run(int users, int threads, int rounds) {


        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();

        CountDownLatch cdl = new CountDownLatch(users * threads);
        Date startTime = new Date();
        for (int i = 0; i < users; i++) {
            User user = new User();
            user.run(rounds, threads, channel, cdl);
        }
        try {
            cdl.await();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        Date endTime = new Date();
        channel.shutdown();
        long totalTime = endTime.getTime() - startTime.getTime();
        System.out.println("Total time: " + totalTime + " Time per round: " + (totalTime / (users * threads * rounds)));
    }
}
