package org.example.sec08;

import org.example.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class L01SimpleCompletableFuture {

    public static final Logger log = LoggerFactory.getLogger(L01SimpleCompletableFuture.class);
    public static void main(String[] args) {
        log.info("Main Starts---------");
/*
        var cf = fastTask();
        log.info("value={}",cf.join());
        // here we should have used get() but that was throwing the exception so we have used join() method.
//        In this case we are blocking the main thread i.e unless and until we are not getting the value we are holding the main method
//o/p
//                18:01:06.325 [main] INFO org.example.sec08.L01SimpleCompletableFuture -- Main Starts---------
//                18:01:06.327 [main] INFO org.example.sec08.L01SimpleCompletableFuture -- Method Starts---------
//                18:01:06.329 [main] INFO org.example.sec08.L01SimpleCompletableFuture -- Method Ends---------
//                18:01:06.329 [main] INFO org.example.sec08.L01SimpleCompletableFuture -- value=Hi
//                18:01:06.331 [main] INFO org.example.sec08.L01SimpleCompletableFuture -- Main Ends---------

 */
        // /*
        var cf = slowTask();
        cf.thenAccept(v-> log.info("value={}",v));

//        o/P <-- We can see that the main thread was not blocked
//                17:58:10.563 [main] INFO org.example.sec08.L01SimpleCompletableFuture -- Main Starts---------
//                17:58:10.564 [main] INFO org.example.sec08.L01SimpleCompletableFuture -- Method Starts----------
//                17:58:10.569 [main] INFO org.example.sec08.L01SimpleCompletableFuture -- Methods Ends-----------
//                17:58:10.570 [main] INFO org.example.sec08.L01SimpleCompletableFuture -- Main Ends---------
//                17:58:12.578 [] INFO org.example.sec08.L01SimpleCompletableFuture -- value=Hii
//          */

        log.info("Main Ends---------");
        CommonUtils.sleep(Duration.ofSeconds(3)); // since we are dealing in virtual thread so we have to block main method to get everything completed.
    }

    public static CompletableFuture<String> fastTask() {
        log.info("Method Starts---------");
        var cf = new CompletableFuture<String>();
        cf.complete("Hi");
        log.info("Method Ends---------");
        return cf;
    }

    public static CompletableFuture<String> slowTask() {
        log.info("Method Starts----------");
        var cf = new CompletableFuture<String>();
        Thread.ofVirtual().start( () -> {
            CommonUtils.sleep(Duration.ofSeconds(2));
            cf.complete("Hii");
        });
        log.info("Methods Ends-----------");
        return cf;
    }
}
