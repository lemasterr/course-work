package ua.opnu.labwork2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.opnu.labwork2.dto.OrderDto;
import ua.opnu.labwork2.exception.ErrorResponse;
import ua.opnu.labwork2.model.Order;
import ua.opnu.labwork2.model.OrderStatus;
import ua.opnu.labwork2.model.Product;
import ua.opnu.labwork2.service.OrderService;

@Tag(name = "Управління замовленнями", description = "Операції для створення замовлень, перегляду замовлень, зміни статусу та отримання товарів у замовленні")
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Створити замовлення", description = "Створює нове замовлення. Перевіряється статус, загальна сума та дата, яка не може бути майбутньою.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Замовлення успішно створено", content = @Content(schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "400", description = "Помилка валідації вхідних даних", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderDto orderDto) {
        Order order = toOrder(orderDto);
        return ResponseEntity.ok(orderService.create(order));
    }

    @Operation(summary = "Отримати всі замовлення", description = "Повертає список усіх замовлень інтернет-магазину.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список замовлень успішно отримано", content = @Content(schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @Operation(summary = "Отримати замовлення за ID", description = "Повертає замовлення за ID. Якщо замовлення не існує, сервер повертає структуровану помилку 404.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Замовлення знайдено", content = @Content(schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "404", description = "Замовлення з указаним ID не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@Parameter(description = "ID замовлення", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    @Operation(summary = "Оновити статус замовлення", description = "Оновлює статус і суму замовлення. Статус є обов'язковим, а сума не може бути від'ємною.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Статус замовлення успішно оновлено", content = @Content(schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "400", description = "Помилка валідації вхідних даних", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Замовлення з указаним ID не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@Parameter(description = "ID замовлення", example = "1") @PathVariable Long id, @Valid @RequestBody OrderDto orderDto) {
        Order order = toOrder(orderDto);
        return ResponseEntity.ok(orderService.updateStatus(id, order));
    }

    @Operation(summary = "Отримати активні замовлення", description = "Повертає замовлення зі статусом ACTIVE.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Активні замовлення успішно отримано", content = @Content(schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/active")
    public ResponseEntity<List<Order>> getActiveOrders() {
        return ResponseEntity.ok(orderService.getByStatus(OrderStatus.ACTIVE));
    }

    @Operation(summary = "Отримати завершені замовлення", description = "Повертає замовлення зі статусом COMPLETED.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Завершені замовлення успішно отримано", content = @Content(schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/completed")
    public ResponseEntity<List<Order>> getCompletedOrders() {
        return ResponseEntity.ok(orderService.getByStatus(OrderStatus.COMPLETED));
    }

    @Operation(summary = "Отримати товари замовлення", description = "Повертає список товарів, які входять до конкретного замовлення.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Товари замовлення успішно отримано", content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Замовлення з указаним ID не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}/products")
    public ResponseEntity<List<Product>> getOrderProducts(@Parameter(description = "ID замовлення", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(orderService.getProductsByOrderId(id));
    }

    private Order toOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setOrderDate(orderDto.getOrderDate());
        order.setStatus(orderDto.getStatus());
        order.setTotalAmount(orderDto.getTotalAmount());
        return order;
    }
}
