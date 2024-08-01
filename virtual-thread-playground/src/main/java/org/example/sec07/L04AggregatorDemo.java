package org.example.sec07;

import org.example.sec07.aggregator.AggregatorService;
import org.example.sec07.aggregator.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class L04AggregatorDemo {
    public static final Logger log = LoggerFactory.getLogger(L04AggregatorDemo.class);
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        var executor = Executors.newVirtualThreadPerTaskExecutor();
        var aggregator = new AggregatorService(executor);

        List<Future<ProductDto>> futures = IntStream.range(1, 50).mapToObj(id -> executor.submit(() -> aggregator.getProductDto(id))).toList();

        var list = futures.stream().map(f -> toProductDto(f)).collect(Collectors.toList());

        log.info("list: {}",list);
    }

    public static ProductDto toProductDto(Future<ProductDto> future) {
        try {
            return future.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
