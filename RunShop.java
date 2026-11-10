import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main application class for the Miner Cars Dealership system.
 * Handles program execution, data loading from CSVs, and main menu routing.
 * 
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 * @version 1.3
 * @since November 10, 2026
 */
public class RunShop {
    private static List<User> users = new ArrayList<>();
    private static List<Car> cars = new ArrayList<>();
    
    // Corregido: Se pasa el archivo de log requerido por el constructor de Log.java
    private static Log logger = new Log("log.txt"); 
    
    /**
     * Main execution method. Loads data from CSVs, runs the main application loop, 
     * and saves state on exit.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try {
            System.out.println("Starting Miner Cars Dealership System...");
            
            loadUsers();
            loadCars();

            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            List<String> mainOptions = List.of("Register", "Login", "Exit");
            Menu mainMenu = new Menu(mainOptions);

            while (running) {
                mainMenu.displayOptions();
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("EXIT") || input.equals("3")) {
                    running = false;
                    System.out.println("Exiting system. Thank you for visiting Miner Cars!");
                    logger.writeLog("System", "Exited application.");
                    break;
                }

                switch (input) {
                    case "1":
                        handleRegister(scanner);
                        break;
                    case "2":
                        handleLogin(scanner);
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
            scanner.close();
        } catch (Throwable t) {
            System.out.println("Ocurrió un error crítico al iniciar el sistema:");
            t.printStackTrace();
        }
    }

    /**
     * Handles the user registration workflow.
     * 
     * @param scanner active console scanner
     */
    private static void handleRegister(Scanner scanner) {
        System.out.println("\n--- NEW USER REGISTRATION ---");
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine().trim();
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();
        System.out.print("Enter initial money available ($): ");
        double money = 0.0;
        try {
            money = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            money = 0.0;
        }

        int newId = users.size() + 1001;
        Customer newCustomer = new Customer(firstName, lastName, newId, username, password);
        newCustomer.updateMoneyAvailable(money);
        newCustomer.setIsMember(false);

        users.add(newCustomer);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("user_data.csv", true))) {
            bw.write(newId + "," + firstName + "," + lastName + "," + money + ",," + false + "," + username + "," + password + ",Customer\n");
            System.out.println("Registration successful! You can now log in.");
            logger.writeLog(username, "Registered new customer account.");
        } catch (IOException e) {
            System.out.println("Warning: Could not save to user_data.csv file: " + e.getMessage());
        }
    }

    /**
     * Handles the user login workflow and routes to role-specific menus.
     * 
     * @param scanner active console scanner
     */
    private static void handleLogin(Scanner scanner) {
        System.out.println("\n--- SYSTEM LOGIN ---");
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        User authenticatedUser = null;
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                authenticatedUser = u;
                break;
            }
        }

        if (authenticatedUser == null) {
            System.out.println("Invalid username or password. Please try again.");
            logger.writeLog("Guest", "Failed login attempt for username: " + username);
            return;
        }

        System.out.println("\nLogin successful! Welcome back, " + authenticatedUser.getFirstName() + "!");
        logger.writeLog(username, "Successfully logged in.");

        if (authenticatedUser instanceof Admin) {
            runAdminMenu(scanner, (Admin) authenticatedUser);
        } else if (authenticatedUser instanceof Customer) {
            runCustomerMenu(scanner, (Customer) authenticatedUser);
        }
    }

    /**
     * Menu interface for authenticated administrators.
     */
    private static void runAdminMenu(Scanner scanner, Admin admin) {
        boolean sessionActive = true;
        while (sessionActive) {
            System.out.println("\n========================================");
            System.out.println("          ADMINISTRATOR MENU            ");
            System.out.println("========================================");
            System.out.println("1. View Dealership Inventory");
            System.out.println("2. Logout");
            System.out.println("========================================");
            System.out.print("Please select an option: ");
            String opt = scanner.nextLine().trim();

            if (opt.equals("2") || opt.equalsIgnoreCase("Logout")) {
                sessionActive = false;
                System.out.println("Logged out successfully.");
            } else if (opt.equals("1")) {
                System.out.println("\n[Inventory view feature active - total cars: " + cars.size() + "]");
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    /**
     * Menu interface for authenticated customers.
     */
    private static void runCustomerMenu(Scanner scanner, Customer customer) {
        boolean sessionActive = true;
        while (sessionActive) {
            System.out.println("\n========================================");
            System.out.println("             CUSTOMER MENU              ");
            System.out.println("========================================");
            System.out.println("1. View Available Cars");
            System.out.println("2. View My Account Details");
            System.out.println("3. Logout");
            System.out.println("========================================");
            System.out.print("Please select an option: ");
            String opt = scanner.nextLine().trim();

            if (opt.equals("3") || opt.equalsIgnoreCase("Logout")) {
                sessionActive = false;
                System.out.println("Logged out successfully.");
            } else if (opt.equals("1")) {
                System.out.println("\n--- CAR INVENTORY ---");
                for (Car c : cars) {
                    System.out.println("- " + c.getModel() + " ($" + c.getPrice() + ")");
                }
            } else if (opt.equals("2")) {
                System.out.println("\n--- ACCOUNT INFO ---");
                System.out.println("Name: " + customer.getFirstName() + " " + customer.getLastName());
                System.out.println("Balance: $" + customer.getMoneyAvailable());
                // Corregido: Se usa getIsMember() acorde al estándar de los métodos getters
                System.out.println("Member status: " + customer.getIsMember());
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    /**
     * Loads car inventory data from 'car_data.csv' with safe parsing for empty cells.
     */
    private static void loadCars() {
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
                            cars.add(car);
                        }
                    } catch (NumberFormatException e) {
                        // Skip malformed rows safely
                    }
                }
            }
            logger.writeLog("System", "Loaded cars inventory successfully from " + csvFile);
        } catch (Exception e) {
            System.out.println("Error reading car data: " + e.getMessage());
        }
    }

    /**
     * Loads user data from 'user_data.csv' with safe parsing for empty cells.
     */
    private static void loadUsers() {
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
                            users.add(admin);
                        } else if (userType.equalsIgnoreCase("Customer")) {
                            Customer customer = new Customer(firstName, lastName, userID, username, password);
                            customer.updateMoneyAvailable(moneyAvailable);
                            customer.setIsMember(isMember);
                            users.add(customer);
                        }
                    } catch (NumberFormatException e) {
                        // Skip malformed rows safely
                    }
                }
            }
            logger.writeLog("System", "Loaded users successfully from " + csvFile);
        } catch (Exception e) {
            System.out.println("Error reading user data: " + e.getMessage());
        }
    }
}
