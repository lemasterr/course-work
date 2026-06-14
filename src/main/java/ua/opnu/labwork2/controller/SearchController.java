package ua.opnu.labwork2.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.opnu.labwork2.model.Customer;
import ua.opnu.labwork2.model.Product;
import ua.opnu.labwork2.service.CustomerService;
import ua.opnu.labwork2.service.ProductService;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final ProductService productService;
    private final CustomerService customerService;

    public SearchController(ProductService productService, CustomerService customerService) {
        this.productService = productService;
        this.customerService = customerService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String query) {
        return ResponseEntity.ok(productService.search(query));
    }

    @GetMapping("/products/advanced")
    public ResponseEntity<List<Product>> advancedSearchProducts(
            @RequestParam String query,
            @RequestParam Double price,
            @RequestParam Long category
    ) {
        return ResponseEntity.ok(productService.advancedSearch(query, price, category));
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> searchCustomers(@RequestParam String query) {
        return ResponseEntity.ok(customerService.search(query));
    }
}
