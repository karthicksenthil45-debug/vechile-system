package com.vcet.vehicleservice.main;

import com.vcet.vehicleservice.dao.*;
import com.vcet.vehicleservice.model.*;
import com.vcet.vehicleservice.service.BillingService;
import com.vcet.vehicleservice.service.ReportService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Main Application Entry Point for Vehicle Service Management System.
 * VCET - Department of Information Technology.
 * Project-Based Assessment: Scenario 16 (Register Nos: 16, 46, 76, 106).
 */
public class VehicleServiceApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final CustomerDAO customerDAO = new CustomerDAO();
    private static final VehicleDAO vehicleDAO = new VehicleDAO();
    private static final AppointmentDAO appointmentDAO = new AppointmentDAO();
    private static final ServiceRecordDAO serviceRecordDAO = new ServiceRecordDAO();
    private static final BillDAO billDAO = new BillDAO();
    private static final BillingService billingService = new BillingService();
    private static final ReportService reportService = new ReportService();

    public static void main(String[] args) {
        System.out.println("=".repeat(75));
        System.out.println("   VEHICLE SERVICE MANAGEMENT & MAINTENANCE ANALYSIS SYSTEM");
        System.out.println("       Velalar College of Engineering and Technology (VCET)");
        System.out.println("                  Department of IT - Scenario 16");
        System.out.println("=".repeat(75));

        boolean running = true;
        while (running) {
            printMainMenu();
            System.out.print("Select an option (0-7): ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    handleCustomerManagement();
                    break;
                case "2":
                    handleVehicleManagement();
                    break;
                case "3":
                    handleAppointmentScheduling();
                    break;
                case "4":
                    handleMaintenanceRecording();
                    break;
                case "5":
                    handleBilling();
                    break;
                case "6":
                    handleReports();
                    break;
                case "7":
                    triggerPythonAnalytics();
                    break;
                case "0":
                    running = false;
                    System.out.println("\nExiting system. Thank you for using Vehicle Service Management!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 0 and 7.");
            }
        }
        DatabaseConnection.closeConnection();
    }

    private static void printMainMenu() {
        System.out.println("\n" + "-".repeat(50));
        System.out.println("                   MAIN MENU");
        System.out.println("-".repeat(50));
        System.out.println("1. Customer Details Management");
        System.out.println("2. Vehicle Details Management");
        System.out.println("3. Schedule Service Appointments");
        System.out.println("4. Record Maintenance Activities & Spare Parts");
        System.out.println("5. Billing & Invoice Generation");
        System.out.println("6. Reports (Service History, Costs & Workload)");
        System.out.println("7. Run Python Data Analysis & Visualizations");
        System.out.println("0. Exit Application");
        System.out.println("-".repeat(50));
    }

    // -------------------------------------------------------------
    // 1. Customer Management
    // -------------------------------------------------------------
    private static void handleCustomerManagement() {
        System.out.println("\n--- [Customer Management] ---");
        System.out.println("1. Register New Customer");
        System.out.println("2. View All Customers");
        System.out.println("3. Search Customer by ID");
        System.out.print("Choose action (1-3): ");
        String choice = scanner.nextLine().trim();

        try {
            if ("1".equals(choice)) {
                System.out.print("Enter Customer Name: ");
                String name = scanner.nextLine().trim();
                System.out.print("Enter Phone Number: ");
                String phone = scanner.nextLine().trim();
                System.out.print("Enter Email Address: ");
                String email = scanner.nextLine().trim();
                System.out.print("Enter Address: ");
                String address = scanner.nextLine().trim();

                if (name.isEmpty() || phone.isEmpty()) {
                    System.out.println("Error: Name and Phone are mandatory!");
                    return;
                }

                Customer customer = new Customer(name, phone, email, address);
                int id = customerDAO.addCustomer(customer);
                System.out.println("Success! New Customer created with Customer ID: " + id);

            } else if ("2".equals(choice)) {
                List<Customer> list = customerDAO.getAllCustomers();
                System.out.println("\n--- Registered Customers (" + list.size() + ") ---");
                list.forEach(System.out::println);

            } else if ("3".equals(choice)) {
                System.out.print("Enter Customer ID: ");
                int id = Integer.parseInt(scanner.nextLine().trim());
                Customer c = customerDAO.getCustomerById(id);
                if (c != null) {
                    System.out.println("Found: " + c);
                } else {
                    System.out.println("No customer found with ID: " + id);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Input error: Please enter valid numbers.");
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    // -------------------------------------------------------------
    // 2. Vehicle Management
    // -------------------------------------------------------------
    private static void handleVehicleManagement() {
        System.out.println("\n--- [Vehicle Management] ---");
        System.out.println("1. Register New Vehicle");
        System.out.println("2. View All Vehicles");
        System.out.println("3. Search Vehicle by Registration Number");
        System.out.print("Choose action (1-3): ");
        String choice = scanner.nextLine().trim();

        try {
            if ("1".equals(choice)) {
                System.out.print("Enter Customer ID: ");
                int customerId = Integer.parseInt(scanner.nextLine().trim());

                if (customerDAO.getCustomerById(customerId) == null) {
                    System.out.println("Error: Customer ID " + customerId + " does not exist!");
                    return;
                }

                System.out.print("Enter Registration Number (e.g. TN33AB1234): ");
                String reg = scanner.nextLine().trim().toUpperCase();
                System.out.print("Enter Vehicle Model (e.g. Hyundai i20 / Tata Nexon): ");
                String model = scanner.nextLine().trim();
                System.out.print("Enter Vehicle Type (Hatchback/Sedan/SUV/Two-Wheeler): ");
                String type = scanner.nextLine().trim();
                System.out.print("Enter Manufacture Year (e.g. 2022): ");
                int year = Integer.parseInt(scanner.nextLine().trim());

                Vehicle v = new Vehicle(customerId, reg, model, type, year);
                int id = vehicleDAO.addVehicle(v);
                System.out.println("Success! Vehicle registered with Vehicle ID: " + id);

            } else if ("2".equals(choice)) {
                List<Vehicle> list = vehicleDAO.getAllVehicles();
                System.out.println("\n--- Registered Vehicles (" + list.size() + ") ---");
                list.forEach(System.out::println);

            } else if ("3".equals(choice)) {
                System.out.print("Enter Registration Plate (e.g. TN33AB1001): ");
                String reg = scanner.nextLine().trim();
                Vehicle v = vehicleDAO.getVehicleByRegNumber(reg);
                if (v != null) {
                    System.out.println("Found: " + v);
                } else {
                    System.out.println("Vehicle not found: " + reg);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Input error: Please enter valid numbers.");
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    // -------------------------------------------------------------
    // 3. Appointment Scheduling
    // -------------------------------------------------------------
    private static void handleAppointmentScheduling() {
        System.out.println("\n--- [Service Appointment Scheduling] ---");
        System.out.println("1. Schedule New Appointment");
        System.out.println("2. View All Appointments");
        System.out.println("3. Update Appointment Status");
        System.out.print("Choose action (1-3): ");
        String choice = scanner.nextLine().trim();

        try {
            if ("1".equals(choice)) {
                System.out.print("Enter Vehicle ID: ");
                int vehicleId = Integer.parseInt(scanner.nextLine().trim());
                if (vehicleDAO.getVehicleById(vehicleId) == null) {
                    System.out.println("Error: Vehicle ID " + vehicleId + " does not exist!");
                    return;
                }

                System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
                String date = scanner.nextLine().trim();
                System.out.print("Enter Service Type (General Service / Oil Change / Brake Overhaul / AC Service): ");
                String serviceType = scanner.nextLine().trim();
                System.out.print("Enter Notes/Complaints: ");
                String notes = scanner.nextLine().trim();

                Appointment appt = new Appointment(vehicleId, date, serviceType, "Scheduled", notes);
                int id = appointmentDAO.addAppointment(appt);
                System.out.println("Success! Appointment scheduled with ID: " + id);

            } else if ("2".equals(choice)) {
                List<Appointment> list = appointmentDAO.getAllAppointments();
                System.out.println("\n--- All Appointments (" + list.size() + ") ---");
                list.forEach(System.out::println);

            } else if ("3".equals(choice)) {
                System.out.print("Enter Appointment ID: ");
                int apptId = Integer.parseInt(scanner.nextLine().trim());
                System.out.print("Enter New Status (Scheduled / In-Progress / Completed / Cancelled): ");
                String status = scanner.nextLine().trim();
                boolean updated = appointmentDAO.updateStatus(apptId, status);
                if (updated) {
                    System.out.println("Appointment status successfully updated to: " + status);
                } else {
                    System.out.println("Failed to update. Verify Appointment ID.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // -------------------------------------------------------------
    // 4. Maintenance Service Recording
    // -------------------------------------------------------------
    private static void handleMaintenanceRecording() {
        System.out.println("\n--- [Record Maintenance & Spare Parts] ---");
        System.out.println("1. Record Maintenance Activity for Appointment");
        System.out.println("2. View All Service Records");
        System.out.print("Choose action (1-2): ");
        String choice = scanner.nextLine().trim();

        try {
            if ("1".equals(choice)) {
                System.out.print("Enter Appointment ID: ");
                int apptId = Integer.parseInt(scanner.nextLine().trim());
                Appointment appt = appointmentDAO.getAppointmentById(apptId);
                if (appt == null) {
                    System.out.println("Error: Appointment ID " + apptId + " does not exist!");
                    return;
                }

                System.out.print("Enter Technician Name: ");
                String techName = scanner.nextLine().trim();
                System.out.print("Enter Work Performed Description: ");
                String work = scanner.nextLine().trim();
                System.out.print("Enter Spare Parts Cost (Rs): ");
                double partsCost = Double.parseDouble(scanner.nextLine().trim());
                System.out.print("Enter Labor Charges (Rs): ");
                double labor = Double.parseDouble(scanner.nextLine().trim());

                if (partsCost < 0 || labor < 0) {
                    System.out.println("Error: Costs cannot be negative!");
                    return;
                }

                String completionDate = LocalDate.now().toString();
                ServiceRecord record = new ServiceRecord(apptId, techName, work, partsCost, labor, "Completed", completionDate);
                int serviceId = serviceRecordDAO.addServiceRecord(record);

                // Update appointment status to Completed
                appointmentDAO.updateStatus(apptId, "Completed");

                System.out.println("Success! Maintenance record saved with Service ID: " + serviceId);
                System.out.println("Subtotal: Rs. " + record.getSubtotal());

            } else if ("2".equals(choice)) {
                List<ServiceRecord> list = serviceRecordDAO.getAllServiceRecords();
                System.out.println("\n--- Maintenance Records (" + list.size() + ") ---");
                list.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // -------------------------------------------------------------
    // 5. Billing & Invoicing
    // -------------------------------------------------------------
    private static void handleBilling() {
        System.out.println("\n--- [Billing & Invoicing] ---");
        System.out.println("1. Calculate and Generate Bill for Service");
        System.out.println("2. View All Generated Bills");
        System.out.println("3. Print Invoice Receipt by Bill ID");
        System.out.print("Choose action (1-3): ");
        String choice = scanner.nextLine().trim();

        try {
            if ("1".equals(choice)) {
                System.out.print("Enter Service Record ID: ");
                int serviceId = Integer.parseInt(scanner.nextLine().trim());
                System.out.print("Enter Discount Percentage (e.g. 5 for 5%, 0 for none): ");
                double discount = Double.parseDouble(scanner.nextLine().trim());
                System.out.print("Enter Payment Mode (UPI / Cash / Credit Card / Debit Card): ");
                String paymentMode = scanner.nextLine().trim();

                Bill bill = billingService.generateBill(serviceId, discount, paymentMode);
                System.out.println("\nBill Generated Successfully!");
                System.out.println(bill.generateInvoiceReceipt());

            } else if ("2".equals(choice)) {
                List<Bill> list = billDAO.getAllBills();
                System.out.println("\n--- Generated Bills (" + list.size() + ") ---");
                list.forEach(System.out::println);

            } else if ("3".equals(choice)) {
                System.out.print("Enter Bill ID: ");
                int billId = Integer.parseInt(scanner.nextLine().trim());
                Bill bill = billDAO.getBillById(billId);
                if (bill != null) {
                    System.out.println(bill.generateInvoiceReceipt());
                } else {
                    System.out.println("Bill ID " + billId + " not found!");
                }
            }
        } catch (Exception e) {
            System.err.println("Billing error: " + e.getMessage());
        }
    }

    // -------------------------------------------------------------
    // 6. Reports
    // -------------------------------------------------------------
    private static void handleReports() {
        System.out.println("\n--- [Service Center Reports] ---");
        System.out.println("1. Vehicle Service History (by Reg Number)");
        System.out.println("2. Vehicle-Wise Cumulative Maintenance Costs");
        System.out.println("3. Workshop Workload & Demand Analysis");
        System.out.print("Choose report (1-3): ");
        String choice = scanner.nextLine().trim();

        if ("1".equals(choice)) {
            System.out.print("Enter Vehicle Registration Number (e.g. TN33AB1001): ");
            String reg = scanner.nextLine().trim();
            reportService.printVehicleServiceHistory(reg);
        } else if ("2".equals(choice)) {
            reportService.printVehicleWiseMaintenanceCosts();
        } else if ("3".equals(choice)) {
            reportService.printServiceCenterWorkload();
        } else {
            System.out.println("Invalid report selection.");
        }
    }

    // -------------------------------------------------------------
    // 7. Python Data Analysis
    // -------------------------------------------------------------
    private static void triggerPythonAnalytics() {
        System.out.println("\nLaunching Python Data Science Analytics Pipeline...");
        try {
            ProcessBuilder pb = new ProcessBuilder("python", "python/analysis.py");
            pb.redirectErrorStream(true);
            Process process = pb.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("  [Python] " + line);
                }
            }
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("\nVisualizations successfully created in: python/visualizations/");
            } else {
                System.err.println("\nPython script terminated with error code: " + exitCode);
            }
        } catch (Exception e) {
            System.err.println("Failed to launch Python analysis: " + e.getMessage());
            System.out.println("You can also run it directly via: python python/analysis.py");
        }
    }
}
