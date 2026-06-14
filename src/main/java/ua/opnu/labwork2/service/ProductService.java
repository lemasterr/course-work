package ua.opnu.labwork2.service;

import java.util.List;
import org.springframework.stereotype.Service;
import ua.opnu.labwork2.exception.ResourceNotFoundException;
import ua.opnu.labwork2.model.Product;
import ua.opnu.labwork2.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public Product update(Long id, Product updatedProduct) {
        Product existing = getById(id);
        existing.setName(updatedProduct.getName());
        existing.setDescription(updatedProduct.getDescription());
        existing.setPrice(updatedProduct.getPrice());
        existing.setStockQuantity(updatedProduct.getStockQuantity());
        return productRepository.save(existing);
    }

    public Product delete(Long id) {
        Product product = getById(id);
        productRepository.delete(product);
        return product;
    }

    public List<Product> getByCategoryId(Long categoryId) {
        return productRepository.findByCategoriesId(categoryId);
    }

    public List<Product> search(String query) {
        return productRepository.findByNameContainingIgnoreCase(query);
    }

    public List<Product> advancedSearch(String query, Double price, Long categoryId) {
        return productRepository.findByNameContainingIgnoreCaseAndPriceLessThanEqualAndCategoriesId(query, price, categoryId);
    }

    public long count() {
        return productRepository.count();
    }
}
