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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.opnu.labwork2.dto.OrderItemDto;
import ua.opnu.labwork2.exception.ErrorResponse;
import ua.opnu.labwork2.model.OrderItem;
import ua.opnu.labwork2.service.OrderItemService;

@Tag(name = "Управління позиціями замовлень", description = "Операції для створення, перегляду, оновлення та видалення окремих товарних позицій у замовленнях")
@RestController
@RequestMapping
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @Operation(summary = "Створити позицію замовлення", description = "Створює позицію замовлення. Кількість має бути не менше 1, а ціна має бути більшою за 0.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Позицію замовлення успішно створено", content = @Content(schema = @Schema(implementation = OrderItem.class))),
            @ApiResponse(responseCode = "400", description = "Помилка валідації вхідних даних", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/order-items")
    public ResponseEntity<OrderItem> createOrderItem(@Valid @RequestBody OrderItemDto orderItemDto) {
        OrderItem orderItem = toOrderItem(orderItemDto);
        return ResponseEntity.ok(orderItemService.create(orderItem));
    }

    @Operation(summary = "Отримати всі позиції", description = "Повертає всі позиції замовлень, збережені в системі.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список позицій замовлень успішно отримано", content = @Content(schema = @Schema(implementation = OrderItem.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/order-items")
    public ResponseEntity<List<OrderItem>> getAllOrderItems() {
        return ResponseEntity.ok(orderItemService.getAll());
    }

    @Operation(summary = "Отримати позицію за ID", description = "Повертає позицію замовлення за ID або помилку 404, якщо її не знайдено.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Позицію замовлення знайдено", content = @Content(schema = @Schema(implementation = OrderItem.class))),
            @ApiResponse(responseCode = "404", description = "Позицію замовлення з указаним ID не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/order-items/{id}")
    public ResponseEntity<OrderItem> getOrderItemById(@Parameter(description = "ID позиції замовлення", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(orderItemService.getById(id));
    }

    @Operation(summary = "Оновити позицію", description = "Оновлює кількість та ціну позиції замовлення. Дані перевіряються за правилами DTO.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Позицію замовлення успішно оновлено", content = @Content(schema = @Schema(implementation = OrderItem.class))),
            @ApiResponse(responseCode = "400", description = "Помилка валідації вхідних даних", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Позицію замовлення з указаним ID не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/order-items/{id}")
    public ResponseEntity<OrderItem> updateOrderItem(@Parameter(description = "ID позиції замовлення", example = "1") @PathVariable Long id, @Valid @RequestBody OrderItemDto orderItemDto) {
        OrderItem orderItem = toOrderItem(orderItemDto);
        return ResponseEntity.ok(orderItemService.update(id, orderItem));
    }

    @Operation(summary = "Видалити позицію", description = "Видаляє позицію замовлення за ID та повертає видалений об'єкт.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Позицію замовлення успішно видалено", content = @Content(schema = @Schema(implementation = OrderItem.class))),
            @ApiResponse(responseCode = "404", description = "Позицію замовлення з указаним ID не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/order-items/{id}")
    public ResponseEntity<OrderItem> deleteOrderItem(@Parameter(description = "ID позиції замовлення", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(orderItemService.delete(id));
    }

    @Operation(summary = "Отримати позиції замовлення", description = "Повертає всі позиції, які належать конкретному замовленню.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Позиції замовлення успішно отримано", content = @Content(schema = @Schema(implementation = OrderItem.class))),
            @ApiResponse(responseCode = "404", description = "Замовлення або позиції не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/orders/{id}/items")
    public ResponseEntity<List<OrderItem>> getOrderItemsByOrderId(@Parameter(description = "ID замовлення", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(orderItemService.getByOrderId(id));
    }

    private OrderItem toOrderItem(OrderItemDto orderItemDto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setPrice(orderItemDto.getPrice());
        return orderItem;
    }
}
