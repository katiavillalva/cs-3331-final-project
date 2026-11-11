import java.util.List;
import java.util.Scanner;

/**
 * Main application class for the Miner Cars Dealership system.
 * Acts solely as the user interface controller.
 * 
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 * @version 2.1
 * @since November 10, 2026
 */
public class RunShop {
    private static Log logger = new Log("log.txt");
    private static CarDealership dealership = new CarDealership("Miner Cars");
    private static Menu mainMenu = new Menu(List.of("Register", "Login", "Exit"), dealership.getName().toUpperCase() + " - MAIN MENU (Type 'EXIT' to quit)");
    private static Menu adminMenu = new Menu(List.of("View Inventory", "Logout"), "ADMINISTRATOR MENU");
    private static Menu customerMenu = new Menu(List.of("Manage Profile", "Browse Cars (View, Filter, Purchase)", "Logout"), "CUSTOMER MENU");
    private static Menu browseCarsMenu = new Menu(List.of("Display All Available Cars", "Filter Cars", "Purchase a Car", "Back to Customer Menu"), "BROWSE CARS");
    private static Menu manageProfileMenu = new Menu(List.of("View Profile", "Update Profile (Coming soon)", "Back to Customer Menu"), "MANAGE PROFILE");


    /**
     * Main execution method. Initializes dealership data, runs the main application loop, 
     * and handles exit protocols.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try {
            System.out.println("Starting Miner Cars Dealership System...");
            
            dealership.loadUsers();
            dealership.loadCars();
            logger.writeLog("System", "Loaded inventory and users successfully.");

            Scanner scanner = new Scanner(System.in);
            boolean running = true;

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
     * Handles the user registration workflow by collecting input from the console 
     * and delegating the registration logic to the CarDealership class.
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

        // Delegating entirely to CarDealership, keeping RunShop as a pure UI controller
        boolean success = dealership.registerCustomer(firstName, lastName, username, password, money);

        if (success) {
            System.out.println("Registration successful! You can now log in.");
            logger.writeLog(username, "Registered new customer account.");
        } else {
            System.out.println("Registration failed: Username may already exist or error occurred.");
            logger.writeLog(username, "Failed registration attempt.");
        }
    }

    /**
     * Handles the user login workflow by validating credentials and routing 
     * the session to role-specific menus (Admin or Customer).
     * 
     * @param scanner active console scanner
     */
    private static void handleLogin(Scanner scanner) {
        System.out.println("\n--- SYSTEM LOGIN ---");
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        User authenticatedUser = dealership.getUsers().get(username);

        if (authenticatedUser == null || !authenticatedUser.getPassword().equals(password)) {
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
     * 
     * @param scanner active console scanner
     * @param admin   the currently logged-in Admin instance
     */
    private static void runAdminMenu(Scanner scanner, Admin admin) {
        boolean sessionActive = true;
        while (sessionActive) {
            adminMenu.displayOptions();
            String opt = scanner.nextLine().trim();

            if (opt.equals("2") || opt.equalsIgnoreCase("Logout")) {
                sessionActive = false;
                System.out.println("Logged out successfully.");
            } else if (opt.equals("1")) {
                dealership.displayAllCars();
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    /**
     * Menu interface for authenticated customers.
     * 
     * @param scanner  active console scanner
     * @param customer the currently logged-in Customer instance
     */
/**
     * Menu interface for authenticated customers, structuring options according to 
     * project requirements (Manage Profile, Browse Cars, Logout).
     * 
     * @param scanner  active console scanner
     * @param customer the currently logged-in Customer instance
     */
    private static void runCustomerMenu(Scanner scanner, Customer customer) {
        boolean sessionActive = true;
        while (sessionActive) {
            customerMenu.displayOptions();
            String opt = scanner.nextLine().trim();

            if (opt.equals("3") || opt.equalsIgnoreCase("Logout")) {
                sessionActive = false;
                System.out.println("Logged out successfully.");
                logger.writeLog(customer.getUsername(), "Logged out.");
            } else if (opt.equals("1")) {
                handleManageProfile(scanner, customer);
            } else if (opt.equals("2")) {
                runBrowseCarsMenu(scanner, customer);
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }
/**
     * Sub-menu interface for browsing cars, filtering, and making purchases 
     * (matching section 5.a.ii of the project requirements)[cite: 6, 7].
     * 
     * @param scanner  active console scanner
     * @param customer the currently logged-in Customer instance
     */
    private static void runBrowseCarsMenu(Scanner scanner, Customer customer) {
        boolean browsing = true;
        while (browsing) {
            browseCarsMenu.displayOptions();
            String opt = scanner.nextLine().trim();

            switch (opt) {
                case "1":
                    dealership.displayAvailableCars();
                    logger.writeLog(customer.getUsername(), "Viewed available cars inventory.");
                    break;
                case "2":
                    handleFilterCars(scanner);
                    logger.writeLog(customer.getUsername(), "Used advanced car filter.");
                    break;
                case "3":
                    handlePurchaseCar(scanner, customer);
                    break;
                case "4":
                    browsing = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Interactive sub-menu to collect parameters and invoke CarDealership's filterCars method.
     * 
     * @param scanner active console scanner
     */
    private static void handleFilterCars(Scanner scanner) {
        System.out.println("\n--- ADVANCED CAR FILTER ---");
        System.out.println("Tip: Enter -1 to ignore ID or Price. Leave blank to ignore Type or Condition.");

        System.out.print("Enter Car ID (-1 to ignore): ");
        int idIn = -1;
        try {
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) idIn = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            idIn = -1;
        }

        System.out.print("Enter Car Type (e.g., Sedan, SUV, Pickup, Hatchback or leave blank): ");
        String carTypeIn = scanner.nextLine().trim();
        if (carTypeIn.isEmpty()) carTypeIn = null;

        System.out.print("Enter Condition (e.g., New, Used or leave blank): ");
        String conditionIn = scanner.nextLine().trim();
        if (conditionIn.isEmpty()) conditionIn = null;

        System.out.print("Enter Target Price (-1 to ignore): ");
        double priceIn = -1.0;
        try {
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) priceIn = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            priceIn = -1.0;
        }

        dealership.filterCars(idIn, carTypeIn, conditionIn, priceIn);
    }

    /**
     * Handles the vehicle purchase workflow by interacting with the console,
     * validating car availability, and delegating the transaction to the 
     * Customer and CarDealership classes.
     * 
     * @param scanner  active console scanner
     * @param customer the currently logged-in Customer instance
     */
    private static void handlePurchaseCar(Scanner scanner, Customer customer) {
        System.out.println("\n--- PURCHASE A CAR ---");
        dealership.displayAvailableCars();
        
        System.out.print("\nEnter the ID of the car you wish to purchase (or 0 to cancel): ");
        int carId = -1;
        try {
            carId = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Purchase cancelled.");
            return;
        }

        if (carId == 0) {
            System.out.println("Purchase cancelled.");
            return;
        }

        Car targetCar = null;
        for (Car c : dealership.getInventory()) {
            if (c.getId() == carId) {
                targetCar = c;
                break;
            }
        }

        if (targetCar == null) {
            System.out.println("Error: Car with ID " + carId + " not found.");
            return;
        }

        if (targetCar.getCarsAvailable() <= 0) {
            System.out.println("Sorry, this car is currently out of stock.");
            return;
        }

        // Delegating transaction logic to the Customer class
        boolean success = customer.purchaseCar(targetCar);

        if (success) {
            // Updating dealership revenue and car inventory stock
            dealership.updateRevenue(targetCar.getPrice());
            targetCar.setCarsAvailable(targetCar.getCarsAvailable() - 1);

            System.out.println("\nCongratulations! Purchase successful!");
            System.out.println("You bought the " + targetCar.getModel() + ".");
            System.out.println("Remaining balance: $" + customer.getMoneyAvailable());

            logger.writeLog(customer.getUsername(), "Purchased car ID " + targetCar.getId());
        } else {
            System.out.println("Transaction failed: Insufficient funds.");
            logger.writeLog(customer.getUsername(), "Failed purchase attempt for car ID " + targetCar.getId() + " (Insufficient funds)");
        }
    }
/**
     * Manages the customer profile sub-menu (View Profile, Update Profile, Back).
     * 
     * @param scanner  active console scanner
     * @param customer the currently logged-in Customer instance
     * @param manageProfileMenu the menu instance for managing the profile
     */
    private static void handleManageProfile(Scanner scanner, Customer customer ) {
        boolean managing = true;
        while (managing) {
            manageProfileMenu.displayOptions();
            String opt = scanner.nextLine().trim();

            switch (opt) {
                case "1":
                    // Delegating profile display to the Customer class (UML compliance)
                    customer.displayProfile();
                    logger.writeLog(customer.getUsername(), "Viewed profile details.");
                    break;
                case "2":
                    System.out.println("Update profile feature will be implemented shortly.");
                    break;
                case "3":
                    managing = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    

}
