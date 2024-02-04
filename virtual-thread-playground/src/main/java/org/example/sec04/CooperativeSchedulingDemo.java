package org.example.sec04;

import org.example.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;


public class CooperativeSchedulingDemo {
    public static final Logger log = LoggerFactory.getLogger(CooperativeSchedulingDemo.class);

    public static void main(String[] args) {
        var builder = Thread.ofVirtual();
//        here we will be performing an operation on virtual thread that means cooperative scheduling will be done.
        var t1 = builder.unstarted(() -> demo(1));
        var t2 = builder.unstarted(() -> demo(2));

        t1.start();
        t2.start();

        CommonUtils.sleep(Duration.ofSeconds(5));
    }

    private static void demo (int threadNumber){
        log.info("thread-{} started",threadNumber);
        for (int i = 0; i < 10; i++) {
            log.info("thread-{} is pringting {}. Thread {}",threadNumber,i,Thread.currentThread());
        }
        log.info("thread-{} ended",threadNumber);
    }
}
