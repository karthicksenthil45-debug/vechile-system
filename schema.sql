-- =====================================================================
-- DATABASE SCHEMA DEFINITION (DDL)
-- Vehicle Service Management and Maintenance Analysis System
-- VCET - Department of Information Technology
-- Course: 25ITT31 (Database Management Systems)
-- Compatible with MySQL 8.0+ and SQLite 3.x
-- =====================================================================

-- 1. Drop existing tables if they exist (in reverse foreign-key order)
DROP TABLE IF EXISTS bills;
DROP TABLE IF EXISTS service_records;
DROP TABLE IF EXISTS appointments;
DROP TABLE IF EXISTS vehicles;
DROP TABLE IF EXISTS customers;

-- 2. Customers Table
CREATE TABLE customers (
    customer_id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(100),
    address VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Vehicles Table
CREATE TABLE vehicles (
    vehicle_id INTEGER PRIMARY KEY AUTOINCREMENT,
    customer_id INTEGER NOT NULL,
    reg_number VARCHAR(20) NOT NULL UNIQUE,
    model VARCHAR(100) NOT NULL,
    vehicle_type VARCHAR(50) NOT NULL, -- 'Hatchback', 'Sedan', 'SUV', 'Two-Wheeler', 'Commercial'
    manufacture_year INTEGER NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE
);

-- 4. Appointments Table
CREATE TABLE appointments (
    appointment_id INTEGER PRIMARY KEY AUTOINCREMENT,
    vehicle_id INTEGER NOT NULL,
    appointment_date DATE NOT NULL,
    service_type VARCHAR(100) NOT NULL, -- 'General Service', 'Oil Change', 'Brake Overhaul', 'Engine Diagnostics', 'AC Service', 'Suspension Repair'
    status VARCHAR(30) DEFAULT 'Scheduled', -- 'Scheduled', 'In-Progress', 'Completed', 'Cancelled'
    notes TEXT,
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id) ON DELETE CASCADE
);

-- 5. Service Records Table
CREATE TABLE service_records (
    service_id INTEGER PRIMARY KEY AUTOINCREMENT,
    appointment_id INTEGER NOT NULL UNIQUE,
    technician_name VARCHAR(100) NOT NULL,
    work_performed TEXT NOT NULL,
    spare_parts_cost DECIMAL(10, 2) DEFAULT 0.00,
    labor_charges DECIMAL(10, 2) DEFAULT 0.00,
    service_status VARCHAR(30) DEFAULT 'Completed', -- 'In-Progress', 'Completed', 'Awaiting Parts'
    completion_date DATE,
    FOREIGN KEY (appointment_id) REFERENCES appointments(appointment_id) ON DELETE CASCADE
);

-- 6. Bills Table
CREATE TABLE bills (
    bill_id INTEGER PRIMARY KEY AUTOINCREMENT,
    service_id INTEGER NOT NULL UNIQUE,
    bill_date DATE NOT NULL,
    parts_cost DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    labor_cost DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    tax_amount DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    discount_amount DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    total_amount DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    payment_status VARCHAR(30) DEFAULT 'Paid', -- 'Paid', 'Pending', 'Partially Paid'
    payment_method VARCHAR(50) DEFAULT 'UPI', -- 'Cash', 'Credit Card', 'Debit Card', 'UPI', 'Net Banking'
    FOREIGN KEY (service_id) REFERENCES service_records(service_id) ON DELETE CASCADE
);

-- Performance Indexes (as per 25ITT31 Unit 3: Indexing-B+ tree / Hash indices)
CREATE INDEX idx_vehicle_reg ON vehicles(reg_number);
CREATE INDEX idx_vehicle_customer ON vehicles(customer_id);
CREATE INDEX idx_appointment_vehicle ON appointments(vehicle_id);
CREATE INDEX idx_appointment_date ON appointments(appointment_date);
CREATE INDEX idx_service_appointment ON service_records(appointment_id);
CREATE INDEX idx_bill_service ON bills(service_id);
