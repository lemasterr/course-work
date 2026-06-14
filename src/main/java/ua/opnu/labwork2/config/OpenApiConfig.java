package ua.opnu.labwork2.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI internetShopOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Backend-сервіс управління інтернет-магазином")
                        .version("1.0.0")
                        .description("REST API для керування інтернет-магазином. Система підтримує роботу з товарами, категоріями, клієнтами, замовленнями та позиціями замовлень. API реалізовано на базі Spring Boot, Spring Web MVC, Spring Data JPA та Bean Validation. Документація побудована за специфікацією OpenAPI 3 і містить опис контролерів, DTO-моделей, успішних відповідей та типових помилок валідації або пошуку ресурсів.")
                        .contact(new Contact()
                                .name("Кафедра інформаційних систем ОНПУ")
                                .email("student@opnu.ua")));
    }
}
