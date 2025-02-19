package com.jd;

public class Employee {

    private int employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String hireDate;
    private double salary;
    private String password;
    private java.sql.Timestamp createdAt;

    public Employee(int employeeId, String firstName, String lastName, String email,
                    String phoneNumber, String hireDate, double salary, String password, java.sql.Timestamp createdAt) {

        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.hireDate = hireDate;
        this.salary = salary;
        this.password = password;
        this.createdAt = createdAt;
    }

    public Employee(String firstName, String lastName, String email,
                     String phoneNumber, String hireDate, double salary) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.hireDate = hireDate;
        this.salary = salary;
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

    public String getHireDate() {
        return hireDate;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "com.jd.Employee{" +
                "employeeID=" + employeeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", hireDate='" + hireDate + '\'' +
                ", salary=$'" + salary + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
