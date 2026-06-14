package ua.opnu.labwork2.service;

import java.util.List;
import org.springframework.stereotype.Service;
import ua.opnu.labwork2.exception.ResourceNotFoundException;
import ua.opnu.labwork2.model.Customer;
import ua.opnu.labwork2.model.Order;
import ua.opnu.labwork2.repository.CustomerRepository;
import ua.opnu.labwork2.repository.OrderRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public CustomerService(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer getById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    public Customer update(Long id, Customer updatedCustomer) {
        Customer existing = getById(id);
        existing.setFirstName(updatedCustomer.getFirstName());
        existing.setLastName(updatedCustomer.getLastName());
        existing.setEmail(updatedCustomer.getEmail());
        existing.setPhone(updatedCustomer.getPhone());
        return customerRepository.save(existing);
    }

    public Customer delete(Long id) {
        Customer customer = getById(id);
        customerRepository.delete(customer);
        return customer;
    }

    public List<Order> getOrdersByCustomerId(Long customerId) {
        getById(customerId);
        return orderRepository.findByCustomerId(customerId);
    }

    public List<Customer> search(String query) {
        return customerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query);
    }

    public long count() {
        return customerRepository.count();
    }
}
