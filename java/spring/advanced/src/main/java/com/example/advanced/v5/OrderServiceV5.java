package com.example.advanced.v5;

import com.example.advanced.trace.callback.TraceTemplate;
import com.example.advanced.trace.logtrace.LogTrace;
import com.example.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceV5 {

    private final OrderRepositoryV5 orderRepositoryV0;
    private final TraceTemplate traceTemplate;

    public OrderServiceV5(OrderRepositoryV5 orderRepositoryV0, LogTrace trace) {
        this.orderRepositoryV0 = orderRepositoryV0;
        this.traceTemplate = new TraceTemplate(trace);
    }

    public void orderItem(String itemId){
        traceTemplate.execute("OrderService.orderItem()", () -> {
            orderRepositoryV0.save(itemId);
            return null;
        });
    }
}
