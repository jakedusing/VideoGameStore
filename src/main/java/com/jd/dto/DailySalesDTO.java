package com.jd.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DailySalesDTO {
    private LocalDate saleDate;
    private int totalOrders;
    private BigDecimal totalRevenue;

    public DailySalesDTO(LocalDate saleDate, int totalOrders, BigDecimal totalRevenue) {
        this.saleDate = saleDate;
        this.totalOrders = totalOrders;
        this.totalRevenue = totalRevenue;
    }

    public LocalDate getSaleDate() { return saleDate; }
    public int getTotalOrders() { return totalOrders; }
    public BigDecimal getTotalRevenue() { return totalRevenue; }
}
