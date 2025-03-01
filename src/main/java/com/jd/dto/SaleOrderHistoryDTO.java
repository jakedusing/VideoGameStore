package com.jd.dto;

public class SaleOrderHistoryDTO {

    private int saleId;
    private int orderId;
    private int gameId;
    private int quantity;
    private double price;
    private double totalPrice;
    private String orderDate;

    public SaleOrderHistoryDTO(int saleId, int orderId, int gameId, int quantity, double price, double totalPrice, String orderDate) {
        this.saleId = saleId;
        this.orderId = orderId;
        this.gameId = gameId;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
