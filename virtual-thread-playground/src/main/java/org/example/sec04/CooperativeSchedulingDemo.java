package org.example.sec04;

import org.example.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;


public class CooperativeSchedulingDemo {
    public static final Logger log = LoggerFactory.getLogger(CooperativeSchedulingDemo.class);
//case 1
    // Here from line 16 to 34 premptive scheduling is happening because we are running two threads and the task 1 is getting performed
    // and then context switching happened and the other task got executed.

  /*  public static void main(String[] args) {

        var builder = Thread.ofVirtual();
        var t1 = builder.unstarted(() -> demo(1));
        var t2 = builder.unstarted(() -> demo(2));

        t1.start();
        t2.start();

        CommonUtils.sleep(Duration.ofSeconds(5));
    }

    private static void demo(int threadNumber) {
        log.info("thread-{} started", threadNumber);
        for (int i = 0; i < 10; i++) {
            log.info("thread-{} is pringting {}. Thread {}", threadNumber, i, Thread.currentThread());
        }
        log.info("thread-{} ended", threadNumber);
    } */

//    case 2 line no 39 to 62
    // To demo Cooperative scheduling we have to limit our cpu resource ie to run the program with 1 thread and 1 parallelism
//If we run this program we will see that since we are allowing only one thread so thread 1 will execute completely then the request will be given to thread 2
  /*  static {
        System.setProperty("jdk.virtualThread.Scheduler.parallelism","1");
        System.setProperty("jdk.virtualThread.Scheduler.maxPoolSize","1");
    }

    public static void main(String[] args) {
        var builder = Thread.ofVirtual();
        var t1 = builder.unstarted(() -> demo(1));
        var t2 = builder.unstarted(() -> demo(2));

        t1.start();
        t2.start();

        CommonUtils.sleep(Duration.ofSeconds(5));
    }

    private static void demo(int threadNumber) {
        log.info("thread-{} started", threadNumber);
        for (int i = 0; i < 10; i++) {
            log.info("thread-{} is pringting {}. Thread {}", threadNumber, i, Thread.currentThread());
        }
        log.info("thread-{} ended", threadNumber);
    }*/

//    case3
        static {
        System.setProperty("jdk.virtualThread.Scheduler.parallelism","1");
        System.setProperty("jdk.virtualThread.Scheduler.maxPoolSize","1");
    }

    public static void main(String[] args) {
        var builder = Thread.ofVirtual();
        var t1 = builder.unstarted(() -> demo(1));
        var t2 = builder.unstarted(() -> demo(2));

        t1.start();
        t2.start();

        CommonUtils.sleep(Duration.ofSeconds(5));
    }

    private static void demo(int threadNumber) {
        log.info("thread-{} started", threadNumber);
        for (int i = 0; i < 10; i++) {
            log.info("thread-{} is pringting {}. Thread {}", threadNumber, i, Thread.currentThread());
            Thread.yield(); // we are forcefully giving the chance to other thread so that they can execute
        }
        log.info("thread-{} ended", threadNumber);
    }
}
