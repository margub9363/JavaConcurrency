package org.example.sec07;

import org.example.sec07.externalService.Client;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class L03AccessResponseUsingFuture {
    public static final org.slf4j.Logger log = LoggerFactory.getLogger(L03AccessResponseUsingFuture.class);

    public static void main(String[] args) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()){
            var product1 = executor.submit(() -> Client.getProduct(1));
            var product2 = executor.submit(() -> Client.getProduct(1));
            var product3 = executor.submit(() -> Client.getProduct(1));
            log.info("Product-1 : {}",product1.get());
            log.info("Product-1 : {}",product2.get());
            log.info("Product-1 : {}",product3.get());
        }
        catch (Exception e) {

        }
    }
}
