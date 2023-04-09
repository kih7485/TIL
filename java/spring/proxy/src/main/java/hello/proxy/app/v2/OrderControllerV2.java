package hello.proxy.app.v2;

import hello.proxy.app.v1.OrderServiceV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping
@ResponseBody
public class OrderControllerV2 {
    private final OrderServiceV2 service;

    public OrderControllerV2(OrderServiceV2 service) {
        this.service = service;
    }

    @GetMapping("/v2/request")
    public String request(String itemId) {
        service.orderItem(itemId);
        return "ok";
    }

    public String noLog() {
        return "ok";
    }
}
