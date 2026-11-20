# System Architecture & Design Specification
## Vehicle Service Management and Maintenance Analysis System

---

**Institution:** Velalar College of Engineering and Technology, Erode – 12  
**Department:** Department of Information Technology  
**Course Codes:** 25ITT31 (DBMS), 25ITT32 (Java), 25ITT33 (Software Engineering), 25ITC31 (Data Science)  
**Academic Year:** 2026–2027 (ODD Semester)  
**Class:** 25IT3A & 25IT3B  
**Scenario 16 (Register Nos: 16, 46, 76, 106)**

---

## 1. System Architecture Overview

The **Vehicle Service Management and Maintenance Analysis System** is engineered using an enterprise three-tier architectural pattern. This separates user interaction, business processing logic, and persistent storage, while integrating an analytical engine for business intelligence.

```mermaid
flowchart TD
    subgraph UI_Layer["1. Presentation Layer (Java CLI / User Interface)"]
        CLI["Interactive Menu Interface"]
        InputHandler["Input Validation & Formatting"]
    end

    subgraph Business_Layer["2. Business Logic & Service Layer (Java OOP)"]
        AppController["VehicleServiceApp (Controller)"]
        BillingService["BillingService (Cost, Tax, Discount Logic)"]
        ReportService["ReportService (Workload & History Summaries)"]
        DAO_Factory["DAO Layer (Customer, Vehicle, Appointment, Service, Bill)"]
    end

    subgraph Persistence_Layer["3. Persistence & Database Layer (DBMS)"]
        JDBC["JDBC Connection Manager"]
        DB[("Relational Database (MySQL / SQLite)\n- Customers\n- Vehicles\n- Appointments\n- ServiceRecords\n- Bills")]
    end

    subgraph Analytics_Layer["4. Data Science & Visual Analytics Layer (Python)"]
        PyConnector["Data Ingestion (sqlite3 / mysql-connector)"]
        PandasEngine["Data Wrangling & Preprocessing (Pandas / NumPy)"]
        VisualizationEngine["Visualization Engine (Matplotlib & Seaborn)"]
        Charts["Generated Insights (PNG Charts & Jupyter Notebook)"]
    end

    UI_Layer --> Business_Layer
    Business_Layer --> Persistence_Layer
    Persistence_Layer -.->|Read Historical Data| Analytics_Layer
    PyConnector --> PandasEngine --> VisualizationEngine --> Charts
```

---

## 2. Use Case Diagram

The Use Case Diagram defines the interactions between the system's primary actors (**Customer / Receptionist**, **Service Advisor / Technician**, **Billing Officer**, and **System Administrator / Analyst**) and the core functionalities.

```mermaid
flowchart LR
    Customer["Customer / Receptionist"]
    Advisor["Service Advisor / Technician"]
    Billing["Billing Officer"]
    Analyst["Operations Manager / Analyst"]

    subgraph System["Vehicle Service Management System"]
        UC1(["UC-01: Manage Customer Details"])
        UC2(["UC-02: Register & Manage Vehicles"])
        UC3(["UC-03: Schedule Service Appointments"])
        UC4(["UC-04: Log Maintenance Work & Parts"])
        UC5(["UC-05: Calculate & Generate Service Bill"])
        UC6(["UC-06: View Vehicle Service History"])
        UC7(["UC-07: Track Workshop Workload"])
        UC8(["UC-08: Generate Python Visualizations & Trends"])
    end

    Customer --> UC1
    Customer --> UC2
    Customer --> UC3
    
    Advisor --> UC4
    Advisor --> UC6
    Advisor --> UC7

    Billing --> UC5
    Billing --> UC6

    Analyst --> UC7
    Analyst --> UC8

    UC4 -.->|«include»| UC3
    UC5 -.->|«include»| UC4
```

---

## 3. Class Diagram

The Class Diagram highlights the Object-Oriented structure implemented in Java, conforming to the **25ITT32 (Java Programming)** course specifications. It showcases encapsulation, relationships, data access objects (DAOs), and service interfaces.

```mermaid
classDiagram
    class Customer {
        -int customerId
        -String name
        -String phone
        -String email
        -String address
        +getCustomerId() int
        +getName() String
        +getPhone() String
        +getEmail() String
        +getAddress() String
        +toString() String
    }

    class Vehicle {
        -int vehicleId
        -int customerId
        -String regNumber
        -String model
        -String vehicleType
        -int manufactureYear
        +getVehicleId() int
        +getCustomerId() int
        +getRegNumber() String
        +getModel() String
        +getVehicleType() String
        +getManufactureYear() int
    }

    class Appointment {
        -int appointmentId
        -int vehicleId
        -String appointmentDate
        -String serviceType
        -String status
        +getAppointmentId() int
        +getVehicleId() int
        +getAppointmentDate() String
        +getServiceType() String
        +getStatus() String
        +setStatus(String status) void
    }

    class ServiceRecord {
        -int serviceId
        -int appointmentId
        -String technicianName
        -String workPerformed
        -double sparePartsCost
        -double laborCharges
        -String serviceStatus
        -String completionDate
        +getServiceId() int
        +getAppointmentId() int
        +getTechnicianName() String
        +getWorkPerformed() String
        +getSparePartsCost() double
        +getLaborCharges() double
        +getServiceStatus() String
        +calculateSubtotal() double
    }

    class Bill {
        -int billId
        -int serviceId
        -String billDate
        -double partsCost
        -double laborCost
        -double taxRate
        -double taxAmount
        -double discountAmount
        -double totalAmount
        -String paymentStatus
        -String paymentMethod
        +getBillId() int
        +getTotalAmount() double
        +getPaymentStatus() String
        +generateInvoiceReceipt() String
    }

    class BillingService {
        +calculateTax(double subtotal, double taxRate) double
        +calculateDiscount(double subtotal, double discountPct) double
        +computeFinalBill(ServiceRecord record, double taxRate, double discountPct, String paymentMethod) Bill
    }

    class ReportService {
        +printServiceHistory(int vehicleId) void
        +printServiceCenterWorkload() void
        +printCostSummaryByVehicle() void
    }

    Customer "1" *-- "0..*" Vehicle : owns
    Vehicle "1" *-- "0..*" Appointment : has
    Appointment "1" -- "1" ServiceRecord : leads to
    ServiceRecord "1" -- "1" Bill : generates
    BillingService ..> ServiceRecord : uses
    BillingService ..> Bill : creates
    ReportService ..> ServiceRecord : aggregates
```

---

## 4. Entity-Relationship (ER) Diagram

The Entity-Relationship (ER) diagram models the conceptual database schema designed in accordance with **25ITT31 (Database Management Systems)**.

```mermaid
erDiagram
    CUSTOMERS ||--o{ VEHICLES : "owns (1:N)"
    VEHICLES ||--o{ APPOINTMENTS : "scheduled for (1:N)"
    APPOINTMENTS ||--|| SERVICE_RECORDS : "results in (1:1)"
    SERVICE_RECORDS ||--|| BILLS : "billed via (1:1)"

    CUSTOMERS {
        int customer_id PK
        string name
        string phone
        string email
        string address
        timestamp created_at
    }

    VEHICLES {
        int vehicle_id PK
        int customer_id FK
        string reg_number UK
        string model
        string vehicle_type
        int manufacture_year
    }

    APPOINTMENTS {
        int appointment_id PK
        int vehicle_id FK
        date appointment_date
        string service_type
        string status
        text notes
    }

    SERVICE_RECORDS {
        int service_id PK
        int appointment_id FK
        string technician_name
        text work_performed
        decimal spare_parts_cost
        decimal labor_charges
        string service_status
        date completion_date
    }

    BILLS {
        int bill_id PK
        int service_id FK
        date bill_date
        decimal parts_cost
        decimal labor_cost
        decimal tax_amount
        decimal discount_amount
        decimal total_amount
        string payment_status
        string payment_method
    }
```

---

## 5. Relational Schema & Integrity Constraints

The conceptual model is mapped into the following relational model with primary key (PK), foreign key (FK), and unique key (UK) constraints:

1. **`customers`** (`customer_id` [PK], `name`, `phone`, `email`, `address`, `created_at`)
2. **`vehicles`** (`vehicle_id` [PK], `customer_id` [FK -> `customers.customer_id` ON DELETE CASCADE], `reg_number` [UK], `model`, `vehicle_type`, `manufacture_year`)
3. **`appointments`** (`appointment_id` [PK], `vehicle_id` [FK -> `vehicles.vehicle_id` ON DELETE CASCADE], `appointment_date`, `service_type`, `status`, `notes`)
4. **`service_records`** (`service_id` [PK], `appointment_id` [FK -> `appointments.appointment_id` ON DELETE CASCADE], `technician_name`, `work_performed`, `spare_parts_cost`, `labor_charges`, `service_status`, `completion_date`)
5. **`bills`** (`bill_id` [PK], `service_id` [FK -> `service_records.service_id` ON DELETE CASCADE], `bill_date`, `parts_cost`, `labor_cost`, `tax_amount`, `discount_amount`, `total_amount`, `payment_status`, `payment_method`)
