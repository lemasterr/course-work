package ua.opnu.labwork2.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.opnu.labwork2.model.Order;
import ua.opnu.labwork2.model.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);

    List<Order> findByCustomerId(Long customerId);

    long countByStatus(OrderStatus status);
}
