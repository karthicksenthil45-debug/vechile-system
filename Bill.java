package com.vcet.vehicleservice.model;

/**
 * Model representing a customer invoice/bill.
 * Maps to the 'bills' table in the database.
 */
public class Bill {
    private int billId;
    private int serviceId;
    private String billDate;
    private double partsCost;
    private double laborCost;
    private double taxAmount;
    private double discountAmount;
    private double totalAmount;
    private String paymentStatus;
    private String paymentMethod;

    public Bill() {
    }

    public Bill(int serviceId, String billDate, double partsCost, double laborCost,
                double taxAmount, double discountAmount, double totalAmount,
                String paymentStatus, String paymentMethod) {
        this.serviceId = serviceId;
        this.billDate = billDate;
        this.partsCost = partsCost;
        this.laborCost = laborCost;
        this.taxAmount = taxAmount;
        this.discountAmount = discountAmount;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
    }

    public Bill(int billId, int serviceId, String billDate, double partsCost, double laborCost,
                double taxAmount, double discountAmount, double totalAmount,
                String paymentStatus, String paymentMethod) {
        this.billId = billId;
        this.serviceId = serviceId;
        this.billDate = billDate;
        this.partsCost = partsCost;
        this.laborCost = laborCost;
        this.taxAmount = taxAmount;
        this.discountAmount = discountAmount;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public double getPartsCost() {
        return partsCost;
    }

    public void setPartsCost(double partsCost) {
        this.partsCost = partsCost;
    }

    public double getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(double laborCost) {
        this.laborCost = laborCost;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String generateInvoiceReceipt() {
        StringBuilder sb = new StringBuilder();
        sb.append("=========================================================\n");
        sb.append("          VEHICLE SERVICE CENTER - OFFICIAL INVOICE      \n");
        sb.append("=========================================================\n");
        sb.append(String.format("Invoice No   : INV-%05d          Date: %s\n", billId, billDate));
        sb.append(String.format("Service Ref  : SRV-%05d          Status: %s\n", serviceId, paymentStatus));
        sb.append("---------------------------------------------------------\n");
        sb.append(String.format("Spare Parts Charges           : Rs. %10.2f\n", partsCost));
        sb.append(String.format("Technician Labor Charges      : Rs. %10.2f\n", laborCost));
        sb.append(String.format("Subtotal                      : Rs. %10.2f\n", (partsCost + laborCost)));
        sb.append(String.format("Applicable GST (18%%)          : Rs. %10.2f\n", taxAmount));
        sb.append(String.format("Discount Applied              : Rs. -%9.2f\n", discountAmount));
        sb.append("---------------------------------------------------------\n");
        sb.append(String.format("NET PAYABLE AMOUNT            : Rs. %10.2f\n", totalAmount));
        sb.append(String.format("Payment Mode                  : %s\n", paymentMethod));
        sb.append("=========================================================\n");
        sb.append("      Thank you for choosing our Service Center!         \n");
        sb.append("=========================================================\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("[Bill ID: %-3d] SrvID: %-3d | Date: %-10s | Parts: Rs.%7.2f | Labor: Rs.%7.2f | Total: Rs.%8.2f | Status: %s",
                billId, serviceId, billDate, partsCost, laborCost, totalAmount, paymentStatus);
    }
}
