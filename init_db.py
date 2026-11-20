"""
Database Initialization Script
Executes schema.sql and sample_data.sql to create vehicle_service.db
"""
import sqlite3
import os

DB_PATH = os.path.join(os.path.dirname(__file__), "vehicle_service.db")
SCHEMA_PATH = os.path.join(os.path.dirname(__file__), "schema.sql")
DATA_PATH = os.path.join(os.path.dirname(__file__), "sample_data.sql")

def initialize_database():
    print(f"Initializing database at: {DB_PATH}")
    if os.path.exists(DB_PATH):
        try:
            os.remove(DB_PATH)
            print("Removed existing database file.")
        except Exception as e:
            print(f"Note: Could not remove old DB file: {e}")

    conn = sqlite3.connect(DB_PATH)
    cursor = conn.cursor()

    with open(SCHEMA_PATH, "r", encoding="utf-8") as f:
        schema_sql = f.read()
    cursor.executescript(schema_sql)
    print("Schema applied successfully.")

    with open(DATA_PATH, "r", encoding="utf-8") as f:
        data_sql = f.read()
    cursor.executescript(data_sql)
    print("Sample data populated successfully.")

    # Verify counts
    tables = ['customers', 'vehicles', 'appointments', 'service_records', 'bills']
    print("\nVerification of table records:")
    for t in tables:
        cursor.execute(f"SELECT COUNT(*) FROM {t}")
        count = cursor.fetchone()[0]
        print(f"  - {t}: {count} records")

    conn.commit()
    conn.close()
    print("\nDatabase initialization complete!")

if __name__ == "__main__":
    initialize_database()
