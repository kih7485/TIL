package com.example.advanced.v4;

import com.example.advanced.trace.TraceStatus;
import com.example.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV3 {

    private final OrderRepositoryV3 orderRepositoryV0;
    private final LogTrace trace;

    public void orderItem(String itemId){

        TraceStatus status = null;

        try {
            status = trace.begin("OrderService.orderItem()");
            orderRepositoryV0.save(itemId);
            trace.end(status);
        }catch (Exception e) {
            trace.exception(status, e);
            throw e; // 예외를 꼭 다시 던져줘야 함.
        }

    }
}
