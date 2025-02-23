package com.jd.repository;

import com.jd.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findByFirstNameContainingIgnoreCase(String firstName);
}
