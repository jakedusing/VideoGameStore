import java.util.List;

public class Order {
    private List<Sale> sales;
    private int customerId;
    private double orderTotal;

    public Order(List<Sale> sales, int customerId) {
        this.sales = sales;
        this.customerId = customerId;
        this.orderTotal = 0;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public int getCustomerId() {
        return customerId;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void addToOrderTotal(double amount) {
        this.orderTotal += amount;
    }
}
