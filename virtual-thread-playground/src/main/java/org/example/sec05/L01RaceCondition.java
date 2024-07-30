package org.example.sec05;

import org.example.sec04.CooperativeSchedulingDemo;
import org.example.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

//If we execute the operation on the Array List then we will see different list size each time because of the race condition.
public class L01RaceCondition {
    public static final Logger log = LoggerFactory.getLogger(CooperativeSchedulingDemo.class);
    public static final List<Integer> list = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        demo(Thread.ofPlatform());
//        demo(Thread.ofVirtual());
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
        list.add(1);
    }
}
