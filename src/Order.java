import java.util.List;

public class Order {
    private List<Sale> sales;
    private int customerId;
    private int employeeId;
    private double orderTotal;

    public Order(List<Sale> sales, int customerId, int employeeId) {
        this.sales = sales;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.orderTotal = 0;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void addToOrderTotal(double amount) {
        this.orderTotal += amount;
    }
}
