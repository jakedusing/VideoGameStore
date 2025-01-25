public class Customer {

    private int customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private java.sql.Timestamp createdAt;

    public Customer(int customerId, String firstName, String lastName,
                    String email, String phoneNumber, java.sql.Timestamp createdAt) {

        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
    }

    public Customer(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public java.sql.Timestamp getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerID=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
