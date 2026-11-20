package com.vcet.vehicleservice.dao;

import com.vcet.vehicleservice.model.Bill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Bills and Invoices.
 */
public class BillDAO {

    public int addBill(Bill bill) throws SQLException {
        String sql = "INSERT INTO bills (service_id, bill_date, parts_cost, labor_cost, tax_amount, discount_amount, total_amount, payment_status, payment_method) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, bill.getServiceId());
            pstmt.setString(2, bill.getBillDate());
            pstmt.setDouble(3, bill.getPartsCost());
            pstmt.setDouble(4, bill.getLaborCost());
            pstmt.setDouble(5, bill.getTaxAmount());
            pstmt.setDouble(6, bill.getDiscountAmount());
            pstmt.setDouble(7, bill.getTotalAmount());
            pstmt.setString(8, bill.getPaymentStatus() != null ? bill.getPaymentStatus() : "Paid");
            pstmt.setString(9, bill.getPaymentMethod() != null ? bill.getPaymentMethod() : "UPI");
            int affected = pstmt.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        bill.setBillId(id);
                        return id;
                    }
                }
            }
        }
        return -1;
    }

    public Bill getBillById(int billId) throws SQLException {
        String sql = "SELECT bill_id, service_id, bill_date, parts_cost, labor_cost, tax_amount, discount_amount, total_amount, payment_status, payment_method " +
                     "FROM bills WHERE bill_id = ?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, billId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    public Bill getBillByServiceId(int serviceId) throws SQLException {
        String sql = "SELECT bill_id, service_id, bill_date, parts_cost, labor_cost, tax_amount, discount_amount, total_amount, payment_status, payment_method " +
                     "FROM bills WHERE service_id = ?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, serviceId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    public List<Bill> getAllBills() throws SQLException {
        List<Bill> list = new ArrayList<>();
        String sql = "SELECT bill_id, service_id, bill_date, parts_cost, labor_cost, tax_amount, discount_amount, total_amount, payment_status, payment_method " +
                     "FROM bills ORDER BY bill_id ASC";
        Connection conn = DatabaseConnection.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    private Bill mapRow(ResultSet rs) throws SQLException {
        return new Bill(
                rs.getInt("bill_id"),
                rs.getInt("service_id"),
                rs.getString("bill_date"),
                rs.getDouble("parts_cost"),
                rs.getDouble("labor_cost"),
                rs.getDouble("tax_amount"),
                rs.getDouble("discount_amount"),
                rs.getDouble("total_amount"),
                rs.getString("payment_status"),
                rs.getString("payment_method")
        );
    }
}
