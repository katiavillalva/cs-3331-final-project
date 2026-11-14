# cs-3331-final-project
Final project for CS-3331 Advanced Objected Oriented Programming, Fall 2026. 

# Miner Cars Dealership System

Console-based Object-Oriented Java application developed as a term project for **CS 3331 (Advanced Object-Oriented Programming)**. The system simulates a comprehensive car dealership management platform supporting multi-role authentication (Administrators and Customers), real-time inventory management, advanced searching/filtering, user profile management, secure purchasing workflows, and automated activity logging.

---

## Authors
* **Jean Jose Rendon Sifontes**
* **Katia Villalva**

---

## Key Features

### 1. Role-specific functions
* **Customer**: Register new accounts, deposit initial funds, browse inventory, filter vehicles, manage profile attributes, and purchase cars.
* **Administrator**: 
  * **Manage Users**: Add new users (Customers or Admins), view all registered members, update profile details (name, unique username validation, password), and delete user accounts.
  * **Manage Cars**: Add new vehicle types (`Sedan`, `SUV`, `Hatchback`, `Pickup`), view/search the complete inventory, update car prices or available stock, and delete vehicles.
  * **View Dealership Revenue**: Track earnings generated through vehicle sales.

### 2. Vehicle Inventory & Filtering
* Vehicle subclasses are referenced by the Car supertype to store all cars in one inventory list. The inventory can be filtered by multiple criteria:  
  * Filter by **ID**, **Car Type**, **Condition** (New/Used), and **Price Range ($\pm\$5,000$)**.
  * Dynamic stock management ensuring vehicles are only purchased if available and car counts are updated after a purchase is made. 

### 3. Data Persistence & Logging
* **CSV-based Storage**: Automatically loads and synchronizes data from `user_data.csv` and `car_data.csv` upon startup, writing updated states back to disk in real-time and upon exit.
* **Activity Audit Log (`log.txt`)**: Records timestamped audit trails of all user actions (logins, inventory views, filters, purchases, and admin modifications) using a precise format (`HH:mm:ss MM/dd/yy`).

---

## Class Structure

* **`RunShop.java`**: Main user interface controller coordinating console input/output and session flow. Handles registering, logging in, and Customer/Admin specific functions. 
* **`Menu.java`**: Constructs menus with options and implements displaying functionality.
* **`CarDealership.java`**: Constructs and modifies dealership objects with features such as name, list of Users, and revenue.
* **`User.java**: Abstract superclass modeling a general user in the system. Has Admin and Customer as concrete subclasses. 
* **`Car.java**: Abstract superclass modeling a car object with attributes such as make, color, VIN, and price. 
* **`Log.java`**: Timestamped logger storing writing user actions in a .txt file. 

---

## How to Compile and Run

Make sure you have **Java Development Kit (JDK)** installed on your system. Open your terminal or command prompt inside the project folder and follow these steps:

### 1. Compile all Java source files
```bash
javac *.java

2. Run the application
java RunShop

