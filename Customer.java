package com.vcet.vehicleservice.model;

import java.sql.Timestamp;

/**
 * Model representing a Vehicle Service Center Customer.
 * Maps to the 'customers' table in the database.
 */
public class Customer {
    private int customerId;
    private String name;
    private String phone;
    private String email;
    private String address;
    private Timestamp createdAt;

    public Customer() {
    }

    public Customer(String name, String phone, String email, String address) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Customer(int customerId, String name, String phone, String email, String address, Timestamp createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.createdAt = createdAt;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return String.format("[ID: %-3d] %-18s | Phone: %-12s | Email: %-22s | Address: %s",
                customerId, name, phone, email, address);
    }
}
