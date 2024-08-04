package org.example.sec08;

import org.example.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class L03SupplyAsync {
    public static final Logger log = LoggerFactory.getLogger(L02RunAsync.class);


    public static void main(String[] args) {
        log.info("Main Method Starts-------------------------");
        var cf = slowTask();
        cf.thenAccept( v -> log.info("value = {}",v));
        log.info("Main Method Ends---------------------------");
        CommonUtils.sleep(Duration.ofSeconds(2));
    }

    public static CompletableFuture<String> slowTask() {
        log.info("Method Starts----------");
        var cf = CompletableFuture.supplyAsync(() -> {
            CommonUtils.sleep(Duration.ofSeconds(1));
            return "Hii";
//        }); <-- This could also have worked but these methods like runAsync or supplyAsync will be using virtual threads- > Fork join pool
//            so we are assuming that it can be a i/o blocking task so better to use newVirtualThreadPerTaskExecutor to create a new virtual
//            thread as required.
        }, Executors.newVirtualThreadPerTaskExecutor());
        log.info("Method Ends ------------");
        return cf;
    }
}
/*
o/p
        10:58:34.846 [main] INFO org.example.sec08.L02RunAsync -- Main Method Starts-------------------------
        10:58:34.851 [main] INFO org.example.sec08.L02RunAsync -- Method Starts----------
        10:58:34.865 [main] INFO org.example.sec08.L02RunAsync -- Method Ends ------------
        10:58:34.867 [main] INFO org.example.sec08.L02RunAsync -- Main Method Ends---------------------------
        10:58:35.880 [] INFO org.example.sec08.L02RunAsync -- value = Hii

 */