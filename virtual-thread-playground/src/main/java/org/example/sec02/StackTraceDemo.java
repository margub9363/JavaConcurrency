package org.example.sec02;

import org.example.sec02.Task;
import org.example.util.CommonUtils;

import java.time.Duration;

public class StackTraceDemo {
    public static void main(String[] args) {
//        demo(Thread.ofPlatform());
        demo(Thread.ofVirtual().name("Virtual-",1));
        CommonUtils.sleep(Duration.ofMillis(2000));
    }

    public static void demo(Thread.Builder builder){
        for (int i=1; i<=20; i++) {
            int j=i;
            builder.start( () -> Task.execute(j));

        }

    }
}
