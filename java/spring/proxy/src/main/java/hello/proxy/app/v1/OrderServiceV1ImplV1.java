package hello.proxy.app.v1;

public class OrderServiceV1ImplV1 implements OrderServiceV1 {
    private final OrderRepositoryV1 orderRepository;

    public OrderServiceV1ImplV1(OrderRepositoryV1 orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void orderItem(String itemId) {
        orderRepository.save(itemId);
    }
}
