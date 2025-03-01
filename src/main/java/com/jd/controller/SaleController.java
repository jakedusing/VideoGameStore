package com.jd.controller;

import com.jd.model.Sale;
import com.jd.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    @Autowired
    private SaleRepository saleRepository;

    // Get all sales
    @GetMapping
    public List<Sale> getAllSales() {
        List<Sale> sales = saleRepository.findAll();
        System.out.println("Fetched sales: " + sales);
        return sales;
    }
}
