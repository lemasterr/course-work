package ua.opnu.labwork2;

import java.time.LocalDate;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.opnu.labwork2.model.Category;
import ua.opnu.labwork2.model.Customer;
import ua.opnu.labwork2.model.Order;
import ua.opnu.labwork2.model.OrderItem;
import ua.opnu.labwork2.model.OrderStatus;
import ua.opnu.labwork2.model.Product;
import ua.opnu.labwork2.repository.CategoryRepository;
import ua.opnu.labwork2.repository.CustomerRepository;
import ua.opnu.labwork2.repository.OrderItemRepository;
import ua.opnu.labwork2.repository.OrderRepository;
import ua.opnu.labwork2.repository.ProductRepository;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner seedData(
            CategoryRepository categoryRepository,
            ProductRepository productRepository,
            CustomerRepository customerRepository,
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository
    ) {
        return args -> {
            if (categoryRepository.count() > 0) {
                return;
            }

            Category electronics = new Category(null, "Electronics", "Devices and gadgets");
            Category accessories = new Category(null, "Accessories", "Useful accessories");
            categoryRepository.saveAll(List.of(electronics, accessories));

            Product laptop = new Product(null, "Laptop", "Gaming laptop", 45999.99, 12);
            laptop.setCategories(List.of(electronics));
            Product mouse = new Product(null, "Mouse", "Wireless mouse", 1299.99, 50);
            mouse.setCategories(List.of(accessories, electronics));
            productRepository.saveAll(List.of(laptop, mouse));

            Customer mykyta = new Customer(null, "Mykyta", "Antonovych", "mykyta@example.com", "+380991112233");
            Customer ivan = new Customer(null, "Ivan", "Petrenko", "ivan@example.com", "+380671234567");
            customerRepository.saveAll(List.of(mykyta, ivan));

            Order firstOrder = new Order(null, LocalDate.now(), OrderStatus.ACTIVE, 47299.98);
            firstOrder.setCustomer(mykyta);
            firstOrder.setProducts(List.of(laptop, mouse));

            Order secondOrder = new Order(null, LocalDate.now().minusDays(1), OrderStatus.COMPLETED, 1299.99);
            secondOrder.setCustomer(ivan);
            secondOrder.setProducts(List.of(mouse));
            orderRepository.saveAll(List.of(firstOrder, secondOrder));

            OrderItem firstOrderItem = new OrderItem(null, 1, 45999.99);
            firstOrderItem.setOrder(firstOrder);
            firstOrderItem.setProduct(laptop);

            OrderItem secondOrderItem = new OrderItem(null, 1, 1299.99);
            secondOrderItem.setOrder(firstOrder);
            secondOrderItem.setProduct(mouse);

            OrderItem thirdOrderItem = new OrderItem(null, 1, 1299.99);
            thirdOrderItem.setOrder(secondOrder);
            thirdOrderItem.setProduct(mouse);
            orderItemRepository.saveAll(List.of(firstOrderItem, secondOrderItem, thirdOrderItem));
        };
    }
}
