package ua.opnu.labwork2.service;

import java.util.List;
import org.springframework.stereotype.Service;
import ua.opnu.labwork2.exception.ResourceNotFoundException;
import ua.opnu.labwork2.model.OrderItem;
import ua.opnu.labwork2.repository.OrderItemRepository;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public OrderItem create(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public List<OrderItem> getAll() {
        return orderItemRepository.findAll();
    }

    public OrderItem getById(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found with id: " + id));
    }

    public OrderItem update(Long id, OrderItem updatedOrderItem) {
        OrderItem existing = getById(id);
        existing.setQuantity(updatedOrderItem.getQuantity());
        existing.setPrice(updatedOrderItem.getPrice());
        return orderItemRepository.save(existing);
    }

    public OrderItem delete(Long id) {
        OrderItem orderItem = getById(id);
        orderItemRepository.delete(orderItem);
        return orderItem;
    }

    public List<OrderItem> getByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }
}
