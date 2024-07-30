package org.example.sec06;

import org.example.sec04.CooperativeSchedulingDemo;
import org.example.util.CommonUtils;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Logger;

public class L01ThreadFactory {
    public static final org.slf4j.Logger log = LoggerFactory.getLogger(L01ThreadFactory.class);

    public static void main(String[] args) {
        demo(Thread.ofVirtual().name("Rahman-",1).factory());
        CommonUtils.sleep(Duration.ofSeconds(3));
    }

    public static void demo(ThreadFactory factory) {
        for (int i=0; i<3; i++) {
            var t = factory.newThread(() -> {
                log.info("Task Started {}", Thread.currentThread());
                var ct = factory.newThread(() -> {
                    log.info("Child task started. {}",Thread.currentThread());
                    CommonUtils.sleep(Duration.ofSeconds(2));
                    log.info("Child task ended. {}",Thread.currentThread());
                });
                ct.start();
                log.info("Task Completed {}", Thread.currentThread());
            });
            t.start();
        }
    }
}
