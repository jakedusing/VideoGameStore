package com.jd.controller;

import com.jd.dto.SaleOrderHistoryDTO;
import com.jd.model.Sale;
import com.jd.repository.SaleRepository;
import com.jd.repository.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private VideoGameRepository videoGameRepository;


    // Get all sales
    @GetMapping
    public List<Sale> getAllSales() {
        List<Sale> sales = saleRepository.findAll();
        System.out.println("Fetched sales: " + sales);
        return sales;
    }

    // get sales by customer_id
    @GetMapping("/customer/{customerId}")
    public List<SaleOrderHistoryDTO> getSalesHistory(@PathVariable int customerId) {
        return saleRepository.getSalesHistoryByCustomerId(customerId);
    }

    
}
