public class Sale {

    private int gameId;
    private int employeeId;
    private int quantity;
    private double totalPrice;

    public Sale(int gameId, int employeeId, int quantity, double totalPrice) {
        this.gameId = gameId;
        this.employeeId = employeeId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
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
}
