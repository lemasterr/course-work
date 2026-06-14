package ua.opnu.labwork2.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.opnu.labwork2.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
}
