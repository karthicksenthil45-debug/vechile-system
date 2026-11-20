# Sample Inputs and Outputs Execution Log
## Vehicle Service Management and Maintenance Analysis System

---

**Institution:** Velalar College of Engineering and Technology, Erode – 12  
**Department:** Department of Information Technology  
**Scenario 16:** Vehicle Service Management and Maintenance Analysis System  
**Academic Year:** 2026–2027 (ODD Semester)  
**Assigned Register Nos:** 16, 46, 76, 106  

---

## 1. Application Startup & Main Menu

```text
===========================================================================
   VEHICLE SERVICE MANAGEMENT & MAINTENANCE ANALYSIS SYSTEM
       Velalar College of Engineering and Technology (VCET)
                  Department of IT - Scenario 16
===========================================================================

--------------------------------------------------
                   MAIN MENU
--------------------------------------------------
1. Customer Details Management
2. Vehicle Details Management
3. Schedule Service Appointments
4. Record Maintenance Activities & Spare Parts
5. Billing & Invoice Generation
6. Reports (Service History, Costs & Workload)
7. Run Python Data Analysis & Visualizations
0. Exit Application
--------------------------------------------------
Select an option (0-7): 
```

---

## 2. Customer Registration (FR-01)

### Sample Input:
```text
Select an option (0-7): 1
--- [Customer Management] ---
1. Register New Customer
2. View All Customers
3. Search Customer by ID
Choose action (1-3): 1
Enter Customer Name: Rajesh Kannan
Enter Phone Number: 9842188990
Enter Email Address: rajesh.k@gmail.com
Enter Address: 15 Cauvery Road, Erode
```

### System Output:
```text
Success! New Customer created with Customer ID: 13
```

---

## 3. Vehicle Registration (FR-02)

### Sample Input:
```text
Select an option (0-7): 2
--- [Vehicle Management] ---
1. Register New Vehicle
2. View All Vehicles
3. Search Vehicle by Registration Number
Choose action (1-3): 1
Enter Customer ID: 13
Enter Registration Number (e.g. TN33AB1234): TN33DE7890
Enter Vehicle Model (e.g. Hyundai i20 / Tata Nexon): Tata Harrier
Enter Vehicle Type (Hatchback/Sedan/SUV/Two-Wheeler): SUV
Enter Manufacture Year (e.g. 2022): 2023
```

### System Output:
```text
Success! Vehicle registered with Vehicle ID: 16
```

---

## 4. Scheduling Service Appointment (FR-03)

### Sample Input:
```text
Select an option (0-7): 3
--- [Service Appointment Scheduling] ---
1. Schedule New Appointment
2. View All Appointments
3. Update Appointment Status
Choose action (1-3): 1
Enter Vehicle ID: 16
Enter Appointment Date (YYYY-MM-DD): 2026-11-28
Enter Service Type (General Service / Oil Change / Brake Overhaul / AC Service): General Service
Enter Notes/Complaints: First annual service checkup, wheel alignment check
```

### System Output:
```text
Success! Appointment scheduled with ID: 26
```

---

## 5. Maintenance Service Logging (FR-04)

### Sample Input:
```text
Select an option (0-7): 4
--- [Record Maintenance & Spare Parts] ---
1. Record Maintenance Activity for Appointment
2. View All Service Records
Choose action (1-2): 1
Enter Appointment ID: 26
Enter Technician Name: Saravanan M
Enter Work Performed Description: Engine oil change, oil filter replace, 4-wheel alignment & balancing
Enter Spare Parts Cost (Rs): 3200.00
Enter Labor Charges (Rs): 1400.00
```

### System Output:
```text
Success! Maintenance record saved with Service ID: 23
Subtotal: Rs. 4600.00
```

---

## 6. Automated Invoice Generation (FR-05)

### Sample Input:
```text
Select an option (0-7): 5
--- [Billing & Invoicing] ---
1. Calculate and Generate Bill for Service
2. View All Generated Bills
3. Print Invoice Receipt by Bill ID
Choose action (1-3): 1
Enter Service Record ID: 23
Enter Discount Percentage (e.g. 5 for 5%, 0 for none): 5
Enter Payment Mode (UPI / Cash / Credit Card / Debit Card): UPI
```

### System Output:
```text
Bill Generated Successfully!
=========================================================
          VEHICLE SERVICE CENTER - OFFICIAL INVOICE      
=========================================================
Invoice No   : INV-00023          Date: 2026-11-18
Service Ref  : SRV-00023          Status: Paid
---------------------------------------------------------
Spare Parts Charges           : Rs.    3200.00
Technician Labor Charges      : Rs.    1400.00
Subtotal                      : Rs.    4600.00
Applicable GST (18%)          : Rs.     828.00
Discount Applied              : Rs. -   230.00
---------------------------------------------------------
NET PAYABLE AMOUNT            : Rs.    5198.00
Payment Mode                  : UPI
=========================================================
      Thank you for choosing our Service Center!         
=========================================================
```

---

## 7. Reports: Vehicle Service History (FR-06)

### Sample Input:
```text
Select an option (0-7): 6
--- [Service Center Reports] ---
1. Vehicle Service History (by Reg Number)
2. Vehicle-Wise Cumulative Maintenance Costs
3. Workshop Workload & Demand Analysis
Choose report (1-3): 1
Enter Vehicle Registration Number (e.g. TN33AB1001): TN33AB1001
```

### System Output:
```text
===============================================================================================
                   SERVICE HISTORY REPORT FOR VEHICLE: TN33AB1001
===============================================================================================
Date: 2026-08-19 | Service: Brake Overhaul         | Tech: Saravanan M    
  Work Performed: Rear drum brake shoe replacement, handbrake cable tension adjustment
  Parts: Rs. 1600.00  | Labor: Rs. 750.00   | Total Billed: Rs. 2673.00  | Payment: Paid
-----------------------------------------------------------------------------------------------
Date: 2026-01-10 | Service: General Service        | Tech: Saravanan M    
  Work Performed: Engine oil, oil filter replacement, 40-point vehicle checkup
  Parts: Rs. 1850.00  | Labor: Rs. 800.00   | Total Billed: Rs. 3027.00  | Payment: Paid
-----------------------------------------------------------------------------------------------
===============================================================================================
```

---

## 8. Reports: Cumulative Maintenance Costs & Workload (FR-06)

```text
=========================================================================================================
                   VEHICLE-WISE CUMULATIVE MAINTENANCE COSTS SUMMARY
=========================================================================================================
Reg Number   | Model                | Type         | Visits | Parts (Rs)   | Labor (Rs)   | Total Cost(Rs) | Avg Cost  
---------------------------------------------------------------------------------------------------------
TN33MN7007   | Toyota Innova Crysta | SUV          | 2      |   16100.00 |    5700.00 |       24624.00 |   12312.00
TN33IJ5005   | Maruti Suzuki Swift  | Hatchback    | 2      |    6600.00 |    2700.00 |       10624.00 |    5312.00
TN33GH4004   | Tata Nexon           | SUV          | 2      |    5700.00 |    2900.00 |        9748.00 |    4874.00
TN33OP8008   | Kia Seltos           | SUV          | 2      |    6500.00 |    1650.00 |        9317.00 |    4658.50
TN33EF3003   | Honda City           | Sedan        | 2      |    5600.00 |    2150.00 |        8845.00 |    4422.50
TN33QR9009   | Mahindra XUV700      | SUV          | 2      |    5300.00 |    2250.00 |        8609.00 |    4304.50
TN33AB1001   | Hyundai i20          | Hatchback    | 2      |    3450.00 |    1550.00 |        5700.00 |    2850.00
TN33ST0110   | Hyundai Creta        | SUV          | 1      |    2900.00 |    1050.00 |        4511.00 |    4511.00
TN33BC5665   | Tata Punch           | SUV          | 1      |    2600.00 |    1150.00 |        4275.00 |    4275.00
TN33YZ3443   | Skoda Slavia         | Sedan        | 1      |    2200.00 |     850.00 |        3499.00 |    3499.00
TN33WX2332   | Maruti Suzuki Baleno | Hatchback    | 1      |    2100.00 |     900.00 |        3440.00 |    3440.00
TN33CD2002   | Royal Enfield Classic 350 | Two-Wheeler  | 1      |     850.00 |     300.00 |        1307.00 |    1307.00
TN33UV1221   | Yamaha MT-15         | Two-Wheeler  | 1      |     750.00 |     250.00 |        1150.00 |    1150.00
TN33AA4554   | Honda Activa 6G      | Two-Wheeler  | 1      |     600.00 |     400.00 |        1130.00 |    1130.00
TN33KL6006   | TVS Jupiter          | Two-Wheeler  | 1      |     450.00 |     350.00 |         904.00 |     904.00
=========================================================================================================
```
