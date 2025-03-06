package com.jd.controller;

import com.jd.dto.SaleOrderHistoryDTO;
import com.jd.dto.SaleRequest;
import com.jd.dto.SaleResponse;
import com.jd.model.Order;
import com.jd.model.Sale;
import com.jd.model.VideoGame;
import com.jd.repository.OrderRepository;
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

    @Autowired
    private OrderRepository orderRepository;


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

    // Create sale (POST /api/sales)
    @PostMapping
    public SaleResponse createSale(@RequestBody SaleRequest saleRequest) {
        // Step 1: Create an order manually
        Order order = new Order();
        order.setCustomerId((saleRequest.getCustomerId()));
        order.setOrderDate(saleRequest.getOrderDate());
        order.setEmployeeId(saleRequest.getEmployeeId());
        order.setTotalPrice(0.0); // initial total, will update later

        // Save the order first so it gets an order_id
        order = orderRepository.save(order);

        double totalPrice = 0;

        // Step 2: Loop through each item, create a sale for each, and calculate the total price
        for (SaleRequest.SaleItem item: saleRequest.getItems()) {
            VideoGame game = videoGameRepository.findById(item.getGameId())
                    .orElseThrow(() -> new RuntimeException("Game not found"));

            // Check if there is enough stock
            if (game.getStock() < item.getQuantity()) {
                throw new RuntimeException("Not enough stock for game: " + game.getTitle());
            }

            Sale sale = new Sale();
            sale.setOrderId(order.getOrderId());
            sale.setGameId(game.getId());
            sale.setQuantity(item.getQuantity());
            sale.setPrice(game.getPrice());

            // Save sale to the database
            saleRepository.save(sale);

            // Deduct stock from the game
            game.setStock(game.getStock() - item.getQuantity());
            videoGameRepository.save(game);

            // Update total price
            totalPrice += game.getPrice() * item.getQuantity();
        }

        // Step 3: Update the total price of the order
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);

        // Step 4: Return response with orderId and totalPrice
        SaleResponse response = new SaleResponse();
        response.setOrderId(order.getOrderId());
        response.setTotalPrice(totalPrice);
        return response;
    }
}
