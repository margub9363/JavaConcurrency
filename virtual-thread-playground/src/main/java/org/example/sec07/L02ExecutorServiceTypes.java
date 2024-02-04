package org.example.sec07;

import org.example.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class L02ExecutorServiceTypes {
    public static final Logger log = LoggerFactory.getLogger(L02ExecutorServiceTypes.class);

    public static void main(String[] args) {

//        Below three are platform threads
//        execute(Executors.newSingleThreadExecutor(),3); // This will make the execution sequentially
//        execute(Executors.newFixedThreadPool(5),20); // here we are giving 5 threads i.e 5 tasks will work at any given instant
//            execute(Executors.newCachedThreadPool(),20); // by default it will not be having any thread in thread pool, but its going to create the same no of threads which has in the no of tasks

//        lets come to Virtual Thread
//        execute(Executors.newVirtualThreadPerTaskExecutor(),2000); // It will create the same no of threads as the no of tasks but the threads will be virtual


//        scheduled
        scheduled();

    }

    private static void scheduled() {
        try (var executorService = Executors.newSingleThreadScheduledExecutor()){
            executorService.scheduleAtFixedRate(() -> {
                log.info("executing task");
            },0,1, TimeUnit.SECONDS);

            CommonUtils.sleep(Duration.ofSeconds(5));
        }
    }
    private static void execute (ExecutorService executorService,int taskCount) {
        try (executorService){
            for (int i = 0; i <taskCount ; i++) {
                int j=i;
                executorService.submit( () -> ioTask(j));
            }
            log.info("Submitted");
        }
    }

    private static void ioTask(int i) {
        log.info("Task started: {}. Thread info {}", i, Thread.currentThread());
        CommonUtils.sleep(Duration.ofSeconds(5));
        log.info("Task ended: {}. Thread info {}", i, Thread.currentThread());
    }

}
