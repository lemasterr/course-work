package ua.opnu.labwork2.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.opnu.labwork2.model.OrderStatus;
import ua.opnu.labwork2.service.CategoryService;
import ua.opnu.labwork2.service.CustomerService;
import ua.opnu.labwork2.service.OrderService;
import ua.opnu.labwork2.service.ProductService;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    private final ProductService productService;
    private final CustomerService customerService;
    private final OrderService orderService;
    private final CategoryService categoryService;

    public AnalyticsController(
            ProductService productService,
            CustomerService customerService,
            OrderService orderService,
            CategoryService categoryService
    ) {
        this.productService = productService;
        this.customerService = customerService;
        this.orderService = orderService;
        this.categoryService = categoryService;
    }

    @GetMapping("/products/count")
    public ResponseEntity<Long> getProductsCount() {
        return ResponseEntity.ok(productService.count());
    }

    @GetMapping("/customers/count")
    public ResponseEntity<Long> getCustomersCount() {
        return ResponseEntity.ok(customerService.count());
    }

    @GetMapping("/orders/active")
    public ResponseEntity<Long> getActiveOrdersCount() {
        return ResponseEntity.ok(orderService.countByStatus(OrderStatus.ACTIVE));
    }

    @GetMapping("/orders/completed")
    public ResponseEntity<Long> getCompletedOrdersCount() {
        return ResponseEntity.ok(orderService.countByStatus(OrderStatus.COMPLETED));
    }

    @GetMapping("/products/by-category")
    public ResponseEntity<Map<String, Long>> getProductsByCategory() {
        Map<String, Long> result = new LinkedHashMap<>();
        categoryService.getAll().forEach(category -> result.put(category.getName(), (long) category.getProducts().size()));
        return ResponseEntity.ok(result);
    }
}
