# Software Requirements Specification (SRS)
## Vehicle Service Management and Maintenance Analysis System

---

**Institution:** Velalar College of Engineering and Technology (Autonomous), Erode – 638 012  
**Department:** Department of Information Technology  
**Academic Year:** 2026–2027 (ODD Semester)  
**Class:** 25IT3A & 25IT3B  
**Course Code & Title:** 25ITT33 – Software Engineering  
**Activity:** Project-Based Assessment (Scenario 16)  
**Assigned Register Nos:** 16, 46, 76, 106  

---

## 1. Introduction

### 1.1 Purpose
This document provides a comprehensive Software Requirements Specification (SRS) for the **Vehicle Service Management and Maintenance Analysis System**. The primary purpose of this system is to streamline automobile service station operations by replacing manual, error-prone logbooks with a centralized, database-backed platform. The system handles customer records, vehicle management, appointment scheduling, service tracking, automated invoice generation, and analytical data visualization.

### 1.2 Scope
The software system encompasses:
- **Core Operations**: Managing customer profiles, vehicle registration details, appointments, workshop maintenance jobs, spare parts inventory usage, and billing.
- **Reporting & Business Logic**: Automatic bill calculation combining labor charges and spare parts costs with statutory taxes/discounts, workload monitoring, and service history tracking.
- **Analytics & Predictive Insights**: Integration with a Python-based Data Science pipeline (using Pandas, NumPy, Matplotlib, and Seaborn) to uncover patterns in vehicle breakdowns, popular service categories, monthly revenue trends, and service center bay utilization.

### 1.3 Definitions, Acronyms, and Abbreviations
- **SRS**: Software Requirements Specification
- **DBMS**: Database Management System
- **RDBMS**: Relational Database Management System
- **JDBC**: Java Database Connectivity
- **DAO**: Data Access Object Pattern
- **CRUD**: Create, Read, Update, Delete
- **EDA**: Exploratory Data Analysis
- **VIN / RegNo**: Vehicle Identification / Registration Number

### 1.4 References
- IEEE Std 830-1998: Recommended Practice for Software Requirements Specifications.
- VCET Regulation 2025 IT Curriculum & Syllabi:
  - 25ITT31: Database Management Systems
  - 25ITT32: Java Programming
  - 25ITT33: Software Engineering
  - 25ITC31: Foundations of Data Science

---

## 2. Overall Description

### 2.1 Product Perspective
The Vehicle Service Management and Maintenance Analysis System is a self-contained multi-tier enterprise solution consisting of:
1. **Presentation / Business Logic Layer (Java)**: A modular, object-oriented application providing user interaction, input sanitization, workflow coordination, and transactional integrity.
2. **Persistence Layer (Relational Database - MySQL / SQLite)**: Normalized database tables storing customer, vehicle, appointment, maintenance, and billing entities with foreign-key constraints.
3. **Analytical & Visualization Layer (Python)**: An Exploratory Data Analysis engine reading transactional records to discover maintenance trends, cost distributions, and operational workloads.

```
+-------------------------------------------------------------+
|                     User Interaction Layer                  |
|                 (Java Console / Interactive CLI)            |
+------------------------------+------------------------------+
                               | JDBC API
+------------------------------v------------------------------+
|             Business Logic & Data Access (Java DAO)         |
|  - CustomerDAO   - VehicleDAO   - ServiceDAO   - BillService|
+------------------------------+------------------------------+
                               | SQL / Queries
+------------------------------v------------------------------+
|           Relational Database (MySQL / SQLite Engine)        |
|  Customers | Vehicles | Appointments | ServiceRecords | Bills|
+------------------------------+------------------------------+
                               | SQL Connector / sqlite3 / pandas
+------------------------------v------------------------------+
|            Python Data Analytics & Visualization Layer       |
|            (Pandas, NumPy, Matplotlib, Seaborn)             |
+-------------------------------------------------------------+
```

### 2.2 Product Functions
1. **Customer Record Management**: Create, update, search, and view customer profiles.
2. **Vehicle Profile Tracking**: Associate one or more vehicles to a customer, recording vehicle model, type, registration plate, and manufacturing year.
3. **Appointment Scheduling**: Book service appointments with requested service categories (e.g., General Service, Oil Change, Brake Overhaul, Engine Diagnostics) while preventing double bookings.
4. **Maintenance & Service Logging**: Record technician findings, work performed, spare parts utilized, spare part unit costs, labor charges, and service statuses (Pending, In-Progress, Completed, Cancelled).
5. **Billing & Invoicing**: Calculate net amounts, tax calculations (e.g., 18% GST), apply discounts, and generate timestamped customer invoices.
6. **Executive Reporting**: Produce aggregated summaries of maintenance costs per vehicle, pending services, and workshop workload.
7. **Visual Analytics**: Generate visual charts highlighting vehicle breakdown patterns, revenue trends, and spare parts expenditures.

### 2.3 User Characteristics
- **Service Center Receptionist / Front Desk**: Manages customer intake, schedules appointments, and looks up vehicle history.
- **Service Advisor / Workshop Manager**: Assigns technicians, updates maintenance records, enters spare parts and labor hours, and finalizes service tickets.
- **Billing Officer**: Generates bills, processes payments, and produces financial summaries.
- **Data Analyst / Operations Manager**: Executes Python analytics scripts to forecast demand and review workload statistics.

### 2.4 Operating Environment
- **Operating System**: Cross-platform (Windows 10/11, Linux, macOS).
- **Java Runtime**: OpenJDK / Oracle JDK 11 or higher (compatible with Java 17/21).
- **Database Engine**: MySQL Server 8.0+ / SQLite 3.x.
- **Python Environment**: Python 3.9+ with `pandas`, `numpy`, `matplotlib`, and `seaborn`.

### 2.5 Design and Implementation Constraints
- Compliance with relational normalization principles up to 3rd Normal Form (3NF).
- Clear adherence to object-oriented programming paradigms in Java (encapsulation, abstraction, inheritance, polymorphism).
- Secure query execution using parameterized SQL (`PreparedStatement`) to prevent SQL injection vulnerabilities.
- Safe handling of database connections using try-with-resources.

---

## 3. Specific Requirements

### 3.1 External Interface Requirements

#### 3.1.1 User Interface
- A structured, numbered interactive console interface with input sanitization, error messages for invalid formats, and formatted tabular outputs for query results.

#### 3.1.2 Software Interface
- **JDBC Driver**: `com.mysql.cj.jdbc.Driver` (for MySQL) / `org.sqlite.JDBC` (for SQLite).
- **Python DB-API**: `sqlite3` and `mysql-connector-python` or SQLAlchemy engines.

### 3.2 Functional Requirements

| Requirement ID | Module | Description | Input | Expected Output |
|---|---|---|---|---|
| **FR-01** | Customer Management | Register and manage customer details. | Name, phone number, email, address | New customer record created with auto-generated `customer_id`. |
| **FR-02** | Vehicle Management | Register vehicle and bind to a valid customer. | Reg No, Make/Model, Vehicle Type (Hatchback/Sedan/SUV/Bike), Year | Vehicle registered and linked to `customer_id`. |
| **FR-03** | Service Scheduling | Schedule service appointments for specific dates. | `vehicle_id`, appointment date, service type, notes | Appointment scheduled with status `Scheduled`. |
| **FR-04** | Maintenance Tracking | Record workshop maintenance activities, labor, and spare parts. | `appointment_id`, technician name, work description, parts cost, labor cost | Service record saved with status `Completed`. |
| **FR-05** | Billing & Invoicing | Compute itemized bills including labor, parts, tax, and total. | `service_id`, discount percentage, payment mode | Final bill generated, status updated to `Paid`. |
| **FR-06** | Operational Reporting | View service center performance, workload, and vehicle logs. | Vehicle ID or date range filter | Formatted tabular report displayed to terminal. |
| **FR-07** | Data Visualization | Fetch database records, clean data, and produce statistical plots. | SQL query over service and billing tables | High-resolution charts (bar, pie, line, box plot, heatmap) saved to disk. |

### 3.3 Non-Functional Requirements

#### 3.3.1 Performance
- All standard database CRUD queries execute in under 100 milliseconds for operational workloads.
- The Python data pipeline handles 10,000+ historical records in under 3 seconds.

#### 3.3.2 Reliability & Fault Tolerance
- Database operations enforce ACID properties.
- Invalid user inputs (e.g., alphabetical characters in phone number or negative costs) are rejected gracefully without system crashes.

#### 3.3.3 Security & Integrity
- Foreign key constraints ensure child records (e.g. Service Records) cannot exist without valid parent records (Vehicles/Appointments).
- Database credentials and configuration parameters are centralized in configuration properties.

#### 3.3.4 Maintainability
- Code organized into clean packages (`model`, `dao`, `service`, `main`).
- Comprehensive documentation provided for database schemas, normal forms, and test suites.

---

## 4. Verification and Validation Matrix

| Requirement | Test Technique | Success Criteria |
|---|---|---|
| Customer Registration | Positive & Negative Input Testing | Record appears in DB; duplicates/null inputs rejected. |
| Vehicle Linkage | Foreign Key Referential Testing | Vehicle links to valid customer; fails on non-existent customer ID. |
| Bill Computation | Boundary & Mathematical Unit Testing | Total = (Parts + Labor) * (1 + Tax) - Discount matches exact penny. |
| Python Visualization | Pipeline Smoke Testing | Script connects to DB, extracts dataset, and writes 6 chart files without errors. |
