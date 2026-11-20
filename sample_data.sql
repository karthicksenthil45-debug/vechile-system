-- =====================================================================
-- SAMPLE DATA SCRIPT (DML)
-- Vehicle Service Management and Maintenance Analysis System
-- VCET - Department of Information Technology
-- =====================================================================

-- 1. Insert Customers
INSERT INTO customers (customer_id, name, phone, email, address) VALUES
(1, 'Ramesh Kumar', '9876543210', 'ramesh.k@gmail.com', '12 Gandhiji Road, Erode'),
(2, 'Priya Senthil', '9842112345', 'priya.s@yahoo.com', '45 Perundurai Road, Erode'),
(3, 'Karthik Raja', '9789012345', 'karthik.raja@outlook.com', '78 Brough Road, Erode'),
(4, 'Ananya Sharma', '9443322110', 'ananya.sharma@gmail.com', '23 Mettur Road, Erode'),
(5, 'Suresh Balaji', '9894455667', 'suresh.b@gmail.com', '89 Chennimalai Road, Erode'),
(6, 'Deepa Natarajan', '9750011223', 'deepa.n@gmail.com', '15 Thindal, Erode'),
(7, 'Vigneshwaran M', '9629988776', 'vicky.m@gmail.com', '56 Surampatti, Erode'),
(8, 'Kavitha Murugesan', '9442233445', 'kavitha.m@yahoo.com', '102 Kollampalayam, Erode'),
(9, 'Manoj Prabakar', '9865123456', 'manoj.p@gmail.com', '34 Solar, Erode'),
(10, 'Swetha Sundaram', '9944556677', 'swetha.s@gmail.com', '67 Villarasampatti, Erode'),
(11, 'Arun Prakash', '9843344556', 'arun.prakash@gmail.com', '88 Modakkurichi, Erode'),
(12, 'Divya Chandran', '9786677889', 'divya.c@gmail.com', '19 Kasipalayam, Erode');

-- 2. Insert Vehicles
INSERT INTO vehicles (vehicle_id, customer_id, reg_number, model, vehicle_type, manufacture_year) VALUES
(1, 1, 'TN33AB1001', 'Hyundai i20', 'Hatchback', 2021),
(2, 1, 'TN33CD2002', 'Royal Enfield Classic 350', 'Two-Wheeler', 2022),
(3, 2, 'TN33EF3003', 'Honda City', 'Sedan', 2020),
(4, 3, 'TN33GH4004', 'Tata Nexon', 'SUV', 2022),
(5, 4, 'TN33IJ5005', 'Maruti Suzuki Swift', 'Hatchback', 2019),
(6, 4, 'TN33KL6006', 'TVS Jupiter', 'Two-Wheeler', 2023),
(7, 5, 'TN33MN7007', 'Toyota Innova Crysta', 'SUV', 2018),
(8, 6, 'TN33OP8008', 'Kia Seltos', 'SUV', 2021),
(9, 7, 'TN33QR9009', 'Mahindra XUV700', 'SUV', 2023),
(10, 8, 'TN33ST0110', 'Hyundai Creta', 'SUV', 2020),
(11, 9, 'TN33UV1221', 'Yamaha MT-15', 'Two-Wheeler', 2022),
(12, 10, 'TN33WX2332', 'Maruti Suzuki Baleno', 'Hatchback', 2021),
(13, 11, 'TN33YZ3443', 'Skoda Slavia', 'Sedan', 2023),
(14, 12, 'TN33AA4554', 'Honda Activa 6G', 'Two-Wheeler', 2022),
(15, 3, 'TN33BC5665', 'Tata Punch', 'SUV', 2023);

-- 3. Insert Appointments (Across 2026)
INSERT INTO appointments (appointment_id, vehicle_id, appointment_date, service_type, status, notes) VALUES
(1, 1, '2026-01-10', 'General Service', 'Completed', '10,000 km periodic checkup'),
(2, 2, '2026-01-18', 'Oil Change', 'Completed', 'Engine oil flush and chain lube'),
(3, 3, '2026-02-05', 'Brake Overhaul', 'Completed', 'Brake pads worn, vibration during braking'),
(4, 4, '2026-02-14', 'Periodic Maintenance', 'Completed', '30,000 km full inspection'),
(5, 5, '2026-03-02', 'AC Service', 'Completed', 'AC cooling insufficient in hot afternoons'),
(6, 6, '2026-03-12', 'General Service', 'Completed', 'Periodic minor servicing'),
(7, 7, '2026-04-08', 'Suspension Repair', 'Completed', 'Front strut noise on uneven roads'),
(8, 8, '2026-04-20', 'Engine Diagnostics', 'Completed', 'Check engine warning lamp illuminated'),
(9, 9, '2026-05-15', 'General Service', 'Completed', 'First year annual maintenance'),
(10, 10, '2026-05-25', 'Brake Overhaul', 'Completed', 'Front and rear brake disc inspection'),
(11, 11, '2026-06-04', 'Oil Change', 'Completed', 'Synthetic engine oil replacement'),
(12, 12, '2026-06-18', 'General Service', 'Completed', 'Regular maintenance, air & oil filter replacement'),
(13, 13, '2026-07-07', 'AC Service', 'Completed', 'Cabin filter replacement and gas top-up'),
(14, 14, '2026-07-22', 'General Service', 'Completed', 'Spark plug change and carburetor cleaning'),
(15, 15, '2026-08-05', 'Periodic Maintenance', 'Completed', 'Engine tuning and wheel alignment'),
(16, 1, '2026-08-19', 'Brake Overhaul', 'Completed', 'Brake fluid flushing and brake shoe change'),
(17, 3, '2026-09-02', 'General Service', 'Completed', '6-month periodic inspection'),
(18, 4, '2026-09-16', 'Engine Diagnostics', 'Completed', 'Sensor calibration and software scan'),
(19, 7, '2026-10-05', 'General Service', 'Completed', 'Major 80,000 km service overhaul'),
(20, 8, '2026-10-21', 'Oil Change', 'Completed', 'High performance synthetic oil upgrade'),
(21, 5, '2026-11-03', 'Suspension Repair', 'Completed', 'Bushings replacement and wheel balance'),
(22, 9, '2026-11-12', 'Periodic Maintenance', 'Completed', 'Biannual routine checkup'),
(23, 10, '2026-11-20', 'General Service', 'Scheduled', 'Upcoming scheduled winter check'),
(24, 2, '2026-11-22', 'Oil Change', 'Scheduled', 'Scheduled chain sprocket replacement'),
(25, 12, '2026-11-24', 'AC Service', 'Scheduled', 'Customer reported minor fan rattle');

-- 4. Insert Service Records (Completed services)
INSERT INTO service_records (service_id, appointment_id, technician_name, work_performed, spare_parts_cost, labor_charges, service_status, completion_date) VALUES
(1, 1, 'Saravanan M', 'Engine oil, oil filter replacement, 40-point vehicle checkup', 1850.00, 800.00, 'Completed', '2026-01-10'),
(2, 2, 'Kabilan R', 'Motul synthetic engine oil change, drive chain cleaning and lubrication', 850.00, 300.00, 'Completed', '2026-01-18'),
(3, 3, 'Saravanan M', 'Front ceramic brake pads replacement, rotor skimming and caliper lubrication', 3200.00, 1200.00, 'Completed', '2026-02-05'),
(4, 4, 'Ganesh K', 'Complete 30k inspection, transmission oil change, coolant flushing', 4500.00, 1800.00, 'Completed', '2026-02-14'),
(5, 5, 'Velumani S', 'AC evaporator cleaning, refrigerant gas recharge R134a, cabin micro-filter', 2800.00, 1100.00, 'Completed', '2026-03-02'),
(6, 6, 'Kabilan R', 'Engine oil change, air filter clean, brake adjustment, water wash', 450.00, 350.00, 'Completed', '2026-03-12'),
(7, 7, 'Saravanan M', 'Front MacPherson strut replacement, sway bar links, wheel alignment', 7200.00, 2500.00, 'Completed', '2026-04-08'),
(8, 8, 'Murugan T', 'OBD-II scanner diagnosis, oxygen sensor replacement and ECU reset', 3400.00, 950.00, 'Completed', '2026-04-20'),
(9, 9, 'Ganesh K', 'Full periodic service, synthetic oil, wheel balancing and rotation', 3800.00, 1400.00, 'Completed', '2026-05-15'),
(10, 10, 'Velumani S', 'Brake pad replacement and brake bleeding with DOT4 fluid', 2900.00, 1050.00, 'Completed', '2026-05-25'),
(11, 11, 'Kabilan R', 'Yamalube fully synthetic oil change, chain adjustment', 750.00, 250.00, 'Completed', '2026-06-04'),
(12, 12, 'Saravanan M', 'Engine oil, air cleaner element, spark plug clean, throttle body flush', 2100.00, 900.00, 'Completed', '2026-06-18'),
(13, 13, 'Velumani S', 'AC system pressure test, refrigerant top-up, pollen filter replacement', 2200.00, 850.00, 'Completed', '2026-07-07'),
(14, 14, 'Kabilan R', 'Engine oil replacement, CVT belt check, clutch shoe deglazing', 600.00, 400.00, 'Completed', '2026-07-22'),
(15, 15, 'Ganesh K', 'Throttle body cleaning, injector flush, spark plug replacement', 2600.00, 1150.00, 'Completed', '2026-08-05'),
(16, 16, 'Saravanan M', 'Rear drum brake shoe replacement, handbrake cable tension adjustment', 1600.00, 750.00, 'Completed', '2026-08-19'),
(17, 17, 'Murugan T', 'Engine oil replacement, coolant top-up, battery health diagnosis', 2400.00, 950.00, 'Completed', '2026-09-02'),
(18, 18, 'Ganesh K', 'MAF sensor cleaning, throttle position sensor calibration, road test', 1200.00, 1100.00, 'Completed', '2026-09-16'),
(19, 19, 'Saravanan M', 'Timing belt replacement, water pump inspection, engine oil & filter', 8900.00, 3200.00, 'Completed', '2026-10-05'),
(20, 20, 'Velumani S', 'Castrol Edge 5W-40 full synthetic oil change and oil filter replacement', 3100.00, 700.00, 'Completed', '2026-10-21'),
(21, 21, 'Saravanan M', 'Lower control arm bushing replacement and camber caster adjustment', 3800.00, 1600.00, 'Completed', '2026-11-03'),
(22, 22, 'Ganesh K', 'Routine 6-month checkup, brake fluid top-up, air pressure, full wash', 1500.00, 850.00, 'Completed', '2026-11-12');

-- 5. Insert Bills
-- Calculation rule: Subtotal = parts_cost + labor_cost; Tax = 18% of Subtotal; Total = Subtotal + Tax - Discount
INSERT INTO bills (bill_id, service_id, bill_date, parts_cost, labor_cost, tax_amount, discount_amount, total_amount, payment_status, payment_method) VALUES
(1, 1, '2026-01-10', 1850.00, 800.00, 477.00, 100.00, 3027.00, 'Paid', 'UPI'),
(2, 2, '2026-01-18', 850.00, 300.00, 207.00, 50.00, 1307.00, 'Paid', 'Cash'),
(3, 3, '2026-02-05', 3200.00, 1200.00, 792.00, 200.00, 4992.00, 'Paid', 'Credit Card'),
(4, 4, '2026-02-14', 4500.00, 1800.00, 1134.00, 300.00, 7134.00, 'Paid', 'UPI'),
(5, 5, '2026-03-02', 2800.00, 1100.00, 702.00, 150.00, 4452.00, 'Paid', 'Debit Card'),
(6, 6, '2026-03-12', 450.00, 350.00, 144.00, 40.00, 904.00, 'Paid', 'Cash'),
(7, 7, '2026-04-08', 7200.00, 2500.00, 1746.00, 500.00, 10946.00, 'Paid', 'Net Banking'),
(8, 8, '2026-04-20', 3400.00, 950.00, 783.00, 150.00, 4983.00, 'Paid', 'UPI'),
(9, 9, '2026-05-15', 3800.00, 1400.00, 936.00, 200.00, 5936.00, 'Paid', 'Credit Card'),
(10, 10, '2026-05-25', 2900.00, 1050.00, 711.00, 150.00, 4511.00, 'Paid', 'UPI'),
(11, 11, '2026-06-04', 750.00, 250.00, 180.00, 30.00, 1150.00, 'Paid', 'UPI'),
(12, 12, '2026-06-18', 2100.00, 900.00, 540.00, 100.00, 3440.00, 'Paid', 'Cash'),
(13, 13, '2026-07-07', 2200.00, 850.00, 549.00, 100.00, 3499.00, 'Paid', 'UPI'),
(14, 14, '2026-07-22', 600.00, 400.00, 180.00, 50.00, 1130.00, 'Paid', 'Cash'),
(15, 15, '2026-08-05', 2600.00, 1150.00, 675.00, 150.00, 4275.00, 'Paid', 'Credit Card'),
(16, 16, '2026-08-19', 1600.00, 750.00, 423.00, 100.00, 2673.00, 'Paid', 'UPI'),
(17, 17, '2026-09-02', 2400.00, 950.00, 603.00, 100.00, 3853.00, 'Paid', 'UPI'),
(18, 18, '2026-09-16', 1200.00, 1100.00, 414.00, 100.00, 2614.00, 'Paid', 'Debit Card'),
(19, 19, '2026-10-05', 8900.00, 3200.00, 2178.00, 600.00, 13678.00, 'Paid', 'Net Banking'),
(20, 20, '2026-10-21', 3100.00, 700.00, 684.00, 150.00, 4334.00, 'Paid', 'UPI'),
(21, 21, '2026-11-03', 3800.00, 1600.00, 972.00, 200.00, 6172.00, 'Paid', 'Credit Card'),
(22, 22, '2026-11-12', 1500.00, 850.00, 423.00, 100.00, 2673.00, 'Paid', 'UPI');
