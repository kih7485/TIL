package hello.proxy.app.v3;

import hello.proxy.app.v2.OrderServiceV2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OrderControllerV3 {
    private final OrderServiceV3 service;

    public OrderControllerV3(OrderServiceV3 service) {
        this.service = service;
    }

    @GetMapping("/v3/request")
    public String request(String itemId) {
        service.orderItem(itemId);
        return "ok";
    }

    public String noLog() {
        return "ok";
    }
}
