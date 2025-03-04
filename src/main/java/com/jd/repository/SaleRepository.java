package com.jd.repository;

import com.jd.dto.SaleOrderHistoryDTO;
import com.jd.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Integer> {

    // query to fetch sales by customer_id
    @Query("SELECT new com.jd.dto.SaleOrderHistoryDTO(s.id, s.orderId, s.gameId, s.quantity, s.price, o.totalPrice, o.orderDate, v.title, v.platform) " +
            "FROM Sale s " +
            "JOIN Order o ON s.orderId = o.orderId " +
            "JOIN VideoGame v ON s.gameId = v.id " +
            "WHERE o.customerId = :customerId")
    List<SaleOrderHistoryDTO> getSalesHistoryByCustomerId(@Param("customerId") int customerId);
}
