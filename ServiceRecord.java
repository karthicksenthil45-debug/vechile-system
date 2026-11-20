package com.vcet.vehicleservice.model;

/**
 * Model representing maintenance work executed by a technician.
 * Maps to the 'service_records' table in the database.
 */
public class ServiceRecord {
    private int serviceId;
    private int appointmentId;
    private String technicianName;
    private String workPerformed;
    private double sparePartsCost;
    private double laborCharges;
    private String serviceStatus;
    private String completionDate;

    public ServiceRecord() {
    }

    public ServiceRecord(int appointmentId, String technicianName, String workPerformed,
                         double sparePartsCost, double laborCharges, String serviceStatus, String completionDate) {
        this.appointmentId = appointmentId;
        this.technicianName = technicianName;
        this.workPerformed = workPerformed;
        this.sparePartsCost = sparePartsCost;
        this.laborCharges = laborCharges;
        this.serviceStatus = serviceStatus;
        this.completionDate = completionDate;
    }

    public ServiceRecord(int serviceId, int appointmentId, String technicianName, String workPerformed,
                         double sparePartsCost, double laborCharges, String serviceStatus, String completionDate) {
        this.serviceId = serviceId;
        this.appointmentId = appointmentId;
        this.technicianName = technicianName;
        this.workPerformed = workPerformed;
        this.sparePartsCost = sparePartsCost;
        this.laborCharges = laborCharges;
        this.serviceStatus = serviceStatus;
        this.completionDate = completionDate;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getTechnicianName() {
        return technicianName;
    }

    public void setTechnicianName(String technicianName) {
        this.technicianName = technicianName;
    }

    public String getWorkPerformed() {
        return workPerformed;
    }

    public void setWorkPerformed(String workPerformed) {
        this.workPerformed = workPerformed;
    }

    public double getSparePartsCost() {
        return sparePartsCost;
    }

    public void setSparePartsCost(double sparePartsCost) {
        this.sparePartsCost = sparePartsCost;
    }

    public double getLaborCharges() {
        return laborCharges;
    }

    public void setLaborCharges(double laborCharges) {
        this.laborCharges = laborCharges;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public double getSubtotal() {
        return sparePartsCost + laborCharges;
    }

    @Override
    public String toString() {
        return String.format("[Service ID: %-3d] ApptID: %-3d | Tech: %-14s | Parts: Rs. %8.2f | Labor: Rs. %8.2f | Status: %s",
                serviceId, appointmentId, technicianName, sparePartsCost, laborCharges, serviceStatus);
    }
}
