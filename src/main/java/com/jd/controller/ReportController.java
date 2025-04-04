package com.jd.controller;

import com.jd.dto.DailySalesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/daily-sales")
    public List<DailySalesDTO> getDailySalesReport() {
        String sql = """
        SELECT DATE(o.order_date) AS sale_date,
               COUNT(DISTINCT o.order_id) AS total_orders,
               SUM(s.price * s.quantity) AS total_revenue
        FROM orders o
        JOIN sales s ON o.order_id = s.order_id
        GROUP BY DATE(o.order_date)
        ORDER BY sale_date DESC
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new DailySalesDTO(
                        rs.getDate("sale_date").toLocalDate(),
                        rs.getInt("total_orders"),
                        rs.getBigDecimal("total_revenue")
                )
        );
    }
}