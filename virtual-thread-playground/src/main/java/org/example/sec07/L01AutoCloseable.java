package org.example.sec07;

import org.example.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class L01AutoCloseable {
    public static final Logger log = LoggerFactory.getLogger(L01AutoCloseable.class);

    public static void main(String[] args) {

//        without any shutdown
        /*var executorService = Executors.newSingleThreadExecutor();
        executorService.submit( () -> task());
        log.info("Submitted");
*/

        //        without  shutdown
        /*var executorService = Executors.newSingleThreadExecutor();
        executorService.submit( () -> task());
        log.info("Submitted");
        executorService.shutdown();
//        executorService.submit( () -> task()); //it will give Exception -> RejectedExecutionException
        */

//        try with resources
        try (var executorService = Executors.newSingleThreadExecutor();){
            executorService.submit( () -> task());
            log.info("Submitted");
        }
    }

    private static void task() {
        CommonUtils.sleep(Duration.ofSeconds(1));
        log.info("Task Executed");
    }

}
