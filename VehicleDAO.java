package com.vcet.vehicleservice.dao;

import com.vcet.vehicleservice.model.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Vehicle entities.
 */
public class VehicleDAO {

    public int addVehicle(Vehicle vehicle) throws SQLException {
        String sql = "INSERT INTO vehicles (customer_id, reg_number, model, vehicle_type, manufacture_year) VALUES (?, ?, ?, ?, ?)";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, vehicle.getCustomerId());
            pstmt.setString(2, vehicle.getRegNumber().toUpperCase().trim());
            pstmt.setString(3, vehicle.getModel());
            pstmt.setString(4, vehicle.getVehicleType());
            pstmt.setInt(5, vehicle.getManufactureYear());
            int affected = pstmt.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        vehicle.setVehicleId(id);
                        return id;
                    }
                }
            }
        }
        return -1;
    }

    public Vehicle getVehicleById(int vehicleId) throws SQLException {
        String sql = "SELECT vehicle_id, customer_id, reg_number, model, vehicle_type, manufacture_year FROM vehicles WHERE vehicle_id = ?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, vehicleId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    public Vehicle getVehicleByRegNumber(String regNumber) throws SQLException {
        String sql = "SELECT vehicle_id, customer_id, reg_number, model, vehicle_type, manufacture_year FROM vehicles WHERE UPPER(reg_number) = ?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, regNumber.toUpperCase().trim());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    public List<Vehicle> getVehiclesByCustomerId(int customerId) throws SQLException {
        List<Vehicle> list = new ArrayList<>();
        String sql = "SELECT vehicle_id, customer_id, reg_number, model, vehicle_type, manufacture_year FROM vehicles WHERE customer_id = ?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    public List<Vehicle> getAllVehicles() throws SQLException {
        List<Vehicle> list = new ArrayList<>();
        String sql = "SELECT vehicle_id, customer_id, reg_number, model, vehicle_type, manufacture_year FROM vehicles ORDER BY vehicle_id ASC";
        Connection conn = DatabaseConnection.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    private Vehicle mapRow(ResultSet rs) throws SQLException {
        return new Vehicle(
                rs.getInt("vehicle_id"),
                rs.getInt("customer_id"),
                rs.getString("reg_number"),
                rs.getString("model"),
                rs.getString("vehicle_type"),
                rs.getInt("manufacture_year")
        );
    }
}
