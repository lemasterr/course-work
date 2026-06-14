package ua.opnu.labwork2.service;

import java.util.List;
import org.springframework.stereotype.Service;
import ua.opnu.labwork2.exception.ResourceNotFoundException;
import ua.opnu.labwork2.model.Category;
import ua.opnu.labwork2.model.Product;
import ua.opnu.labwork2.repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    public Category update(Long id, Category updatedCategory) {
        Category existing = getById(id);
        existing.setName(updatedCategory.getName());
        existing.setDescription(updatedCategory.getDescription());
        return categoryRepository.save(existing);
    }

    public Category delete(Long id) {
        Category category = getById(id);
        categoryRepository.delete(category);
        return category;
    }

    public List<Product> getProductsByCategoryId(Long id) {
        return getById(id).getProducts();
    }

    public long count() {
        return categoryRepository.count();
    }
}
