import java.util.List;

public class Order {
    private List<Sale> sales;
    private int customerId;

    public Order(List<Sale> sales, int customerId) {
        this.sales = sales;
        this.customerId = customerId;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public int getCustomerId() {
        return customerId;
    }
}
