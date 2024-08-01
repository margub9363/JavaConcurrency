package org.example.sec07.aggregator;

import org.example.sec07.externalService.Client;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class AggregatorService {
    public final ExecutorService executorService;

    public AggregatorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public ProductDto getProductDto(int id) throws ExecutionException, InterruptedException {
        var product = executorService.submit( () -> Client.getProduct(id));
        var rating = executorService.submit( () -> Client.getRating(id));
     return new ProductDto(id,product.get(),rating.get());
    }
}
