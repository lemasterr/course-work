package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO для створення або оновлення категорії товарів")
public class CategoryDto {

    @Schema(description = "Назва категорії товарів. Використовується для групування асортименту", example = "Електроніка", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Category name is required")
    @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters")
    private String name;

    @Schema(description = "Опис категорії та типів товарів, які до неї належать", example = "Техніка, гаджети та аксесуари для дому й роботи")
    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    public CategoryDto() {
    }

    public CategoryDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
