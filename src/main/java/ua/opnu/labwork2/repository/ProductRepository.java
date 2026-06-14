package ua.opnu.labwork2.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.opnu.labwork2.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByNameContainingIgnoreCaseAndPriceLessThanEqual(String name, Double price);

    List<Product> findByCategoriesId(Long categoryId);

    List<Product> findByNameContainingIgnoreCaseAndPriceLessThanEqualAndCategoriesId(String name, Double price, Long categoryId);
}
