# Vehicle Service Management and Maintenance Analysis System

[![Academic Assessment](https://img.shields.io/badge/Assessment-Project--Based%20Assessment-blue.svg)](#)
[![Java](https://img.shields.io/badge/Java-11%20%7C%2017%20%7C%2021-orange.svg)](#)
[![Python](https://img.shields.io/badge/Python-3.9%2B-brightgreen.svg)](#)
[![Database](https://img.shields.io/badge/DBMS-MySQL%20%7C%20SQLite-blueviolet.svg)](#)
[![Status](https://img.shields.io/badge/Status-Completed%20%26%20Verified-success.svg)](#)

---

## 📋 Academic Project Profile

- **Institution:** Velalar College of Engineering and Technology (Autonomous), Erode – 638 012
- **Department:** Department of Information Technology
- **Academic Year:** 2026–2027 (ODD Semester)
- **Class:** 25IT3A & 25IT3B
- **Activity:** Project-Based Assessment
- **Scenario Assigned:** **Scenario 16 – Vehicle Service Management and Maintenance Analysis System**
- **Assigned Register Numbers:** `16, 46, 76, 106`

### Integrated Course Mapping
1. **25ITT31 – Database Management Systems**: E-R modeling, relational schema, normalization (1NF, 2NF, 3NF), and SQL DDL/DML/DQL scripts.
2. **25ITT32 – Java Programming**: Object-oriented application, inheritance, exception handling, collections/streams, and JDBC connectivity.
3. **25ITT33 – Software Engineering**: IEEE-compliant SRS, Use Case diagrams, Class diagrams, architectural design, and black-box/white-box test cases.
4. **25ITC31 – Foundations of Data Science**: Database retrieval, Pandas data cleaning, and statistical visualizations using Matplotlib & Seaborn.

---

## 🚗 Project Description & Scenario Problem Statement

An automobile service center manages customer details, vehicle profiles, service appointments, workshop maintenance records, and billing transactions. Maintaining these records manually leads to scheduling conflicts, difficulty tracking vehicle history, and delays in identifying frequently serviced vehicles and high-cost maintenance patterns.

The **Vehicle Service Management and Maintenance Analysis System** provides:
- Centralized tracking of customer and vehicle records.
- Service appointment scheduling avoiding double-booking.
- Workshop maintenance logging (mechanic tasks, spare parts, and labor costs).
- Automated invoice computation incorporating labor, spare parts, GST (18%), and discounts.
- Statistical reporting on vehicle maintenance history and workshop workload.
- A Python Data Science engine for exploratory data analysis (EDA) and predictive trend visualizations.

---

## 🛠️ Technologies Used

| Domain | Technology / Tool | Version / Library |
|---|---|---|
| **Programming Language** | Java (Core OOP, JDBC, DAO Pattern) | JDK 11 / 17 / 21 |
| **Database Management** | SQLite (Embedded) & MySQL | SQLite 3.x / MySQL 8.0+ |
| **Data Analytics** | Python | Python 3.9+ (`pandas`, `numpy`) |
| **Data Visualization** | Matplotlib & Seaborn | `matplotlib>=3.8`, `seaborn>=0.13` |
| **Documentation & Modeling** | Markdown & Mermaid | GitHub Flavored Markdown |

---

## 📂 Repository Structure

```text
vechile project/
│
├── docs/                                # Software Engineering & Design Documents
│   ├── SRS.md                          # Complete Software Requirements Specification (IEEE 830)
│   ├── ARCHITECTURE_DESIGN.md          # Use Case, Class, Architecture & ER Diagrams
│   ├── NORMALIZATION.md                # 1NF, 2NF, 3NF documentation & Relational Schema
│   └── TESTING_REPORT.md               # Test cases (Black-box & White-box) with actual results
│
├── database/                            # DBMS Database Deliverables
│   ├── schema.sql                      # DDL: Table creation, keys, and indexes
│   ├── sample_data.sql                 # DML: Realistic dataset (customers, vehicles, appointments, services, bills)
│   ├── queries.sql                     # DQL: Reporting queries, joins, aggregates, and views
│   ├── init_db.py                      # Database creation and populator script
│   └── vehicle_service.db              # Pre-populated SQLite database (ready to run)
│
├── java/                                # Java Application Source Code
│   ├── lib/                            # JDBC Drivers (sqlite-jdbc, slf4j)
│   ├── bin/                            # Compiled .class files
│   └── src/com/vcet/vehicleservice/
│       ├── model/                      # Customer, Vehicle, Appointment, ServiceRecord, Bill
│       ├── dao/                        # DatabaseConnection, CustomerDAO, VehicleDAO, etc.
│       ├── service/                    # BillingService, ReportService
│       └── main/                       # VehicleServiceApp (Interactive CLI entry point)
│
├── python/                              # Data Science & Visual Analytics
│   ├── analysis.py                     # Database ingestion, preprocessing & plotting script
│   ├── maintenance_analysis.ipynb      # Interactive Jupyter Notebook
│   └── visualizations/                 # Exported high-resolution (300 DPI) chart images
│       ├── 1_service_frequency_workload.png
│       ├── 2_monthly_revenue_trend.png
│       ├── 3_vehicle_type_distribution.png
│       ├── 4_cost_spread_by_vehicle_type.png
│       ├── 5_vehicle_cumulative_cost.png
│       └── 6_correlation_heatmap.png
│
├── sample_outputs/                      # Verification Logs
│   └── CLI_EXECUTION_SAMPLE.md         # Full terminal interaction transcripts
│
├── init_database.bat                    # 1-click batch script to initialize DB
├── run_app.bat                          # 1-click batch script to compile & launch Java App
├── run_analysis.bat                     # 1-click batch script to run Python Visualizations
└── README.md                            # Main project documentation
```

---

## ⚡ Execution Instructions

### Step 1: Database Initialization
The SQLite database `database/vehicle_service.db` comes pre-populated. To reset or re-initialize:
```powershell
python database/init_db.py
```
*(Or double-click `init_database.bat`)*

> **Note on MySQL:** If your college lab requires MySQL, simply import `database/schema.sql` and `database/sample_data.sql` into MySQL Workbench or phpMyAdmin. In `java/src/com/vcet/vehicleservice/dao/DatabaseConnection.java`, set `DB_TYPE = "MYSQL"`.

---

### Step 2: Compile & Run Java Application
Compile and launch the interactive Java console application:
```powershell
# Using the automated launcher:
.\run_app.bat
```
Or manually using `javac` and `java`:
```powershell
javac -encoding UTF-8 -cp "java/lib/*" -d "java/bin" @java/sources.txt
java -cp "java/bin;java/lib/*" com.vcet.vehicleservice.main.VehicleServiceApp
```

---

### Step 3: Run Python Analytics & Visualizations
Execute the Data Science pipeline to generate statistical summaries and charts:
```powershell
# Using the automated launcher:
.\run_analysis.bat
```
Or manually:
```powershell
python python/analysis.py
```
All charts will be generated in `python/visualizations/`.

---

## 📊 Visualizations Overview

The Python analytics suite generates 6 visualizations:

| Chart | Filename | Description |
|---|---|---|
| **1. Workload by Service Type** | `1_service_frequency_workload.png` | Bar chart showing frequency of service types (General Service, Oil Change, Brake Overhaul, etc.). |
| **2. Monthly Revenue Trend** | `2_monthly_revenue_trend.png` | Dual-axis line and bar chart displaying revenue and completed jobs across 2026. |
| **3. Vehicle Type Distribution** | `3_vehicle_type_distribution.png` | Donut chart showing the proportion of SUVs, Sedans, Hatchbacks, and Two-Wheelers serviced. |
| **4. Maintenance Cost Spread** | `4_cost_spread_by_vehicle_type.png` | Seaborn box plot & strip plot displaying cost variance and outlier detection. |
| **5. Cumulative Vehicle Cost** | `5_vehicle_cumulative_cost.png` | Horizontal bar chart highlighting vehicles incurring the highest cumulative maintenance costs. |
| **6. Correlation Heatmap** | `6_correlation_heatmap.png` | Pearson correlation matrix evaluating parts costs, labor costs, vehicle age, and total invoices. |

---

## 🧪 Testing Summary

- **Total Test Cases**: 18
- **Passed**: 18 / 18 (100% Pass Rate)
- **Testing Techniques Employed**:
  - Equivalence Class Partitioning (ECP) & Boundary Value Analysis (BVA)
  - Foreign Key Referential Integrity & Unique Constraint Testing
  - Mathematical Billing & Tax Verification
  - End-to-end Integration & Pipeline Testing
- Complete test cases and execution logs are documented in [`docs/TESTING_REPORT.md`](file:///c:/Users/senth/OneDrive/文档/vechile%20project/docs/TESTING_REPORT.md).

---

## 📑 Assessment Deliverables Checklist

- [x] **SRS Document**: [`docs/SRS.md`](file:///c:/Users/senth/OneDrive/文档/vechile%20project/docs/SRS.md)
- [x] **Design Diagrams**: [`docs/ARCHITECTURE_DESIGN.md`](file:///c:/Users/senth/OneDrive/文档/vechile%20project/docs/ARCHITECTURE_DESIGN.md) (Use Case, Class, Architecture, ER)
- [x] **Normalization & Relational Model**: [`docs/NORMALIZATION.md`](file:///c:/Users/senth/OneDrive/文档/vechile%20project/docs/NORMALIZATION.md) (1NF, 2NF, 3NF)
- [x] **Testing Document**: [`docs/TESTING_REPORT.md`](file:///c:/Users/senth/OneDrive/文档/vechile%20project/docs/TESTING_REPORT.md)
- [x] **Database Scripts**: [`database/schema.sql`](file:///c:/Users/senth/OneDrive/文档/vechile%20project/database/schema.sql), [`database/sample_data.sql`](file:///c:/Users/senth/OneDrive/文档/vechile%20project/database/sample_data.sql), [`database/queries.sql`](file:///c:/Users/senth/OneDrive/文档/vechile%20project/database/queries.sql)
- [x] **Java Source Code**: [`java/src/`](file:///c:/Users/senth/OneDrive/文档/vechile%20project/java/src/) (Models, DAOs, Services, CLI Application)
- [x] **Sample Inputs and Outputs**: [`sample_outputs/CLI_EXECUTION_SAMPLE.md`](file:///c:/Users/senth/OneDrive/文档/vechile%20project/sample_outputs/CLI_EXECUTION_SAMPLE.md)
- [x] **Python Code & Visualizations**: [`python/analysis.py`](file:///c:/Users/senth/OneDrive/文档/vechile%20project/python/analysis.py), [`python/maintenance_analysis.ipynb`](file:///c:/Users/senth/OneDrive/文档/vechile%20project/python/maintenance_analysis.ipynb), [`python/visualizations/`](file:///c:/Users/senth/OneDrive/文档/vechile%20project/python/visualizations/)
- [x] **Execution Launcher Scripts**: `run_app.bat`, `run_analysis.bat`, `init_database.bat`
- [x] **README Documentation**: [`README.md`](file:///c:/Users/senth/OneDrive/文档/vechile%20project/README.md)
