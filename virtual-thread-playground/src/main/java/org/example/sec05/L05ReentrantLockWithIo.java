package org.example.sec05;

import org.example.sec04.CooperativeSchedulingDemo;
import org.example.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class L05ReentrantLockWithIo {
    public static final Logger log = LoggerFactory.getLogger(CooperativeSchedulingDemo.class);
    public static final Lock lock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {

        Runnable runnable = () -> {
            log.info("** Test Message **");
        };

//        demo(Thread.ofPlatform());
//        Thread.ofPlatform().start(runnable); // when we execute this with the platform thread then we can see that line no 16 is printed

        demo(Thread.ofVirtual());
        Thread.ofVirtual().start(runnable); // when you execute this with the Virtual thread you will not see the line no 16 getting printed
//        but if you use the concept of Reentrant lock then you will be able to see the line 16 log message


        CommonUtils.sleep(Duration.ofSeconds(15));
    }

    private static void demo(Thread.Builder builder) {

        for (int i = 0; i < 50; i++) {
            builder.start( () -> {
                log.info("Task started. {}",Thread.currentThread());
                for (int j = 0; j < 200; j++) {
                    ioTask();
                }
                log.info("Task ended. {}",Thread.currentThread());
            });
        }
    }

//    private static synchronized void ioTask() {
//        CommonUtils.sleep(Duration.ofSeconds(10));
//    }

    private static void ioTask() {
        try {
            lock.lock();
            CommonUtils.sleep(Duration.ofSeconds(10));
        } catch (Exception e){
            log.error("Error",e);
        } finally {
            lock.unlock();
        }
    }
}
