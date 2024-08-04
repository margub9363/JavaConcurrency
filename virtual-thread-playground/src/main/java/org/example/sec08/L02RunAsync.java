package org.example.sec08;

import org.example.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class L02RunAsync {
    public static final Logger log = LoggerFactory.getLogger(L02RunAsync.class);


    public static void main(String[] args) {
        log.info("Main Method Starts-------------------------");

        /* // step1
                runAsync();
        * o/p <-- We can see that the main method was not blocked but even though we are not sure that the task
        *         got executed successfully or it encountered any exception. and to check that we are going for step 2
        *   10:22:25.329 [main] INFO org.example.sec08.L02RunAsync -- Main Method Starts-------------------------
        *   10:22:25.331 [main] INFO org.example.sec08.L02RunAsync -- Method Starts-------------------------------
        *   10:22:25.333 [main] INFO org.example.sec08.L02RunAsync -- Method Ends---------------------------------
        *   10:22:25.333 [main] INFO org.example.sec08.L02RunAsync -- Main Method Ends---------------------------
        *   10:22:26.341 [ForkJoinPool.commonPool-worker-1] INFO org.example.sec08.L02RunAsync -- Task Completed--------------------------
        * */

//        step2
        runAsync().
                thenRun( () -> {
                    log.info("It is done"); // if the task got executed sucessfully then we will get this messages
                }).exceptionally(ex-> {
                    log.info("Some exception encountered. Exception Message : {}",ex.getMessage());
                    return null;
                });
        /* O/p <- We can see that
                10:35:04.272 [main] INFO org.example.sec08.L02RunAsync -- Main Method Starts-------------------------
                10:35:04.274 [main] INFO org.example.sec08.L02RunAsync -- Method Starts-------------------------------
                10:35:04.284 [main] INFO org.example.sec08.L02RunAsync -- Method Ends---------------------------------
                10:35:04.286 [main] INFO org.example.sec08.L02RunAsync -- Main Method Ends---------------------------
                10:35:05.304 [] INFO org.example.sec08.L02RunAsync -- Some exception encountered. Exception Message : java.lang.RuntimeException: Something went wrong
        */
        log.info("Main Method Ends---------------------------");
        CommonUtils.sleep(Duration.ofSeconds(2));
    }
    /*
    //step 1
    public static void runAsync() { // here the return type is void
        log.info("Method Starts-------------------------------");
        CompletableFuture.runAsync( () -> {
            CommonUtils.sleep(Duration.ofSeconds(1));
            log.info("Task Completed--------------------------");
//        }); // this can also work and the below one will also work
        }, Executors.newVirtualThreadPerTaskExecutor());
        log.info("Method Ends---------------------------------");
        return ;
    }
    */

//    Step 2 <-- here we are returning the completable future of Void type to indicate the main thread that the task got
//    executed successfully or it encountered some error;
public static CompletableFuture<Void> runAsync() { // here the return type is void
    log.info("Method Starts-------------------------------");
    CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
        CommonUtils.sleep(Duration.ofSeconds(1));
        throw new RuntimeException("Something went wrong");
//        log.info("Task Completed--------------------------");
//        }); // this can also work and the below one will also work
    }, Executors.newVirtualThreadPerTaskExecutor());
    log.info("Method Ends---------------------------------");
    return cf;
}
}
