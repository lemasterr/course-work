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
import ua.opnu.labwork2.dto.ProductDto;
import ua.opnu.labwork2.exception.ErrorResponse;
import ua.opnu.labwork2.model.Product;
import ua.opnu.labwork2.service.ProductService;

@Tag(name = "Управління товарами", description = "Операції для створення, перегляду, оновлення, видалення та фільтрації товарів інтернет-магазину")
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Створити товар", description = "Створює новий товар. Перевіряється наявність назви, додатна ціна та невід'ємна кількість на складі.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Товар успішно створено", content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "400", description = "Помилка валідації вхідних даних", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDto productDto) {
        Product product = toProduct(productDto);
        return ResponseEntity.ok(productService.create(product));
    }

    @Operation(summary = "Отримати всі товари", description = "Повертає повний список товарів, які збережені в системі.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список товарів успішно отримано", content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAll());
    }

    @Operation(summary = "Отримати товар за ID", description = "Повертає товар за унікальним ідентифікатором. Якщо товар не знайдено, повертається структурована помилка 404.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Товар знайдено", content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Товар з указаним ID не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@Parameter(description = "ID товару", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @Operation(summary = "Оновити товар", description = "Оновлює дані товару за ID. Вхідні дані проходять валідацію за тими самими правилами, що й під час створення.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Товар успішно оновлено", content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "400", description = "Помилка валідації вхідних даних", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Товар з указаним ID не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@Parameter(description = "ID товару", example = "1") @PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
        Product product = toProduct(productDto);
        return ResponseEntity.ok(productService.update(id, product));
    }

    @Operation(summary = "Видалити товар", description = "Видаляє товар за ID та повертає видалений об'єкт. Якщо ID не існує, повертається помилка 404.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Товар успішно видалено", content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Товар з указаним ID не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@Parameter(description = "ID товару", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(productService.delete(id));
    }

    @Operation(summary = "Отримати товари категорії", description = "Повертає список товарів, які належать до вказаної категорії.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Товари категорії успішно отримано", content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Категорію не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategory(@Parameter(description = "ID категорії", example = "1") @PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getByCategoryId(categoryId));
    }

    private Product toProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStockQuantity(productDto.getStockQuantity());
        return product;
    }
}
