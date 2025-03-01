package com.jd.repository;

import com.jd.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Integer> {

    // query to fetch sales by customer_id
    @Query("SELECT s FROM Sale s JOIN Order o ON s.orderId = o.orderId WHERE o.customerId = :customerId")
    List<Sale> findByCustomerId(int customerId);
}
