package com.vcet.vehicleservice.dao;

import com.vcet.vehicleservice.model.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Appointment scheduling.
 */
public class AppointmentDAO {

    public int addAppointment(Appointment appt) throws SQLException {
        String sql = "INSERT INTO appointments (vehicle_id, appointment_date, service_type, status, notes) VALUES (?, ?, ?, ?, ?)";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, appt.getVehicleId());
            pstmt.setString(2, appt.getAppointmentDate());
            pstmt.setString(3, appt.getServiceType());
            pstmt.setString(4, appt.getStatus() != null ? appt.getStatus() : "Scheduled");
            pstmt.setString(5, appt.getNotes());
            int affected = pstmt.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        appt.setAppointmentId(id);
                        return id;
                    }
                }
            }
        }
        return -1;
    }

    public Appointment getAppointmentById(int appointmentId) throws SQLException {
        String sql = "SELECT appointment_id, vehicle_id, appointment_date, service_type, status, notes FROM appointments WHERE appointment_id = ?";
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

    public List<Appointment> getAppointmentsByVehicleId(int vehicleId) throws SQLException {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT appointment_id, vehicle_id, appointment_date, service_type, status, notes FROM appointments WHERE vehicle_id = ? ORDER BY appointment_date DESC";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, vehicleId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    public List<Appointment> getAllAppointments() throws SQLException {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT appointment_id, vehicle_id, appointment_date, service_type, status, notes FROM appointments ORDER BY appointment_id ASC";
        Connection conn = DatabaseConnection.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    public boolean updateStatus(int appointmentId, String status) throws SQLException {
        String sql = "UPDATE appointments SET status = ? WHERE appointment_id = ?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, appointmentId);
            return pstmt.executeUpdate() > 0;
        }
    }

    private Appointment mapRow(ResultSet rs) throws SQLException {
        return new Appointment(
                rs.getInt("appointment_id"),
                rs.getInt("vehicle_id"),
                rs.getString("appointment_date"),
                rs.getString("service_type"),
                rs.getString("status"),
                rs.getString("notes")
        );
    }
}
