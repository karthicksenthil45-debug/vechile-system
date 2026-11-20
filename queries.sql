-- =====================================================================
-- SQL QUERIES, VIEWS, AND ANALYTICAL REPORTS (DQL)
-- Vehicle Service Management and Maintenance Analysis System
-- VCET - Department of Information Technology
-- Course: 25ITT31 (Database Management Systems - Unit 2 & 3)
-- =====================================================================

-- ---------------------------------------------------------------------
-- 1. BASIC JOINS & SERVICE HISTORY RETRIEVAL
-- Retrieves comprehensive vehicle service history with customer & bill info
-- ---------------------------------------------------------------------
SELECT 
    v.reg_number,
    v.model,
    c.name AS customer_name,
    c.phone,
    a.appointment_date,
    a.service_type,
    sr.technician_name,
    sr.work_performed,
    sr.spare_parts_cost,
    sr.labor_charges,
    b.total_amount AS billed_amount,
    b.payment_status,
    b.payment_method
FROM vehicles v
JOIN customers c ON v.customer_id = c.customer_id
JOIN appointments a ON v.vehicle_id = a.vehicle_id
JOIN service_records sr ON a.appointment_id = sr.appointment_id
JOIN bills b ON sr.service_id = b.service_id
ORDER BY a.appointment_date DESC;

-- ---------------------------------------------------------------------
-- 2. VEHICLE-WISE MAINTENANCE COSTS (AGGREGATE FUNCTIONS)
-- Calculates total spending, average service cost, and visit frequency
-- ---------------------------------------------------------------------
SELECT 
    v.reg_number,
    v.model,
    v.vehicle_type,
    COUNT(sr.service_id) AS total_services_count,
    ROUND(SUM(sr.spare_parts_cost), 2) AS total_spare_parts_cost,
    ROUND(SUM(sr.labor_charges), 2) AS total_labor_charges,
    ROUND(SUM(b.total_amount), 2) AS cumulative_maintenance_cost,
    ROUND(AVG(b.total_amount), 2) AS average_bill_per_visit
FROM vehicles v
JOIN appointments a ON v.vehicle_id = a.vehicle_id
JOIN service_records sr ON a.appointment_id = sr.appointment_id
JOIN bills b ON sr.service_id = b.service_id
GROUP BY v.vehicle_id, v.reg_number, v.model, v.vehicle_type
ORDER BY cumulative_maintenance_cost DESC;

-- ---------------------------------------------------------------------
-- 3. SERVICE CENTER WORKLOAD & STATUS ANALYSIS
-- ---------------------------------------------------------------------
SELECT 
    status,
    COUNT(*) AS appointment_count,
    ROUND(COUNT(*) * 100.0 / (SELECT COUNT(*) FROM appointments), 2) AS percentage_share
FROM appointments
GROUP BY status;

-- ---------------------------------------------------------------------
-- 4. MONTHLY REVENUE AND SERVICE VOLUME TRENDS
-- ---------------------------------------------------------------------
SELECT 
    SUBSTR(b.bill_date, 1, 7) AS service_month,
    COUNT(b.bill_id) AS bills_generated,
    ROUND(SUM(b.parts_cost), 2) AS total_parts_revenue,
    ROUND(SUM(b.labor_cost), 2) AS total_labor_revenue,
    ROUND(SUM(b.tax_amount), 2) AS total_tax_collected,
    ROUND(SUM(b.discount_amount), 2) AS total_discounts_granted,
    ROUND(SUM(b.total_amount), 2) AS net_revenue
FROM bills b
GROUP BY SUBSTR(b.bill_date, 1, 7)
ORDER BY service_month ASC;

-- ---------------------------------------------------------------------
-- 5. MOST COMMON SERVICE TYPES & REVENUE BREAKDOWN
-- ---------------------------------------------------------------------
SELECT 
    a.service_type,
    COUNT(a.appointment_id) AS request_frequency,
    ROUND(AVG(sr.spare_parts_cost), 2) AS avg_parts_cost,
    ROUND(AVG(sr.labor_charges), 2) AS avg_labor_cost,
    ROUND(SUM(b.total_amount), 2) AS total_revenue_generated
FROM appointments a
JOIN service_records sr ON a.appointment_id = sr.appointment_id
JOIN bills b ON sr.service_id = b.service_id
GROUP BY a.service_type
ORDER BY request_frequency DESC;

-- ---------------------------------------------------------------------
-- 6. TECHNICIAN WORKLOAD AND EFFICIENCY PERFORMANCE
-- ---------------------------------------------------------------------
SELECT 
    technician_name,
    COUNT(service_id) AS total_jobs_completed,
    ROUND(SUM(spare_parts_cost), 2) AS parts_managed,
    ROUND(SUM(labor_charges), 2) AS total_labor_earned,
    ROUND(AVG(labor_charges), 2) AS avg_labor_per_job
FROM service_records
GROUP BY technician_name
ORDER BY total_jobs_completed DESC;

-- ---------------------------------------------------------------------
-- 7. NESTED SUBQUERY: Vehicles with Above-Average Maintenance Costs
-- ---------------------------------------------------------------------
SELECT 
    v.reg_number,
    v.model,
    v.vehicle_type,
    ROUND(SUM(b.total_amount), 2) AS total_spent
FROM vehicles v
JOIN appointments a ON v.vehicle_id = a.vehicle_id
JOIN service_records sr ON a.appointment_id = sr.appointment_id
JOIN bills b ON sr.service_id = b.service_id
GROUP BY v.vehicle_id, v.reg_number, v.model, v.vehicle_type
HAVING SUM(b.total_amount) > (
    SELECT AVG(total_amount) FROM bills
)
ORDER BY total_spent DESC;

-- ---------------------------------------------------------------------
-- 8. DATABASE VIEWS (as per 25ITT31 Unit 2: Views)
-- ---------------------------------------------------------------------
DROP VIEW IF EXISTS v_service_summary;
CREATE VIEW v_service_summary AS
SELECT 
    c.name AS customer_name,
    c.phone,
    v.reg_number,
    v.model,
    v.vehicle_type,
    a.appointment_date,
    a.service_type,
    sr.technician_name,
    b.total_amount,
    b.payment_status,
    b.payment_method
FROM customers c
JOIN vehicles v ON c.customer_id = v.customer_id
JOIN appointments a ON v.vehicle_id = a.vehicle_id
JOIN service_records sr ON a.appointment_id = sr.appointment_id
JOIN bills b ON sr.service_id = b.service_id;
