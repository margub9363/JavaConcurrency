package org.example.sec05;

import org.example.sec04.CooperativeSchedulingDemo;
import org.example.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// here we are using Reentrant lock because of which the list size each time is 10000
public class L04ReentrantLock {
    public static final Logger log = LoggerFactory.getLogger(CooperativeSchedulingDemo.class);
    public static final List<Integer> list = new ArrayList<>();
    public static final Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
//        demo(Thread.ofPlatform());
        demo(Thread.ofVirtual());
        CommonUtils.sleep(Duration.ofSeconds(2));
        log.info("Size of the list is :{}", list.size());
    }

    private static void demo(Thread.Builder builder) {

        for (int i = 0; i < 50; i++) {
            builder.start( () -> {
                log.info("Task started. {}",Thread.currentThread());
                for (int j = 0; j < 200; j++) {
                    inMemoryTask();
                }
                log.info("Task ended. {}",Thread.currentThread());
            });
        }
    }

    private static void inMemoryTask() {
        try {
            lock.lock();
            list.add(1);
        } catch (Exception e) {
            log.error("Error",e);
        }
        finally {
            lock.unlock();
        }
    }
}
