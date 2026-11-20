"""
=============================================================================
VEHICLE SERVICE MANAGEMENT AND MAINTENANCE ANALYSIS SYSTEM
Course: 25ITC31 - Foundations of Data Science (VCET IT Dept)
Module: Database Retrieval, Data Preprocessing, EDA & Visualizations
=============================================================================
This script:
1. Connects to the vehicle service database (SQLite / MySQL compatible).
2. Retrieves service, customer, vehicle, maintenance, and billing datasets.
3. Preprocesses data (handling nulls, type conversions, feature engineering).
4. Generates statistical summaries and high-resolution visualizations using
   Matplotlib and Seaborn.
=============================================================================
"""

import os
import sqlite3
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns

# Configure plot styling
plt.style.use('seaborn-v0_8-whitegrid' if 'seaborn-v0_8-whitegrid' in plt.style.available else 'default')
plt.rcParams['font.sans-serif'] = 'Arial'
plt.rcParams['font.size'] = 10
plt.rcParams['axes.titlesize'] = 13
plt.rcParams['axes.labelsize'] = 11

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
DB_PATH = os.path.join(BASE_DIR, "..", "database", "vehicle_service.db")
OUTPUT_DIR = os.path.join(BASE_DIR, "visualizations")
os.makedirs(OUTPUT_DIR, exist_ok=True)


def load_dataset():
    """Connects to database and extracts integrated analytical view."""
    print(f"[1/4] Connecting to database: {DB_PATH}")
    conn = sqlite3.connect(DB_PATH)
    
    query = """
    SELECT 
        c.customer_id,
        c.name AS customer_name,
        c.phone,
        v.vehicle_id,
        v.reg_number,
        v.model,
        v.vehicle_type,
        v.manufacture_year,
        a.appointment_id,
        a.appointment_date,
        a.service_type,
        a.status AS appointment_status,
        sr.service_id,
        sr.technician_name,
        sr.work_performed,
        sr.spare_parts_cost,
        sr.labor_charges,
        sr.service_status,
        b.bill_id,
        b.bill_date,
        b.parts_cost,
        b.labor_cost,
        b.tax_amount,
        b.discount_amount,
        b.total_amount,
        b.payment_status,
        b.payment_method
    FROM customers c
    JOIN vehicles v ON c.customer_id = v.customer_id
    JOIN appointments a ON v.vehicle_id = a.vehicle_id
    JOIN service_records sr ON a.appointment_id = sr.appointment_id
    JOIN bills b ON sr.service_id = b.service_id
    """
    
    df = pd.read_sql_query(query, conn)
    conn.close()
    print(f"      Successfully extracted {len(df)} records across 27 columns.")
    return df


def preprocess_data(df):
    """
    Unit 4 (25ITC31): Data Cleaning and Transformation Techniques
    - Missing value detection & handling
    - Data type conversions
    - Feature engineering (dates, parts-to-labor ratio, age of vehicle)
    """
    print("\n[2/4] Preprocessing and Transforming Data...")
    
    # 1. Type Conversions
    df['appointment_date'] = pd.to_datetime(df['appointment_date'])
    df['bill_date'] = pd.to_datetime(df['bill_date'])
    df['service_month'] = df['bill_date'].dt.strftime('%Y-%m')
    df['service_month_name'] = df['bill_date'].dt.strftime('%b %Y')

    # Numeric columns casting
    numeric_cols = ['spare_parts_cost', 'labor_charges', 'parts_cost', 'labor_cost', 'tax_amount', 'discount_amount', 'total_amount']
    for col in numeric_cols:
        df[col] = pd.to_numeric(df[col], errors='coerce').fillna(0.0)

    # 2. Feature Engineering
    df['subtotal'] = df['parts_cost'] + df['labor_cost']
    df['parts_share_pct'] = np.where(df['subtotal'] > 0, (df['parts_cost'] / df['subtotal']) * 100, 0)
    df['vehicle_age'] = 2026 - df['manufacture_year']

    # 3. Missing Value & Duplicate Check
    null_counts = df.isnull().sum().sum()
    duplicate_rows = df.duplicated(subset=['bill_id']).sum()
    print(f"      Total null values found: {null_counts}")
    print(f"      Total duplicate bills: {duplicate_rows}")

    return df


def display_descriptive_statistics(df):
    """Unit 1 & Unit 5: Descriptive Statistics & Summaries."""
    print("\n[3/4] Descriptive Statistical Summary:")
    print("-" * 70)
    stats_cols = ['parts_cost', 'labor_cost', 'tax_amount', 'total_amount', 'vehicle_age']
    stats_df = df[stats_cols].describe().T[['mean', 'std', 'min', '50%', 'max']]
    stats_df.columns = ['Mean', 'Std Dev', 'Min', 'Median (50%)', 'Max']
    print(stats_df.round(2).to_string())
    print("-" * 70)
    print(f"  Total Revenue Generated:  Rs. {df['total_amount'].sum():,.2f}")
    print(f"  Total Spare Parts Spend:  Rs. {df['parts_cost'].sum():,.2f}")
    print(f"  Total Labor Earnings:     Rs. {df['labor_cost'].sum():,.2f}")
    print(f"  Total Tax Collected:      Rs. {df['tax_amount'].sum():,.2f}")
    print(f"  Average Bill per Service: Rs. {df['total_amount'].mean():,.2f}")
    print("-" * 70)


def generate_visualizations(df):
    """
    Unit 5 (25ITC31): Exploratory Data Analysis & Visualizations
    Produces 6 high-impact charts showcasing operational patterns.
    """
    print("\n[4/4] Generating Publication-Quality Visualizations...")

    # -------------------------------------------------------------
    # 1. Bar Chart: Service Frequency by Service Type
    # -------------------------------------------------------------
    fig, ax = plt.subplots(figsize=(10, 6))
    service_counts = df['service_type'].value_counts()
    colors = sns.color_palette("viridis", len(service_counts))
    bars = ax.bar(service_counts.index, service_counts.values, color=colors, edgecolor='black', alpha=0.85)
    
    for bar in bars:
        yval = bar.get_height()
        ax.text(bar.get_x() + bar.get_width() / 2.0, yval + 0.1, f'{int(yval)}', ha='center', va='bottom', fontweight='bold')

    ax.set_title("1. Service Center Workload: Service Request Frequency by Category", pad=15, fontweight='bold')
    ax.set_xlabel("Service Type", labelpad=10)
    ax.set_ylabel("Number of Services Completed", labelpad=10)
    plt.xticks(rotation=25, ha='right')
    plt.tight_layout()
    chart1_path = os.path.join(OUTPUT_DIR, "1_service_frequency_workload.png")
    plt.savefig(chart1_path, dpi=300)
    plt.close()
    print(f"  [+] Chart 1 saved: {chart1_path}")

    # -------------------------------------------------------------
    # 2. Dual-Axis Line & Bar Chart: Monthly Financial Performance
    # -------------------------------------------------------------
    monthly_data = df.groupby('service_month').agg({
        'total_amount': 'sum',
        'service_id': 'count'
    }).reset_index().rename(columns={'total_amount': 'revenue', 'service_id': 'service_count'})

    fig, ax1 = plt.subplots(figsize=(11, 6))
    color_bar = '#3498db'
    ax1.set_xlabel('Service Month', labelpad=10, fontweight='bold')
    ax1.set_ylabel('Total Revenue (INR ₹)', color='#2980b9', labelpad=10, fontweight='bold')
    bars = ax1.bar(monthly_data['service_month'], monthly_data['revenue'], color=color_bar, alpha=0.75, width=0.5, label='Monthly Revenue (₹)')
    ax1.tick_params(axis='y', labelcolor='#2980b9')

    ax2 = ax1.twinx()
    color_line = '#e74c3c'
    ax2.set_ylabel('Service Volume (Jobs Completed)', color=color_line, labelpad=10, fontweight='bold')
    line = ax2.plot(monthly_data['service_month'], monthly_data['service_count'], color=color_line, marker='o', linewidth=2.5, markersize=8, label='Service Count')
    ax2.tick_params(axis='y', labelcolor=color_line)
    ax2.grid(False)

    plt.title("2. Monthly Revenue and Service Volume Trend (2026)", pad=15, fontweight='bold')
    plt.tight_layout()
    chart2_path = os.path.join(OUTPUT_DIR, "2_monthly_revenue_trend.png")
    plt.savefig(chart2_path, dpi=300)
    plt.close()
    print(f"  [+] Chart 2 saved: {chart2_path}")

    # -------------------------------------------------------------
    # 3. Pie / Donut Chart: Vehicle Type Distribution
    # -------------------------------------------------------------
    fig, ax = plt.subplots(figsize=(8, 7))
    type_counts = df['vehicle_type'].value_counts()
    palette = sns.color_palette("Set2", len(type_counts))
    
    wedges, texts, autotexts = ax.pie(
        type_counts.values,
        labels=type_counts.index,
        autopct='%1.1f%%',
        startangle=140,
        colors=palette,
        wedgeprops=dict(width=0.4, edgecolor='w', linewidth=2),
        textprops=dict(fontweight='bold')
    )
    for at in autotexts:
        at.set_fontsize(11)
        at.set_color('#2c3e50')

    ax.set_title("3. Distribution of Vehicle Types Undergoing Maintenance", pad=15, fontweight='bold')
    plt.tight_layout()
    chart3_path = os.path.join(OUTPUT_DIR, "3_vehicle_type_distribution.png")
    plt.savefig(chart3_path, dpi=300)
    plt.close()
    print(f"  [+] Chart 3 saved: {chart3_path}")

    # -------------------------------------------------------------
    # 4. Box Plot: Maintenance Cost Breakdown by Vehicle Type
    # -------------------------------------------------------------
    fig, ax = plt.subplots(figsize=(10, 6))
    sns.boxplot(
        data=df,
        x='vehicle_type',
        y='total_amount',
        hue='vehicle_type',
        palette="Blues_d",
        ax=ax,
        width=0.5,
        legend=False
    )
    sns.stripplot(
        data=df,
        x='vehicle_type',
        y='total_amount',
        color='darkred',
        size=6,
        jitter=0.2,
        alpha=0.65,
        ax=ax
    )
    ax.set_title("4. Maintenance Cost Distribution & Spread by Vehicle Type", pad=15, fontweight='bold')
    ax.set_xlabel("Vehicle Classification", labelpad=10)
    ax.set_ylabel("Total Bill Amount (INR ₹)", labelpad=10)
    plt.tight_layout()
    chart4_path = os.path.join(OUTPUT_DIR, "4_cost_spread_by_vehicle_type.png")
    plt.savefig(chart4_path, dpi=300)
    plt.close()
    print(f"  [+] Chart 4 saved: {chart4_path}")

    # -------------------------------------------------------------
    # 5. Horizontal Bar Chart: Cumulative Cost by Vehicle Model
    # -------------------------------------------------------------
    fig, ax = plt.subplots(figsize=(10, 7))
    model_costs = df.groupby('model')['total_amount'].sum().sort_values(ascending=True)
    palette_hbar = sns.color_palette("mako", len(model_costs))
    bars = ax.barh(model_costs.index, model_costs.values, color=palette_hbar, edgecolor='black', alpha=0.85)

    for bar in bars:
        xval = bar.get_width()
        ax.text(xval + 150, bar.get_y() + bar.get_height() / 2.0, f'₹{xval:,.0f}', ha='left', va='center', fontsize=9, fontweight='bold')

    ax.set_title("5. Vehicle-Wise Cumulative Maintenance Expenditure", pad=15, fontweight='bold')
    ax.set_xlabel("Cumulative Maintenance Spend (INR ₹)", labelpad=10)
    ax.set_ylabel("Vehicle Model", labelpad=10)
    plt.tight_layout()
    chart5_path = os.path.join(OUTPUT_DIR, "5_vehicle_cumulative_cost.png")
    plt.savefig(chart5_path, dpi=300)
    plt.close()
    print(f"  [+] Chart 5 saved: {chart5_path}")

    # -------------------------------------------------------------
    # 6. Correlation Heatmap: Parts, Labor, Tax, Discount & Total
    # -------------------------------------------------------------
    fig, ax = plt.subplots(figsize=(9, 7))
    corr_cols = ['parts_cost', 'labor_cost', 'tax_amount', 'discount_amount', 'total_amount', 'vehicle_age']
    corr_matrix = df[corr_cols].corr()

    sns.heatmap(
        corr_matrix,
        annot=True,
        cmap='coolwarm',
        fmt=".2f",
        linewidths=1.0,
        linecolor='white',
        ax=ax,
        cbar_kws={'label': 'Pearson Correlation Coefficient'}
    )
    ax.set_title("6. Correlation Heatmap of Financial and Maintenance Attributes", pad=15, fontweight='bold')
    plt.tight_layout()
    chart6_path = os.path.join(OUTPUT_DIR, "6_correlation_heatmap.png")
    plt.savefig(chart6_path, dpi=300)
    plt.close()
    print(f"  [+] Chart 6 saved: {chart6_path}")

    print("\nVisualizations successfully exported to directory: " + OUTPUT_DIR)


def main():
    print("=" * 70)
    print(" VEHICLE SERVICE MANAGEMENT & MAINTENANCE ANALYSIS PIPELINE")
    print(" Department of Information Technology - VCET")
    print("=" * 70)
    
    df = load_dataset()
    df = preprocess_data(df)
    display_descriptive_statistics(df)
    generate_visualizations(df)
    print("\n[✔] Analysis Pipeline Finished Successfully!")

if __name__ == '__main__':
    main()
