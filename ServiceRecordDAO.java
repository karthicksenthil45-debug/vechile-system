package com.vcet.vehicleservice.dao;

import com.vcet.vehicleservice.model.ServiceRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Maintenance Service Records.
 */
public class ServiceRecordDAO {

    public int addServiceRecord(ServiceRecord record) throws SQLException {
        String sql = "INSERT INTO service_records (appointment_id, technician_name, work_performed, spare_parts_cost, labor_charges, service_status, completion_date) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, record.getAppointmentId());
            pstmt.setString(2, record.getTechnicianName());
            pstmt.setString(3, record.getWorkPerformed());
            pstmt.setDouble(4, record.getSparePartsCost());
            pstmt.setDouble(5, record.getLaborCharges());
            pstmt.setString(6, record.getServiceStatus() != null ? record.getServiceStatus() : "Completed");
            pstmt.setString(7, record.getCompletionDate());
            int affected = pstmt.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        record.setServiceId(id);
                        return id;
                    }
                }
            }
        }
        return -1;
    }

    public ServiceRecord getServiceRecordById(int serviceId) throws SQLException {
        String sql = "SELECT service_id, appointment_id, technician_name, work_performed, spare_parts_cost, labor_charges, service_status, completion_date " +
                     "FROM service_records WHERE service_id = ?";
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

    public ServiceRecord getServiceRecordByAppointmentId(int appointmentId) throws SQLException {
        String sql = "SELECT service_id, appointment_id, technician_name, work_performed, spare_parts_cost, labor_charges, service_status, completion_date " +
                     "FROM service_records WHERE appointment_id = ?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, appointmentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    public List<ServiceRecord> getAllServiceRecords() throws SQLException {
        List<ServiceRecord> list = new ArrayList<>();
        String sql = "SELECT service_id, appointment_id, technician_name, work_performed, spare_parts_cost, labor_charges, service_status, completion_date " +
                     "FROM service_records ORDER BY service_id ASC";
        Connection conn = DatabaseConnection.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    private ServiceRecord mapRow(ResultSet rs) throws SQLException {
        return new ServiceRecord(
                rs.getInt("service_id"),
                rs.getInt("appointment_id"),
                rs.getString("technician_name"),
                rs.getString("work_performed"),
                rs.getDouble("spare_parts_cost"),
                rs.getDouble("labor_charges"),
                rs.getString("service_status"),
                rs.getString("completion_date")
        );
    }
}
