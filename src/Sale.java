public class Sale {

    private int saleId;
    private int gameId;
    private int employeeId;
    private int quantity;
    private double totalPrice;
    private java.sql.Timestamp saleDate;
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

    public int getGameId() {
        return gameId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public int getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "gameId=" + gameId +
                ", employeeId=" + employeeId +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
