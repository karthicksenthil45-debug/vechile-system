package com.vcet.vehicleservice.service;

import com.vcet.vehicleservice.dao.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Service Layer for Operational Reports and Business Summaries.
 * Implements Functional Requirement 6:
 * - Service history tracking
 * - Vehicle-wise maintenance costs
 * - Service center workload
 */
public class ReportService {

    public void printVehicleServiceHistory(String regNumber) {
        String sql = "SELECT " +
                "    v.reg_number, v.model, c.name AS customer_name, " +
                "    a.appointment_date, a.service_type, " +
                "    sr.technician_name, sr.work_performed, " +
                "    sr.spare_parts_cost, sr.labor_charges, " +
                "    b.total_amount, b.payment_status " +
                "FROM vehicles v " +
                "JOIN customers c ON v.customer_id = c.customer_id " +
                "JOIN appointments a ON v.vehicle_id = a.vehicle_id " +
                "JOIN service_records sr ON a.appointment_id = sr.appointment_id " +
                "LEFT JOIN bills b ON sr.service_id = b.service_id " +
                "WHERE UPPER(v.reg_number) = ? " +
                "ORDER BY a.appointment_date DESC";

        try {
            Connection conn = DatabaseConnection.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, regNumber.toUpperCase().trim());
                try (ResultSet rs = pstmt.executeQuery()) {
                    System.out.println("\n" + "=".repeat(95));
                    System.out.println("                   SERVICE HISTORY REPORT FOR VEHICLE: " + regNumber.toUpperCase());
                    System.out.println("=".repeat(95));
                    boolean found = false;
                    while (rs.next()) {
                        found = true;
                        System.out.printf("Date: %-10s | Service: %-22s | Tech: %-15s\n",
                                rs.getString("appointment_date"), rs.getString("service_type"), rs.getString("technician_name"));
                        System.out.printf("  Work Performed: %s\n", rs.getString("work_performed"));
                        System.out.printf("  Parts: Rs. %-8.2f | Labor: Rs. %-8.2f | Total Billed: Rs. %-8.2f | Payment: %s\n",
                                rs.getDouble("spare_parts_cost"), rs.getDouble("labor_charges"),
                                rs.getDouble("total_amount"), rs.getString("payment_status"));
                        System.out.println("-".repeat(95));
                    }
                    if (!found) {
                        System.out.println("  No service records found for vehicle registration: " + regNumber);
                    }
                    System.out.println("=".repeat(95));
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error generating service history: " + e.getMessage());
        }
    }

    public void printVehicleWiseMaintenanceCosts() {
        String sql = "SELECT " +
                "    v.reg_number, v.model, v.vehicle_type, " +
                "    COUNT(sr.service_id) AS service_count, " +
                "    SUM(sr.spare_parts_cost) AS total_parts, " +
                "    SUM(sr.labor_charges) AS total_labor, " +
                "    SUM(b.total_amount) AS cumulative_cost, " +
                "    AVG(b.total_amount) AS avg_cost " +
                "FROM vehicles v " +
                "JOIN appointments a ON v.vehicle_id = a.vehicle_id " +
                "JOIN service_records sr ON a.appointment_id = sr.appointment_id " +
                "JOIN bills b ON sr.service_id = b.service_id " +
                "GROUP BY v.vehicle_id, v.reg_number, v.model, v.vehicle_type " +
                "ORDER BY cumulative_cost DESC";

        try {
            Connection conn = DatabaseConnection.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                System.out.println("\n" + "=".repeat(105));
                System.out.println("                   VEHICLE-WISE CUMULATIVE MAINTENANCE COSTS SUMMARY");
                System.out.println("=".repeat(105));
                System.out.printf("%-12s | %-20s | %-12s | %-5s | %-12s | %-12s | %-14s | %-10s\n",
                        "Reg Number", "Model", "Type", "Visits", "Parts (Rs)", "Labor (Rs)", "Total Cost(Rs)", "Avg Cost");
                System.out.println("-".repeat(105));
                while (rs.next()) {
                    System.out.printf("%-12s | %-20s | %-12s | %-6d | %10.2f | %10.2f | %14.2f | %10.2f\n",
                            rs.getString("reg_number"),
                            rs.getString("model"),
                            rs.getString("vehicle_type"),
                            rs.getInt("service_count"),
                            rs.getDouble("total_parts"),
                            rs.getDouble("total_labor"),
                            rs.getDouble("cumulative_cost"),
                            rs.getDouble("avg_cost"));
                }
                System.out.println("=".repeat(105));
            }
        } catch (SQLException e) {
            System.err.println("Database error generating maintenance cost summary: " + e.getMessage());
        }
    }

    public void printServiceCenterWorkload() {
        String statusSql = "SELECT status, COUNT(*) AS count FROM appointments GROUP BY status";
        String categorySql = "SELECT a.service_type, COUNT(*) AS count, SUM(b.total_amount) AS revenue " +
                "FROM appointments a " +
                "JOIN service_records sr ON a.appointment_id = sr.appointment_id " +
                "JOIN bills b ON sr.service_id = b.service_id " +
                "GROUP BY a.service_type ORDER BY count DESC";

        try {
            Connection conn = DatabaseConnection.getConnection();
            System.out.println("\n" + "=".repeat(75));
            System.out.println("                   SERVICE CENTER WORKLOAD & PERFORMANCE");
            System.out.println("=".repeat(75));

            System.out.println("\n[1] APPOINTMENT STATUS DISTRIBUTION:");
            try (PreparedStatement pstmt = conn.prepareStatement(statusSql);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.printf("  * %-16s : %3d appointments\n", rs.getString("status"), rs.getInt("count"));
                }
            }

            System.out.println("\n[2] TOP SERVICE CATEGORIES & REVENUE EARNED:");
            System.out.printf("  %-25s | %-8s | %-15s\n", "Service Type", "Jobs", "Revenue (Rs)");
            System.out.println("  " + "-".repeat(55));
            try (PreparedStatement pstmt = conn.prepareStatement(categorySql);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.printf("  %-25s | %-8d | Rs. %12.2f\n",
                            rs.getString("service_type"), rs.getInt("count"), rs.getDouble("revenue"));
                }
            }
            System.out.println("=".repeat(75));
        } catch (SQLException e) {
            System.err.println("Database error generating workload report: " + e.getMessage());
        }
    }
}
