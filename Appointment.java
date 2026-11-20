package com.vcet.vehicleservice.model;

/**
 * Model representing a scheduled service appointment.
 * Maps to the 'appointments' table in the database.
 */
public class Appointment {
    private int appointmentId;
    private int vehicleId;
    private String appointmentDate;
    private String serviceType;
    private String status;
    private String notes;

    public Appointment() {
    }

    public Appointment(int vehicleId, String appointmentDate, String serviceType, String status, String notes) {
        this.vehicleId = vehicleId;
        this.appointmentDate = appointmentDate;
        this.serviceType = serviceType;
        this.status = status;
        this.notes = notes;
    }

    public Appointment(int appointmentId, int vehicleId, String appointmentDate, String serviceType, String status, String notes) {
        this.appointmentId = appointmentId;
        this.vehicleId = vehicleId;
        this.appointmentDate = appointmentDate;
        this.serviceType = serviceType;
        this.status = status;
        this.notes = notes;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return String.format("[Appt ID: %-3d] VehID: %-3d | Date: %-10s | Type: %-20s | Status: %-10s | Notes: %s",
                appointmentId, vehicleId, appointmentDate, serviceType, status, (notes != null ? notes : "-"));
    }
}
