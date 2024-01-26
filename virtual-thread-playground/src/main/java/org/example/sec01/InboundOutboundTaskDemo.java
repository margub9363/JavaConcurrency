package org.example.sec01;

import java.util.concurrent.CountDownLatch;

public class InboundOutboundTaskDemo {
    private static final int MAX_PLATFORM = 10;
    public static void main(String[] args) throws InterruptedException {
        platformThreadDemo3();
    }
    private static void  platformThreadDemo1() {
        for (int i =0; i<MAX_PLATFORM; i++) {
            int j = i;
            Thread thread = new Thread( () -> Task.inIntesive(j));
            thread.start();
        }
    }

    private static void  platformThreadDemo2() {
        Thread.Builder.OfPlatform builder = Thread.ofPlatform().name("rahman", 1);
        for (int i =0; i<MAX_PLATFORM; i++) {
            int j = i;
            Thread thread = builder.unstarted( () -> Task.inIntesive(j));
            thread.start();
        }
    }

    private static void  platformThreadDemo3() throws InterruptedException {
        var latch = new CountDownLatch(MAX_PLATFORM);
        Thread.Builder.OfPlatform builder = Thread.ofPlatform().daemon().name("daemon-", 1);
        for (int i =0; i<MAX_PLATFORM; i++) {
            int j = i;
            Thread thread = builder.unstarted( () -> {
                Task.inIntesive(j);
                latch.countDown();
            });
            thread.start();
        }
        latch.await();
    }
}
