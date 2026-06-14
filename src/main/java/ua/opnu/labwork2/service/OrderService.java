package ua.opnu.labwork2.service;

import java.util.List;
import org.springframework.stereotype.Service;
import ua.opnu.labwork2.exception.ResourceNotFoundException;
import ua.opnu.labwork2.model.Order;
import ua.opnu.labwork2.model.OrderStatus;
import ua.opnu.labwork2.model.Product;
import ua.opnu.labwork2.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order create(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    public Order updateStatus(Long id, Order updatedOrder) {
        Order existing = getById(id);
        existing.setStatus(updatedOrder.getStatus());
        return orderRepository.save(existing);
    }

    public List<Order> getByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    public List<Product> getProductsByOrderId(Long orderId) {
        return getById(orderId).getProducts();
    }

    public long countByStatus(OrderStatus status) {
        return orderRepository.countByStatus(status);
    }
}
