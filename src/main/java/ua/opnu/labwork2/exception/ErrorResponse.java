package ua.opnu.labwork2.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "Єдина структура відповіді сервера у випадку помилки")
public class ErrorResponse {

    @Schema(description = "Дата та час виникнення помилки", example = "2026-05-26T12:30:15")
    private LocalDateTime timestamp;

    @Schema(description = "HTTP-статус помилки", example = "400")
    private int status;

    @Schema(description = "Коротка назва типу помилки", example = "Validation Error")
    private String error;

    @Schema(description = "Детальне повідомлення про причину помилки", example = "Input data validation failed")
    private String message;

    @Schema(description = "URI запиту, під час обробки якого сталася помилка", example = "/products")
    private String path;

    @Schema(description = "Мапа помилок валідації, де ключ — назва поля, а значення — текст помилки", example = "{\"name\":\"Product name is required\",\"price\":\"Price must be greater than 0\"}")
    private Map<String, String> validationErrors;

    public ErrorResponse() {
    }

    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path, Map<String, String> validationErrors) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.validationErrors = validationErrors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }
}
