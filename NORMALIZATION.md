# Database Normalization Report
## Vehicle Service Management and Maintenance Analysis System

---

**Institution:** Velalar College of Engineering and Technology (Autonomous), Erode – 12  
**Department:** Department of Information Technology  
**Course Code & Title:** 25ITT31 – Database Management Systems (Unit 3: Normalization and Query Optimization)  
**Academic Year:** 2026–2027 (ODD Semester)  
**Scenario 16 (Register Nos: 16, 46, 76, 106)**

---

## 1. Overview of Normalization

Normalization is the systematic approach of decomposing tables to eliminate data redundancy and avoid undesirable anomalies (**Insertion Anomalies**, **Update Anomalies**, and **Deletion Anomalies**). 

This report provides the mathematical functional dependency analysis and formal step-by-step decomposition from an unnormalized state (UNF) through First Normal Form (1NF), Second Normal Form (2NF), and Third Normal Form (3NF) / Boyce-Codd Normal Form (BCNF).

---

## 2. Unnormalized Form (UNF)

If all vehicle service center data were captured in a single flat file or monolithic unnormalized table `UNF_ServiceCenter`:

```text
UNF_ServiceCenter (
    CustomerName, CustomerPhone, CustomerEmail, CustomerAddress,
    VehicleRegNo, VehicleModel, VehicleType, ManufactureYear,
    AppointmentDate, ServiceType, AppointmentStatus,
    TechnicianName, WorkPerformed, SparePartsCost, LaborCharges, ServiceStatus,
    BillDate, TaxAmount, DiscountAmount, TotalAmount, PaymentStatus, PaymentMethod
)
```

### Anomalies in UNF:
1. **Insertion Anomaly**: We cannot record a newly purchased vehicle without scheduling an appointment or creating a bill.
2. **Update Anomaly**: If a customer changes their phone number or address, multiple historical service rows must be updated; any oversight creates inconsistent records.
3. **Deletion Anomaly**: If historical service bills are purged, customer ownership and vehicle registration details are permanently lost.

---

## 3. First Normal Form (1NF)

### 3.1 Definition
A relation is in **First Normal Form (1NF)** if and only if:
1. All attributes contain only atomic (indivisible) values.
2. There are no repeating groups or multivalued attributes.
3. Each record is uniquely identifiable by a designated Primary Key.

### 3.2 Transformation to 1NF
- Multivalued lists (e.g. multiple services or parts) are split into individual records or unified cost/record aggregates.
- Artificial primary keys (`customer_id`, `vehicle_id`, `appointment_id`, `service_id`, `bill_id`) are introduced.

```text
1NF_FlatServiceCenter (
    customer_id [PK], name, phone, email, address,
    vehicle_id [PK], reg_number, model, vehicle_type, manufacture_year,
    appointment_id [PK], appointment_date, service_type, appt_status,
    service_id [PK], technician_name, work_performed, spare_parts_cost, labor_charges, service_status,
    bill_id [PK], bill_date, tax_amount, discount_amount, total_amount, payment_status, payment_method
)
```

Composite Key: `{customer_id, vehicle_id, appointment_id, service_id, bill_id}`.

---

## 4. Second Normal Form (2NF)

### 4.1 Definition
A relation is in **Second Normal Form (2NF)** if and only if:
1. It is in First Normal Form (1NF).
2. It has **no partial functional dependencies** (no non-prime attribute is functionally dependent on a proper subset of any candidate key).

### 4.2 Analysis of Partial Dependencies in 1NF:
- `customer_id` $\rightarrow$ `{name, phone, email, address}`
- `vehicle_id` $\rightarrow$ `{reg_number, model, vehicle_type, manufacture_year, customer_id}`
- `appointment_id` $\rightarrow$ `{appointment_date, service_type, appt_status, vehicle_id}`
- `service_id` $\rightarrow$ `{technician_name, work_performed, spare_parts_cost, labor_charges, service_status, appointment_id}`
- `bill_id` $\rightarrow$ `{bill_date, tax_amount, discount_amount, total_amount, payment_status, payment_method, service_id}`

Since non-prime attributes depend only on individual sub-keys and not on the whole composite key, we decompose into distinct entities.

### 4.3 2NF Relations:
1. **`Customers`** (`customer_id`, `name`, `phone`, `email`, `address`)
2. **`Vehicles`** (`vehicle_id`, `customer_id`, `reg_number`, `model`, `vehicle_type`, `manufacture_year`)
3. **`Appointments`** (`appointment_id`, `vehicle_id`, `appointment_date`, `service_type`, `status`)
4. **`ServiceRecords`** (`service_id`, `appointment_id`, `technician_name`, `work_performed`, `spare_parts_cost`, `labor_charges`, `service_status`)
5. **`Bills`** (`bill_id`, `service_id`, `bill_date`, `parts_cost`, `labor_cost`, `tax_amount`, `discount_amount`, `total_amount`, `payment_status`, `payment_method`)

---

## 5. Third Normal Form (3NF)

### 5.1 Definition
A relation is in **Third Normal Form (3NF)** if and only if:
1. It is in Second Normal Form (2NF).
2. It contains **no transitive dependencies** (no non-prime attribute depends on another non-prime attribute via $X \rightarrow Y$ and $Y \rightarrow Z$).

### 5.2 Transitive Dependency Examination:
In the `Bills` relation:
- Primary Key: `bill_id`
- Functional Dependency: `bill_id` $\rightarrow$ `{parts_cost, labor_cost}`
- Derived Attribute: $\text{Subtotal} = \text{parts\_cost} + \text{labor\_cost}$
- Calculation: $\text{total\_amount} = (\text{parts\_cost} + \text{labor\_cost} + \text{tax\_amount}) - \text{discount\_amount}$

To maintain strict 3NF purity, derived totals can either be computed dynamically or retained as frozen audit snapshots for fiscal accounting. In our design, all non-key attributes are directly determined by the candidate key alone:

- `Customers`: `customer_id` $\rightarrow$ `{name, phone, email, address}`
- `Vehicles`: `vehicle_id` $\rightarrow$ `{reg_number, model, vehicle_type, manufacture_year, customer_id}` (where `reg_number` is an alternate unique candidate key).
- `Appointments`: `appointment_id` $\rightarrow$ `{vehicle_id, appointment_date, service_type, status, notes}`
- `ServiceRecords`: `service_id` $\rightarrow$ `{appointment_id, technician_name, work_performed, spare_parts_cost, labor_charges, service_status, completion_date}`
- `Bills`: `bill_id` $\rightarrow$ `{service_id, bill_date, parts_cost, labor_cost, tax_amount, discount_amount, total_amount, payment_status, payment_method}`

**Conclusion:** All relations satisfy **3NF and Boyce-Codd Normal Form (BCNF)** because for every non-trivial functional dependency $X \rightarrow Y$, the determinant $X$ is a super key.

---

## 6. Relational Schema Summary & Integrity Rules

```text
+---------------------------------------------------------------------------------+
|                                 RELATIONAL MODEL                                |
+---------------------------------------------------------------------------------+
| CUSTOMERS (customer_id [PK], name, phone, email, address, created_at)          |
|                                                                                 |
| VEHICLES (vehicle_id [PK], customer_id [FK], reg_number [UK], model,           |
|           vehicle_type, manufacture_year)                                       |
|           FK: customer_id REFERENCES CUSTOMERS(customer_id) ON DELETE CASCADE   |
|                                                                                 |
| APPOINTMENTS (appointment_id [PK], vehicle_id [FK], appointment_date,           |
|               service_type, status, notes)                                      |
|               FK: vehicle_id REFERENCES VEHICLES(vehicle_id) ON DELETE CASCADE  |
|                                                                                 |
| SERVICE_RECORDS (service_id [PK], appointment_id [FK], technician_name,        |
|                  work_performed, spare_parts_cost, labor_charges,               |
|                  service_status, completion_date)                               |
|                  FK: appointment_id REFERENCES APPOINTMENTS(appointment_id)    |
|                                                                                 |
| BILLS (bill_id [PK], service_id [FK], bill_date, parts_cost, labor_cost,        |
|        tax_amount, discount_amount, total_amount, payment_status,               |
|        payment_method)                                                          |
|        FK: service_id REFERENCES SERVICE_RECORDS(service_id)                    |
+---------------------------------------------------------------------------------+
```

### Referential Integrity & Constraints:
- **Entity Integrity**: All primary keys (`customer_id`, `vehicle_id`, etc.) are `NOT NULL` and unique.
- **Referential Integrity**: Foreign keys guarantee that a vehicle cannot reference a non-existent customer, and service records strictly associate with booked appointments.
- **Domain Integrity**: 
  - `status` IN ('Scheduled', 'In-Progress', 'Completed', 'Cancelled')
  - `spare_parts_cost` $\ge 0.00$
  - `labor_charges` $\ge 0.00$
  - `total_amount` $\ge 0.00$
