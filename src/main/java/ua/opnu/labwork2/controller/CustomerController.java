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
import ua.opnu.labwork2.dto.CustomerDto;
import ua.opnu.labwork2.exception.ErrorResponse;
import ua.opnu.labwork2.model.Customer;
import ua.opnu.labwork2.model.Order;
import ua.opnu.labwork2.service.CustomerService;

@Tag(name = "Управління клієнтами", description = "Операції для створення, перегляду, оновлення, видалення клієнтів та перегляду їхніх замовлень")
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "Створити клієнта", description = "Створює профіль клієнта. Ім'я, прізвище та email є обов'язковими; email має відповідати коректному формату.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Клієнта успішно створено", content = @Content(schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "400", description = "Помилка валідації вхідних даних", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        Customer customer = toCustomer(customerDto);
        return ResponseEntity.ok(customerService.create(customer));
    }

    @Operation(summary = "Отримати всіх клієнтів", description = "Повертає список усіх клієнтів інтернет-магазину.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список клієнтів успішно отримано", content = @Content(schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAll());
    }

    @Operation(summary = "Отримати клієнта за ID", description = "Повертає клієнта за унікальним ідентифікатором або структуровану помилку 404, якщо клієнта не знайдено.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Клієнта знайдено", content = @Content(schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "404", description = "Клієнта з указаним ID не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@Parameter(description = "ID клієнта", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(customerService.getById(id));
    }

    @Operation(summary = "Оновити клієнта", description = "Оновлює контактні дані клієнта. Перед збереженням перевіряються обов'язкові поля, формат email та номер телефону.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Клієнта успішно оновлено", content = @Content(schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "400", description = "Помилка валідації вхідних даних", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Клієнта з указаним ID не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@Parameter(description = "ID клієнта", example = "1") @PathVariable Long id, @Valid @RequestBody CustomerDto customerDto) {
        Customer customer = toCustomer(customerDto);
        return ResponseEntity.ok(customerService.update(id, customer));
    }

    @Operation(summary = "Видалити клієнта", description = "Видаляє клієнта за ID та повертає видалений об'єкт.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Клієнта успішно видалено", content = @Content(schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "404", description = "Клієнта з указаним ID не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@Parameter(description = "ID клієнта", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(customerService.delete(id));
    }

    @Operation(summary = "Отримати замовлення клієнта", description = "Повертає список замовлень, які належать конкретному клієнту.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Замовлення клієнта успішно отримано", content = @Content(schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "404", description = "Клієнта з указаним ID не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}/orders")
    public ResponseEntity<List<Order>> getCustomerOrders(@Parameter(description = "ID клієнта", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(customerService.getOrdersByCustomerId(id));
    }

    private Customer toCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setEmail(customerDto.getEmail());
        customer.setPhone(customerDto.getPhone());
        return customer;
    }
}
