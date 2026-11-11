# cs-3331-final-project
Final project for CS-3331 Advanced Objected Oriented Programming, Fall 2026. 

Final project for CS-3331 Advanced Objected Oriented Programming, Fall 2026. 

# Miner Cars Dealership System

Console-based Object-Oriented Java application developed as a term project for **CS 3331 (Advanced Object-Oriented Programming)**. The system simulates a comprehensive car dealership management platform supporting multi-role authentication (Administrators and Customers), real-time inventory management, advanced searching/filtering, user profile management, secure purchasing workflows, and automated activity logging.

---

## 👥 Authors
* **Jean Jose Rendon Sifontes**
* **Katia Villalva**

---

## 🚀 Key Features

### 1. Multi-Role Authentication & User Management
* **Customer Role**: Register new accounts, deposit initial funds, browse inventory, filter vehicles, manage profile attributes, and purchase cars.
* **Administrator Role**: 
  * **Manage Users**: Add new users (Customers or Admins), view all registered members, update profile details (name, unique username validation, password), and delete user accounts with full data persistence.
  * **Manage Cars**: Add new vehicle types (`Sedan`, `SUV`, `Hatchback`, `Pickup`), view/search the complete inventory, update car prices or available stock, and delete vehicles.
  * **View Dealership Revenue**: Track financial earnings generated through vehicle sales.

### 2. Vehicle Inventory & Advanced Filtering
* Supports multiple vehicle categories with robust inheritance (`Sedan`, `SUV`, `Hatchback`, `Pickup`).
* Advanced multi-criteria search and filter options:
  * Filter by **ID**, **Car Type**, **Condition** (New/Used), and **Price Range ($\pm\$5,000$)**.
  * Dynamic stock management ensuring vehicles are only purchased if available.

### 3. Data Persistence & Logging
* **CSV-based Storage**: Automatically loads and synchronizes data from `user_data.csv` and `car_data.csv` upon startup, writing updated states back to disk in real-time and upon exit.
* **Activity Audit Log (`log.txt`)**: Records timestamped audit trails of all user actions (logins, inventory views, filters, purchases, and admin modifications) using a precise format (`HH:mm:ss MM/dd/yy`).

---

## 📂 Project Structure

* **`RunShop.java`**: Main user interface controller coordinating console input/output and session workflows.
* **`Menu.java`**: Dynamic menu generation and presentation utility.
* **`CarDealership.java`**: Core business logic controller managing inventory lists, user maps, revenue, and CSV input/output operations.
* **`User.java` / `Customer.java` / `Admin.java`**: Object-oriented hierarchy representing system users and role-specific behaviors.
* **`Car.java` / `Sedan.java` / `SUV.java` / `Hatchback.java` / `Pickup.java`**: Vehicle class hierarchy modeling vehicle attributes.
* **`Log.java`**: Timestamped logging utility managing `log.txt`.

---

## ⚙️ How to Compile and Run

Make sure you have **Java Development Kit (JDK)** installed on your system. Open your terminal or command prompt inside the project folder and follow these steps:

### 1. Compile all Java source files
```bash
javac *.java

2. Run the application
java RunShop

📋 Design Assumptions
ID Generation: New users are assigned sequential IDs starting from 1001, and new cars are assigned sequential IDs starting from 101 based on existing inventory size.
Username Uniqueness: Usernames act as primary keys in the system map and must remain strictly unique.
Data Persistence Timing: Data modifications are saved automatically to CSV files during administrative edits and upon typing EXIT in the main menu.
