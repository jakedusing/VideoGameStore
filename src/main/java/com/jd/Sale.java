package com.jd;

public class Sale {

    private int saleId;
    private int orderId;
    private int gameId;
    private String gameName;
    private int employeeId;
    private int quantity;
    private double price;
    private double totalPrice;
    private java.sql.Timestamp saleDate;
    private java.sql.Timestamp orderDate;
    private int customerId;

    public Sale(int saleId, int gameId, int employeeId, int quantity,
                double totalPrice, java.sql.Timestamp saleDate, int customerId) {

        this.saleId = saleId;
        this.gameId = gameId;
        this.employeeId = employeeId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.saleDate = saleDate;
        this.customerId = customerId;
    }

    public Sale(int orderId, java.sql.Timestamp orderDate, int gameId, String gameName, int quantity, double price) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.gameId = gameId;
        this.gameName = gameName;
        this.quantity = quantity;
        this.price = price;
    }

    public Sale(int gameId, int employeeId, int quantity, int customerId) {
        this.gameId = gameId;
        this.employeeId = employeeId;
        this.quantity = quantity;
        this.customerId = customerId;
    }

    public Sale(int gameId, int employeeId, int quantity, double totalPrice, int customerId) {
        this.gameId = gameId;
        this.employeeId = employeeId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.customerId = customerId;
    }

    public Sale(int orderId, int saleId, int gameId, int quantity, double price) {
        this.orderId = orderId;
        this.saleId = saleId;
        this.gameId = gameId;
        this.quantity = quantity;
        this.price = price;
    }

    public int getSaleId() {
        return saleId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getGameId() {
        return gameId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public java.sql.Timestamp getSaleDate() {
        return saleDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return "com.jd.Sale{" +
                "saleId=" + saleId +
                ", orderId=" + orderId +   // Include orderId
                ", gameId=" + gameId +
                ", gameName='" + gameName + '\'' + // Optional if fetched via JOIN
                ", employeeId=" + employeeId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", orderDate=" + orderDate +  // Include saleDate
                ", customerId=" + customerId +
                '}';
    }
}
