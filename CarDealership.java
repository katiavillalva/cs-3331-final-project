import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Locale;

/**
 * Represents a car dealership managing inventory, users, and financial revenue.
 * Handles data loading from CSV files, user/car management, and search operations.
 * 
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 * @version 3.0
 * @since November 17, 2026
 */
public class CarDealership {
    /** The business name of the car dealership */
    private String name;

    /** The list containing all cars in the dealership's inventory */
    private ArrayList<Car> inventory = new ArrayList<>();

    /** Map of Users by their unique username */
    private Map<String, User> users = new HashMap<>();

    /** Total revenue made from car */
    private double revenue = 0.0;

    /** Car factory object which create Car subclass objects  */
    private CarFactory carFactory = new CarFactory();

    /** Car factory object which create Car subclass objects  */
    private UserFactory userFactory = new UserFactory();

    /**
     * Constructs a new CarDealership with the specified name. 
     * Initializes an empty inventory, an empty user map, and sets revenue to 0.0.
     * 
     * @param name the official name of the dealership
     */
    public CarDealership(String name) {
        this.name = name;
    }

    // ==================== Setters ====================

    /**
     * Sets the name of the dealership.
     * 
     * @param name the new name to set
     */
    public void setName(String name) { 
        this.name = name; 
    }

    // ==================== Getters ====================
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

    // ================================================

    /**
     * Adds a car to the dealership's inventory list. 
     * @param car the Car object to add
     */
    public void addCar(Car car) {
        this.inventory.add(car);
    }

    /**
     * Adds a user to the dealership's map of users using their username as the key. 
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
     * Displays the information of each car in the given list.
     * @param cars the list of cars to display
     */
    public void displayCars(List<Car> cars) {
        System.out.println(Car.HEADER);
        for (Car car : cars) {
            System.out.println(car.toString());
        }
    }

    /**
     * Displays the dealership name and the information of all cars in the inventory, regardless of availability. 
     */
    public void displayAllCars() {
        System.out.println(this.name + " Inventory:");
        displayCars(this.inventory);
    }
 
    /**
     * Displays the dealership name and the information of all cars in the inventory that have at least one car available. 
     */
    public void displayAvailableCars() {
        System.out.println(this.name + " Available Cars:");
        for (Car car : this.inventory) {
            if (car.getCarsAvailable() > 0) {
                System.out.println(car.toString());;
            }
        }
    }

   /** Filters the cars in the inventory by the inpututed ID, type, condition, or price within a range
    * @param idIn         the car ID 
    * @param carTypeIn    the car type 
    * @param conditionIn  the condition 
    * @param priceIn      the target price 
    * @param priceRangeIn the amount to add and subtract from the price to get the price range
    * @return             the list of Cars that were found with the criteria (empty if no cars were found)
    */
    public List<Car> filterCars(int idIn, String carTypeIn, String conditionIn, double priceIn, double priceRangeIn) {
        List<Car> result = new ArrayList<>();
        for (Car car : this.inventory) {
            if ((idIn == 0 || car.getId() == idIn) &&
                (carTypeIn == null || car.getCarType().equalsIgnoreCase(carTypeIn)) &&
                (conditionIn == null || car.getCondition().equalsIgnoreCase(conditionIn)) &&
                (priceIn == 0 || (car.getPrice() >= priceIn - priceRangeIn && car.getPrice() <= priceIn + priceRangeIn)) &&
                (car.getCarsAvailable() > 0)){
                    result.add(car);
            }
        }
        return result;
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

                        Car car = carFactory.createCar(id, carType, model, condition, price, color, capacity, mileage, transmission, fuelType, VIN, carsAvailable);
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

                        User user = userFactory.createUser(userID, firstName, lastName, username, password, userType, moneyAvailable, isMember);
                        if (user != null) {
                            addUser(user);
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

    /**
     * Registers a new user (Customer or Admin) by generating a unique ID, initializing their attributes,
     * adding them to the user map, and writing the user information to 'user_data.csv'.
     * 
     * @param firstName      the user's first name
     * @param lastName       the user's last name
     * @param username       the unique username for login
     * @param password       the account password
     * @param userType       the type of user ("Customer" or "Admin")
     * @param moneyAvailable initial deposit (applicable only for Customers)
     * @return true if registration was successful, false if username already exists or error occurs
     */
    public boolean registerUser(String firstName, String lastName, String username, String password, String userType, double moneyAvailable, boolean isMember) {
        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() || moneyAvailable < 0) {
            return false;
        } if (this.users.containsKey(username)) {
            return false;
        }
        int newId = 0;
        for (User u : this.users.values()) newId = Math.max(newId, u.getUserID() + 1);  // Get a unique user ID

        User newUser = userFactory.createUser(newId, firstName, lastName, username, password, userType, moneyAvailable, isMember);
        if (newUser == null) {
            return false;   // unrecognized user type
        }
        addUser(newUser);
        return saveUsersToCSV();
    }

    /**
     * Displays all registered users in the dealership's user map.
     */
    public void displayAllMembers() {
        if (this.users.isEmpty()) {
            System.out.println("No users registered in the system.");
            return;
        }
        System.out.println(User.HEADER);
        for (User user : this.users.values()) {
            System.out.println(user.toString());
        }
    }

    /**
     * Searches for a member by ID, name (first or last), or username.
     * Displays their profile if found, or an informative message otherwise.
     * 
     * @param searchKey the keyword or identifier entered by the admin
     */
    public void searchMember(String searchKey) {
        System.out.println("\n--- MEMBER SEARCH RESULTS ---");
        boolean found = false;
        String query = searchKey.trim().toLowerCase();

        for (User user : this.users.values()) {
            boolean matchesId = false;
            try {
                int idQuery = Integer.parseInt(query);
                matchesId = (user.getUserID() == idQuery);
            } catch (NumberFormatException e) {
                // Not a numeric ID
            }

            boolean matchesUsername = user.getUsername().toLowerCase().contains(query);
            boolean matchesName = user.getFirstName().toLowerCase().contains(query) || 
                                  user.getLastName().toLowerCase().contains(query);

            if (matchesId || matchesUsername || matchesName) {
                System.out.println(user.toString());
                found = true;
            }
        }

        if (!found) {
            System.out.println("Information: The member matching '" + searchKey + "' was not found in the system.");
        }
    }

    /**
     * Finds and returns a registered User by ID, name (first or last), or username.
     * 
     * @param searchKey the keyword or identifier entered by the admin
     * @return the User object if found, or null otherwise
     */
    public User findMember(String searchKey) {
        String query = searchKey.trim().toLowerCase();

        for (User user : this.users.values()) {
            boolean matchesId = false;
            try {
                int idQuery = Integer.parseInt(query);
                matchesId = (user.getUserID() == idQuery);
            } catch (NumberFormatException e) {
                // Not a numeric ID
            }

            boolean matchesUsername = user.getUsername().toLowerCase().equals(query);
            boolean matchesName = user.getFirstName().toLowerCase().equals(query) || 
                                  user.getLastName().toLowerCase().equals(query);

            if (matchesId || matchesUsername || matchesName) {
                return user;
            }
        }
        return null;
    }

    /**
     * Deletes a registered user from the dealership's map and updates 'user_data.csv'.
     * 
     * @param user the User object to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteUser(User user) {
        if (user == null || !this.users.containsKey(user.getUsername())) {
            return false;
        }
        this.users.remove(user.getUsername());
        return saveUsersToCSV();
    }

    /**
     * Helper method to rewrite 'user_data.csv' with the current state of the users map.
     * Declared public to allow direct access from UI controllers.
     * 
     * @return true if save was successful, false if an I/O error occurs
     */
    public boolean saveUsersToCSV() {
        String csvFile = "user_data.csv";
        try (java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(csvFile, false))) {
            bw.write("UserID,FirstName,LastName,MoneyAvailable,,isMember,Username,Password,UserType\n");

            for (User user : this.users.values()) {
                String line = "";
                if (user instanceof Admin) {
                    Admin admin = (Admin) user;
                    line = String.format("%d,%s,%s,,,,%s,%s,Admin\n", 
                        admin.getUserID(), admin.getFirstName(), admin.getLastName(), admin.getUsername(), admin.getPassword());
                } else if (user instanceof Customer) {
                    Customer cust = (Customer) user;
                    line = String.format(Locale.US, "%d,%s,%s,%.2f,,%b,%s,%s,Customer\n", 
                        cust.getUserID(), cust.getFirstName(), cust.getLastName(), 
                        cust.getMoneyAvailable(), cust.getIsMember(), cust.getUsername(), cust.getPassword());
                }
                bw.write(line);
            }
            return true;
        } catch (java.io.IOException e) {
            System.out.println("Warning: Could not update user_data.csv file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Adds a new car to the inventory by generating a unique ID, instantiating the correct subclass,
     * adding it to the inventory list, and appending it to 'car_data.csv'.
     * 
     * @param carType       type of car (Sedan, SUV, Hatchback, Pickup)
     * @param model         model name
     * @param condition     condition (New, Used)
     * @param color         color
     * @param capacity      seating capacity
     * @param mileage       mileage in miles
     * @param fuelType      fuel type
     * @param transmission  transmission type
     * @param VIN           Vehicle Identification Number
     * @param price         car price
     * @param carsAvailable available stock count
     * @return true if successfully added, false otherwise
     */
    public boolean addCarToInventory(String carType, String model, String condition, String color, 
                                     int capacity, int mileage, String fuelType, String transmission, 
                                     String VIN, double price, int carsAvailable) {
        int newId = 101;
        for (Car c : this.inventory) {
            if (c.getId() >= newId) {
                newId = c.getId() + 1;
            }
        }

        Car newCar = carFactory.createCar(newId, carType, model, condition, price, color, capacity, mileage, transmission, fuelType, VIN, carsAvailable);
        if (newCar == null) {
            return false;
        }
        this.inventory.add(newCar);
        return saveCarsToCSV();
    }

    /**
     * Finds and returns a Car from inventory matching an ID, car type, or model.
     * 
     * @param searchKey keyword or identifier entered by the admin
     * @return the matching Car object, or null if not found
     */
    public Car findCar(String searchKey) {
        String query = searchKey.trim().toLowerCase();

        for (Car car : this.inventory) {
            boolean matchesId = false;
            try {
                int idQuery = Integer.parseInt(query);
                matchesId = (car.getId() == idQuery);
            } catch (NumberFormatException e) {
                // Not numeric
            }

            boolean matchesType = car.getCarType().toLowerCase().contains(query);
            boolean matchesModel = car.getModel().toLowerCase().contains(query);

            if (matchesId || matchesType || matchesModel) {
                return car;
            }
        }
        return null;
    }

    /**
     * Searches and displays car information matching ID, car type, or model.
     * 
     * @param searchKey keyword or identifier
     */
    public void searchCar(String searchKey) {
        System.out.println("\n--- CAR SEARCH RESULTS ---");
        boolean found = false;
        String query = searchKey.trim().toLowerCase();

        for (Car car : this.inventory) {
            boolean matchesId = false;
            try {
                int idQuery = Integer.parseInt(query);
                matchesId = (car.getId() == idQuery);
            } catch (NumberFormatException e) {
                // Not numeric
            }

            boolean matchesType = car.getCarType().toLowerCase().contains(query);
            boolean matchesModel = car.getModel().toLowerCase().contains(query);

            if (matchesId || matchesType || matchesModel) {
                System.out.println(car.toString());;
                found = true;
            }
        }

        if (!found) {
            System.out.println("Information: The car matching '" + searchKey + "' was not found in the inventory.");
        }
    }

    /**
     * Deletes a car from inventory and updates 'car_data.csv'.
     * 
     * @param car the Car object to delete
     * @return true if deleted successfully, false otherwise
     */
    public boolean deleteCar(Car car) {
        if (car == null || !this.inventory.contains(car)) {
            return false;
        }
        this.inventory.remove(car);
        return saveCarsToCSV();
    }

    /**
     * Helper method to rewrite 'car_data.csv' with the current state of the inventory list.
     * Declared public to allow direct access from UI controllers.
     * 
     * @return true if successful, false otherwise
     */
    public boolean saveCarsToCSV() {
        String csvFile = "car_data.csv";
        try (java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(csvFile, false))) {
            bw.write("ID,CarType,Model,Condition,Color,Capacity,Mileage,FuelType,Transmission,VIN,Price,CarsAvailable\n");

            for (Car car : this.inventory) {
                String line = String.format(Locale.US, "%d,%s,%s,%s,%s,%d,%d,%s,%s,%s,%.2f,%d\n",
                    car.getId(),
                    car.getCarType(),
                    car.getModel(),
                    car.getCondition(),
                    car.getColor(),
                    car.getCapacity(),
                    car.getMileage(),
                    car.getFuelType(),
                    car.getTransmission(),
                    car.getVIN(),
                    car.getPrice(),
                    car.getCarsAvailable()
                );
                bw.write(line);
            }
            return true;
        } catch (java.io.IOException e) {
            System.out.println("Warning: Could not update car_data.csv file: " + e.getMessage());
            return false;
        }
    }
}
