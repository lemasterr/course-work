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
import ua.opnu.labwork2.dto.CategoryDto;
import ua.opnu.labwork2.exception.ErrorResponse;
import ua.opnu.labwork2.model.Category;
import ua.opnu.labwork2.model.Product;
import ua.opnu.labwork2.service.CategoryService;

@Tag(name = "Управління категоріями", description = "Операції для роботи з категоріями товарів та отримання товарів конкретної категорії")
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Створити категорію", description = "Створює нову категорію товарів. Назва категорії є обов'язковою та має містити від 2 до 100 символів.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Категорію успішно створено", content = @Content(schema = @Schema(implementation = Category.class))),
            @ApiResponse(responseCode = "400", description = "Помилка валідації вхідних даних", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        Category category = toCategory(categoryDto);
        return ResponseEntity.ok(categoryService.create(category));
    }

    @Operation(summary = "Отримати всі категорії", description = "Повертає список усіх категорій, зареєстрованих у системі.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список категорій успішно отримано", content = @Content(schema = @Schema(implementation = Category.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @Operation(summary = "Отримати категорію за ID", description = "Повертає категорію за її унікальним ідентифікатором. Якщо категорію не знайдено, повертається 404.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Категорію знайдено", content = @Content(schema = @Schema(implementation = Category.class))),
            @ApiResponse(responseCode = "404", description = "Категорію з указаним ID не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@Parameter(description = "ID категорії", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @Operation(summary = "Оновити категорію", description = "Оновлює назву та опис категорії за ID. Вхідні дані проходять перевірку Bean Validation.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Категорію успішно оновлено", content = @Content(schema = @Schema(implementation = Category.class))),
            @ApiResponse(responseCode = "400", description = "Помилка валідації вхідних даних", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Категорію з указаним ID не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@Parameter(description = "ID категорії", example = "1") @PathVariable Long id, @Valid @RequestBody CategoryDto categoryDto) {
        Category category = toCategory(categoryDto);
        return ResponseEntity.ok(categoryService.update(id, category));
    }

    @Operation(summary = "Видалити категорію", description = "Видаляє категорію за ID та повертає видалений об'єкт. Якщо категорію не знайдено, повертається 404.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Категорію успішно видалено", content = @Content(schema = @Schema(implementation = Category.class))),
            @ApiResponse(responseCode = "404", description = "Категорію з указаним ID не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategory(@Parameter(description = "ID категорії", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }

    @Operation(summary = "Отримати товари категорії", description = "Повертає товари, які прив'язані до вказаної категорії.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список товарів категорії успішно отримано", content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Категорію з указаним ID не знайдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}/products")
    public ResponseEntity<List<Product>> getCategoryProducts(@Parameter(description = "ID категорії", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getProductsByCategoryId(id));
    }

    private Category toCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        return category;
    }
}
