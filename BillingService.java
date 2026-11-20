package com.vcet.vehicleservice.service;

import com.vcet.vehicleservice.dao.BillDAO;
import com.vcet.vehicleservice.dao.ServiceRecordDAO;
import com.vcet.vehicleservice.model.Bill;
import com.vcet.vehicleservice.model.ServiceRecord;

import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Service Layer handling billing calculations and business rules.
 * Implements Functional Requirement 5 (Calculate service bills).
 */
public class BillingService {

    public static final double STANDARD_GST_RATE = 0.18; // 18% GST

    private final ServiceRecordDAO serviceRecordDAO;
    private final BillDAO billDAO;

    public BillingService() {
        this.serviceRecordDAO = new ServiceRecordDAO();
        this.billDAO = new BillDAO();
    }

    /**
     * Calculates and generates a persistent bill for a completed service.
     */
    public Bill generateBill(int serviceId, double discountPercentage, String paymentMethod) throws SQLException {
        ServiceRecord service = serviceRecordDAO.getServiceRecordById(serviceId);
        if (service == null) {
            throw new IllegalArgumentException("Service Record ID " + serviceId + " does not exist.");
        }

        Bill existingBill = billDAO.getBillByServiceId(serviceId);
        if (existingBill != null) {
            throw new IllegalStateException("Service Record ID " + serviceId + " is already billed (Bill ID: " + existingBill.getBillId() + ").");
        }

        double parts = service.getSparePartsCost();
        double labor = service.getLaborCharges();
        double subtotal = parts + labor;

        if (discountPercentage < 0 || discountPercentage > 100) {
            throw new IllegalArgumentException("Discount percentage must be between 0% and 100%.");
        }

        double taxAmount = Math.round(subtotal * STANDARD_GST_RATE * 100.0) / 100.0;
        double discountAmount = Math.round(subtotal * (discountPercentage / 100.0) * 100.0) / 100.0;
        double totalAmount = Math.round((subtotal + taxAmount - discountAmount) * 100.0) / 100.0;

        String today = LocalDate.now().toString();
        Bill bill = new Bill(
                serviceId,
                today,
                parts,
                labor,
                taxAmount,
                discountAmount,
                totalAmount,
                "Paid",
                paymentMethod != null && !paymentMethod.trim().isEmpty() ? paymentMethod : "UPI"
        );

        int newBillId = billDAO.addBill(bill);
        bill.setBillId(newBillId);
        return bill;
    }
}
