package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import ua.opnu.labwork2.model.OrderStatus;

@Schema(description = "DTO для створення замовлення або оновлення статусу замовлення")
public class OrderDto {

    @Schema(description = "Дата створення замовлення. Не може бути майбутньою датою", example = "2026-05-26")
    @PastOrPresent(message = "Order date cannot be in the future")
    private LocalDate orderDate;

    @Schema(description = "Поточний статус замовлення", example = "ACTIVE", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Order status is required")
    private OrderStatus status;

    @Schema(description = "Загальна сума замовлення. Не може бути від'ємною", example = "4599.50", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Total amount is required")
    @PositiveOrZero(message = "Total amount cannot be negative")
    private Double totalAmount;

    public OrderDto() {
    }

    public OrderDto(LocalDate orderDate, OrderStatus status, Double totalAmount) {
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
