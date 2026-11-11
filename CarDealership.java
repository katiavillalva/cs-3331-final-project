import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a car dealership managing inventory, users, and financial revenue.
 * Handles data loading from CSV files and advanced search operations.
 * 
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 * @version 2.1
 * @since November 10, 2026
 */
public class CarDealership {
    private String name;
    private ArrayList<Car> inventory = new ArrayList<>();
    private Map<String, User> users = new HashMap<>();
    private double revenue = 0.0;
    private final int filterPriceRange = 5000; // Price range for filtering cars 

    /**
     * Constructs a new CarDealership with the specified name. 
     * Initializes an empty inventory, an empty user map, and sets revenue to 0.0.
     * 
     * @param name the official name of the dealership
     */
    public CarDealership(String name) {
        this.name = name;
    }

    // ==================== Setters & Getters ====================

    /**
     * Sets the name of the dealership.
     * 
     * @param name the new name to set
     */
    public void setName(String name) { 
        this.name = name; 
    }

    /**
     * Returns the name of the dealership.
     * 
     * @return the dealership name
     */
    public String getName() { 
        return name; 
    }

    /**
     * Returns the list of cars in the inventory.
     * 
     * @return the ArrayList of Car objects
     */
    public ArrayList<Car> getInventory() { 
        return this.inventory; 
    }

    /**
     * Returns the map of users registered in the system.
     * 
     * @return the Map of users keyed by their username
     */
    public Map<String, User> getUsers() { 
        return this.users; 
    }

    /**
     * Returns the total revenue accumulated by the dealership.
     * 
     * @return the total revenue amount
     */
    public double getRevenue() { 
        return revenue; 
    }

    // ==================== Core Methods ====================

    /**
     * Adds a car to the dealership's inventory list. 
     * 
     * @param car the Car object to add
     */
    public void addCar(Car car) {
        this.inventory.add(car);
    }

    /**
     * Adds a user to the dealership's map of users using their username as the key. 
     * 
     * @param user the User object to add
     */
    public void addUser(User user) {
        this.users.put(user.getUsername(), user);
    }
    
    /**
     * Updates the dealership's revenue by adding the amount earned from car sale(s). 
     * 
     * @param amountIn the amount to add to the revenue
     * @return true if the update was successful, false if the amount is negative
     */
    public boolean updateRevenue(double amountIn) {
        if (amountIn < 0) {
            return false;
        }
        this.revenue += amountIn;
        return true;
    }

    /**
     * Displays the dealership name and the information of all cars in the inventory, regardless of availability. 
     */
    public void displayAllCars() {
        System.out.println(this.name + " Inventory:");
        for (Car car : this.inventory) {
            car.displayInformation();
        }
    }
 
    /**
     * Displays the dealership name and the information of all cars in the inventory that have at least one car available. 
     */
    public void displayAvailableCars() {
        System.out.println(this.name + " Available Cars:");
        for (Car car : this.inventory) {
            if (car.getCarsAvailable() > 0) {
                car.displayInformation();
            }
        }
    }

    /**
     * Displays the information of available cars that match the specified search criteria 
     * (ID, car type, condition, or a price range within +/- $5,000).
     * 
     * @param idIn        the car ID inputted by the user (-1 to ignore)
     * @param carTypeIn   the car type inputted by the user (null to ignore)
     * @param conditionIn the condition inputted by the user (null to ignore)
     * @param priceIn     the target price inputted by the user (-1 to ignore)
     */
    public void filterCars(int idIn, String carTypeIn, String conditionIn, double priceIn) {
        System.out.println("\n--- " + this.name.toUpperCase() + " FILTERED CARS ---");
        boolean found = false;
        for (Car car : this.inventory) {
            if ((idIn == -1 || car.getId() == idIn) &&
                (carTypeIn == null || car.getCarType().equalsIgnoreCase(carTypeIn)) &&
                (conditionIn == null || car.getCondition().equalsIgnoreCase(conditionIn)) &&
                (priceIn == -1 || (car.getPrice() <= priceIn + filterPriceRange && car.getPrice() >= priceIn - filterPriceRange)) &&
                car.getCarsAvailable() > 0) {
                car.displayInformation();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No cars found matching the specified criteria.");
        }
    }

    /**
     * Loads car inventory data from 'car_data.csv' with safe parsing for empty cells.
     */
    public void loadCars() {
        String csvFile = "car_data.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] data = line.split(",", -1);
                if (data.length >= 12) {
                    try {
                        int id = data[0].trim().isEmpty() ? 0 : Integer.parseInt(data[0].trim());
                        String carType = data[1].trim();
                        String model = data[2].trim();
                        String condition = data[3].trim();
                        String color = data[4].trim();
                        int capacity = data[5].trim().isEmpty() ? 0 : Integer.parseInt(data[5].trim());
                        int mileage = data[6].trim().isEmpty() ? 0 : Integer.parseInt(data[6].trim());
                        String fuelType = data[7].trim();
                        String transmission = data[8].trim();
                        String VIN = data[9].trim();
                        double price = data[10].trim().isEmpty() ? 0.0 : Double.parseDouble(data[10].trim());
                        int carsAvailable = data[11].trim().isEmpty() ? 0 : Integer.parseInt(data[11].trim());

                        Car car = null;
                        if (carType.equalsIgnoreCase("Sedan")) {
                            car = new Sedan(id, model, condition, price, color, capacity, mileage, transmission, fuelType, VIN, carsAvailable);
                        } else if (carType.equalsIgnoreCase("SUV")) {
                            car = new SUV(id, model, condition, price, color, capacity, mileage, transmission, fuelType, VIN, carsAvailable);
                        } else if (carType.equalsIgnoreCase("Hatchback")) {
                            car = new Hatchback(id, model, condition, price, color, capacity, mileage, transmission, fuelType, VIN, carsAvailable);
                        } else if (carType.equalsIgnoreCase("Pickup")) {
                            car = new Pickup(id, model, condition, price, color, capacity, mileage, transmission, fuelType, VIN, carsAvailable);
                        }

                        if (car != null) {
                            this.inventory.add(car);
                        }
                    } catch (NumberFormatException e) {
                        // Skip malformed rows safely
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading car data: " + e.getMessage());
        }
    }

    /**
     * Loads user data from 'user_data.csv' with safe parsing for empty cells.
     */
    public void loadUsers() {
        String csvFile = "user_data.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                
                String[] data = line.split(",", -1);
                if (data.length >= 9) {
                    try {
                        int userID = data[0].trim().isEmpty() ? 0 : Integer.parseInt(data[0].trim());
                        String firstName = data[1].trim();
                        String lastName = data[2].trim();
                        double moneyAvailable = data[3].trim().isEmpty() ? 0.0 : Double.parseDouble(data[3].trim());
                        boolean isMember = !data[5].trim().isEmpty() && Boolean.parseBoolean(data[5].trim());
                        String username = data[6].trim();
                        String password = data[7].trim();
                        String userType = data[8].trim();

                        if (userType.equalsIgnoreCase("Admin")) {
                            Admin admin = new Admin(firstName, lastName, userID, username, password);
                            addUser(admin);
                        } else if (userType.equalsIgnoreCase("Customer")) {
                            Customer customer = new Customer(firstName, lastName, userID, username, password);
                            customer.updateMoneyAvailable(moneyAvailable);
                            customer.setIsMember(isMember);
                            addUser(customer);
                        }
                    } catch (NumberFormatException e) {
                        // Skip malformed rows safely
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading user data: " + e.getMessage());
        }
    }

    /**
     * Registers a new customer by generating a unique ID, initializing their account attributes,
     * adding them to the user map, and persisting the record to 'user_data.csv'.
     * 
     * @param firstName      the customer's first name
     * @param lastName       the customer's last name
     * @param username       the unique username for login
     * @param password       the account password
     * @param moneyAvailable the initial money deposit
     * @return true if registration was successful, false if username already exists or error occurs
     */
    public boolean registerCustomer(String firstName, String lastName, String username, String password, double moneyAvailable) {
        if (this.users.containsKey(username)) {
            return false;
        }

        int newId = this.users.size() + 1001;
        Customer newCustomer = new Customer(firstName, lastName, newId, username, password);
        newCustomer.updateMoneyAvailable(moneyAvailable);
        newCustomer.setIsMember(false);

        addUser(newCustomer);

        try (java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter("user_data.csv", true))) {
            bw.write(newId + "," + firstName + "," + lastName + "," + moneyAvailable + ",," + false + "," + username + "," + password + ",Customer\n");
            return true;
        } catch (java.io.IOException e) {
            System.out.println("Warning: Could not save to user_data.csv file: " + e.getMessage());
            return false;
        }
    }
}
