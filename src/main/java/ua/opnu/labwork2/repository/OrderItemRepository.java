package ua.opnu.labwork2.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.opnu.labwork2.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderId);
}
