package com.vcet.vehicleservice.model;

/**
 * Model representing a Vehicle registered for servicing.
 * Maps to the 'vehicles' table in the database.
 */
public class Vehicle {
    private int vehicleId;
    private int customerId;
    private String regNumber;
    private String model;
    private String vehicleType;
    private int manufactureYear;

    public Vehicle() {
    }

    public Vehicle(int customerId, String regNumber, String model, String vehicleType, int manufactureYear) {
        this.customerId = customerId;
        this.regNumber = regNumber;
        this.model = model;
        this.vehicleType = vehicleType;
        this.manufactureYear = manufactureYear;
    }

    public Vehicle(int vehicleId, int customerId, String regNumber, String model, String vehicleType, int manufactureYear) {
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.regNumber = regNumber;
        this.model = model;
        this.vehicleType = vehicleType;
        this.manufactureYear = manufactureYear;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(int manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    @Override
    public String toString() {
        return String.format("[Veh ID: %-3d] Reg: %-12s | Model: %-22s | Type: %-12s | Year: %d | CustID: %d",
                vehicleId, regNumber, model, vehicleType, manufactureYear, customerId);
    }
}
