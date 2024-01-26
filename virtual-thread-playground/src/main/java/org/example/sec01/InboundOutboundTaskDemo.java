package org.example.sec01;

import java.util.concurrent.CountDownLatch;

public class InboundOutboundTaskDemo {
    private static final int MAX_PLATFORM = 10;
    private static final int MAX_VIRTUAL = 20;
    public static void main(String[] args) throws InterruptedException {
        platformThreadDemo1(); //First we will execute this and see the result then we will execute the other and will see the results
        virtualThreadDemo();
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

//To create virtual thread using Thread.Builder
    private static void  virtualThreadDemo() throws InterruptedException {
        var latch = new CountDownLatch(MAX_VIRTUAL);
        var builder = Thread.ofVirtual().name("virtual-",1);
        for (int i =0; i<MAX_VIRTUAL; i++) {
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
