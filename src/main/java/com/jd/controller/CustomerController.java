package com.jd.controller;

import com.jd.model.Customer;
import com.jd.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    // Get all customers
    @GetMapping
    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        System.out.println("Fetched customers: " + customers);
        return customers;
    }

    // get a customer by id
    @GetMapping("/{id}")
    public Optional<Customer> getCustomerById(@PathVariable int id) {
        return customerRepository.findById(id);
    }

    // get a customer by firstName
    @GetMapping("/search")
    public ResponseEntity<List<Customer>> searchCustomerByFirstName(@RequestParam String firstName) {
        List<Customer> customers = customerRepository.findByFirstNameContainingIgnoreCase(firstName);
        return ResponseEntity.ok(customers);
    }

    // create a new customer
    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        try {
            System.out.println("Received customer: " + customer);
            System.out.println("First Name: " + customer.getFirstName());
            System.out.println("Last Name: " + customer.getLastName());
            System.out.println("Email: " + customer.getEmail());
            System.out.println("Phone: " + customer.getPhoneNumber());

            Customer savedCustomer = customerRepository.save(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error saving customer: " + e.getMessage());
        }
    }
}
