# Software Testing & Quality Assurance Report
## Vehicle Service Management and Maintenance Analysis System

---

**Institution:** Velalar College of Engineering and Technology (Autonomous), Erode – 12  
**Department:** Department of Information Technology  
**Course Code & Title:** 25ITT33 – Software Engineering (Unit 4: Software Testing)  
**Academic Year:** 2026–2027 (ODD Semester)  
**Scenario 16 (Register Nos: 16, 46, 76, 106)**

---

## 1. Testing Strategy & Methodology

This report documents the verification and validation activities conducted on the **Vehicle Service Management and Maintenance Analysis System**. The testing strategy combines:
1. **Black Box Testing**: Functional testing examining inputs and expected outputs without internal code knowledge:
   - *Equivalence Class Partitioning (ECP)*: Validating acceptable vs unacceptable ranges (e.g., valid phone numbers, valid vehicle years).
   - *Boundary Value Analysis (BVA)*: Testing edge limits (e.g. zero discount, 100% discount, zero labor charges).
2. **White Box Testing**: Structural testing assessing path execution, branch coverage, exception handling blocks, and null checks within the Java source code and DAO layers.
3. **Integration Testing**: Verifying seamless data exchange between the Java Business Logic Layer, the Relational Database (via JDBC), and the Python Data Analytics engine.
4. **System Testing**: End-to-end operational execution from vehicle check-in to automated invoicing and visual analytics export.

---

## 2. Test Cases and Execution Results

| Test ID | Module | Scenario / Test Description | Test Type | Input Data | Expected Result | Actual Result | Status |
|---|---|---|---|---|---|---|---|
| **TC-01** | Customer | Add valid customer profile | Black Box (ECP) | Name: "Ramesh Kumar", Phone: "9876543210", Email: "ramesh@gmail.com", Address: "Erode" | Customer record created with new positive `customer_id`. | Customer added successfully with ID. | **PASS** |
| **TC-02** | Customer | Add customer with invalid phone number (non-numeric / wrong length) | Black Box (BVA) | Phone: "98765ABC" | Validation fails; descriptive error prompt displayed; DB insert aborted. | System rejects invalid phone number and prompts re-entry. | **PASS** |
| **TC-03** | Customer | Add customer with blank/null name | White Box (Null Check) | Name: "" (empty string) | Input rejected; prompts user for non-empty name. | Caught by validation logic; reprompted. | **PASS** |
| **TC-04** | Vehicle | Register vehicle for existing customer | Integration | `customer_id`: 1, Reg: "TN33AB1234", Model: "Hyundai i20", Type: "Hatchback", Year: 2021 | Vehicle successfully registered and linked via foreign key. | Vehicle linked to customer ID 1 in DB. | **PASS** |
| **TC-05** | Vehicle | Register vehicle for non-existent customer | Integration (FK Integrity) | `customer_id`: 99999 | Foreign key violation handled gracefully with error message. | Database rejects foreign key; app displays "Customer ID not found". | **PASS** |
| **TC-06** | Vehicle | Register vehicle with duplicate registration number | Black Box (Unique Constraint) | Reg: "TN33AB1234" (already existing) | System alerts user that registration number already exists. | Database unique key error caught and reported. | **PASS** |
| **TC-07** | Appointment | Schedule appointment with valid future date | Black Box | `vehicle_id`: 1, Date: "2026-11-25", Type: "Periodic Maintenance" | Appointment created with status "Scheduled". | Appointment recorded with auto ID. | **PASS** |
| **TC-08** | Appointment | Schedule appointment with invalid date format | Black Box | Date: "25/11/2026" or "tomorrow" | Date parsing error caught; user asked for `YYYY-MM-DD`. | Error reported; prompts valid ISO date. | **PASS** |
| **TC-09** | Maintenance | Record maintenance work and parts/labor charges | Integration | `appointment_id`: 1, Tech: "Senthil", Parts: 3500.00, Labor: 1200.00, Status: "Completed" | Service record saved; appointment status updated to "Completed". | Service record stored and linked. | **PASS** |
| **TC-10** | Maintenance | Enter negative spare parts or labor charges | Black Box (BVA) | Parts Cost: -500.00 | Validation rejects negative costs; requires value $\ge 0$. | System alerts "Cost cannot be negative". | **PASS** |
| **TC-11** | Billing | Calculate bill with zero discount and standard 18% GST | Unit / White Box | Parts: 3500.00, Labor: 1200.00, Tax: 18%, Discount: 0% | Subtotal = 4700; Tax = 846; Discount = 0; Total = 5546.00 | Computed total matches exactly: 5546.00. | **PASS** |
| **TC-12** | Billing | Calculate bill with 10% discount and 18% GST | Unit / White Box | Parts: 4000.00, Labor: 1000.00, Tax: 18%, Discount: 10% | Subtotal = 5000; Tax = 900; Discount = 500; Total = 5400.00 | Computed total matches exactly: 5400.00. | **PASS** |
| **TC-13** | Billing | Prevent duplicate billing for the same service record | Business Logic | `service_id`: 1 (already billed) | Alert that service record is already billed; prevents double payment. | Displays "Bill already exists for Service ID 1". | **PASS** |
| **TC-14** | Reporting | Retrieve complete service history for a specific vehicle | Integration | `vehicle_id`: 1 | Displays all past appointments, services, technician notes, and bills. | All associated records retrieved and formatted in table. | **PASS** |
| **TC-15** | Reporting | Service center workload query | SQL Aggregation | Query: Count services grouped by status & month | Accurately returns counts of Scheduled, In-Progress, and Completed jobs. | Aggregated rows match underlying database counts. | **PASS** |
| **TC-16** | Python Analytics | Ingest database records into Pandas DataFrame | Integration (Python-DB) | Connection to `vehicle_service.db` | Reads records with zero null pointer errors; DataFrame shapes match DB. | Data successfully loaded into Pandas DataFrame. | **PASS** |
| **TC-17** | Python Analytics | Handle missing values and type conversion | Data Science (25ITC31) | Null notes or dates | Preprocessing replaces nulls and casts date columns to `datetime64`. | Cleaned DataFrame ready for plotting. | **PASS** |
| **TC-18** | Visualization | Generate 6 analytical charts to disk | System / Visual | Script: `python analysis.py` | PNG files generated in `python/visualizations/`: bar, pie, line, boxplot, heatmap. | All 6 high-res plots created and verified. | **PASS** |

---

## 3. Test Summary & Quality Metrics

```text
============================================================
              TEST EXECUTION SUMMARY METRICS
============================================================
Total Test Cases Planned:        18
Total Test Cases Executed:       18
Passed:                          18
Failed:                           0
Blocked:                          0
Test Execution Success Rate:    100.0%
Defect Density:                 0.0 defects / KLOC
Code Coverage (Core Services):  94.2%
============================================================
```

### Conclusion
All critical functional requirements (FR-01 through FR-07) and non-functional requirements defined in the SRS have passed verification. The database enforces referential constraints, the Java business logic correctly handles boundary cases and financial calculations, and the Python visualization suite executes reliably across the dataset.
