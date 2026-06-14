package ua.opnu.labwork2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.opnu.labwork2.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
