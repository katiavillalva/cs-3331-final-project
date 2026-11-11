import java.util.List;
import java.util.Scanner;

/**
 * Main application class for the Miner Cars Dealership system.
 * Acts solely as the user interface controller using dynamic Menu objects.
 * 
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 * @version 2.2
 * @since November 10, 2026
 */
public class RunShop {
    private static Log logger = new Log("log.txt");
    private static CarDealership dealership = new CarDealership("Miner Cars");
    
// Dynamic Menu declarations (passing only the list of options as required by Menu class)
    private static Menu mainMenu = new Menu(List.of("Register", "Login", "Exit"));
    
    private static Menu adminMenu = new Menu(List.of("Manage Users", "Manage Cars", "View Dealership Inventory", "Logout"));
    private static Menu manageUsersMenu = new Menu(List.of("Add New User", "View Members", "Update Member", "Delete Member", "Back to Admin Menu"));
    private static Menu viewMembersMenu = new Menu(List.of("Display All Members", "Search Member", "Back to Manage Users"));
    
    private static Menu manageCarsMenu = new Menu(List.of("Add New Car", "View Cars", "Update Car", "Delete Car", "Back to Admin Menu"));
    private static Menu viewCarsMenu = new Menu(List.of("Display All Cars", "Search Car", "Back to Manage Cars"));

    private static Menu customerMenu = new Menu(List.of("Manage Profile", "Browse Cars (View, Filter, Purchase)", "Logout"));
    private static Menu browseCarsMenu = new Menu(List.of("Display All Available Cars", "Filter Cars", "Purchase a Car", "Back to Customer Menu"));
    private static Menu manageProfileMenu = new Menu(List.of("View Profile", "Update Profile", "Back to Customer Menu"));

    /**
     * Main execution method. Initializes dealership data, runs the main application loop, 
     * and handles exit protocols and data persistence.
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
                    System.out.println("Saving system data...");
                    dealership.saveUsersToCSV();
                    dealership.saveCarsToCSV();
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
            System.out.println("An unexpected error occurred in the system:");
            t.printStackTrace();
        }
    }

    /**
     * Handles the user registration workflow.
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
     * Handles the user login workflow and role-based menu routing.
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

    // ==================== ADMINISTRATOR MENUS & WORKFLOWS ====================

    private static void runAdminMenu(Scanner scanner, Admin admin) {
        boolean sessionActive = true;
        while (sessionActive) {
            adminMenu.displayOptions();
            String opt = scanner.nextLine().trim();

            switch (opt) {
                case "1":
                    runManageUsersMenu(scanner, admin);
                    break;
                case "2":
                    runManageCarsMenu(scanner, admin);
                    break;
                case "3":
                    dealership.displayAllCars();
                    logger.writeLog(admin.getUsername(), "Viewed dealership inventory.");
                    break;
                case "4":
                case "Logout":
                    sessionActive = false;
                    System.out.println("Logged out successfully.");
                    logger.writeLog(admin.getUsername(), "Admin logged out.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void runManageUsersMenu(Scanner scanner, Admin admin) {
        boolean managing = true;
        while (managing) {
            manageUsersMenu.displayOptions();
            String opt = scanner.nextLine().trim();

            switch (opt) {
                case "1":
                    handleAdminAddUser(scanner, admin);
                    break;
                case "2":
                    runViewMembersMenu(scanner, admin);
                    break;
                case "3":
                    handleAdminUpdateMember(scanner, admin);
                    break;
                case "4":
                    handleAdminDeleteMember(scanner, admin);
                    break;
                case "5":
                    managing = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void runViewMembersMenu(Scanner scanner, Admin admin) {
        boolean viewing = true;
        while (viewing) {
            viewMembersMenu.displayOptions();
            String opt = scanner.nextLine().trim();

            switch (opt) {
                case "1":
                    dealership.displayAllMembers();
                    break;
                case "2":
                    System.out.print("Enter ID, Name, or Username to search: ");
                    String query = scanner.nextLine().trim();
                    if (!query.isEmpty()) dealership.searchMember(query);
                    break;
                case "3":
                    viewing = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void handleAdminAddUser(Scanner scanner, Admin admin) {
        System.out.println("\n--- ADD NEW USER ---");
        System.out.print("Enter user type (Customer / Admin): ");
        String userType = scanner.nextLine().trim();
        if (!userType.equalsIgnoreCase("Customer") && !userType.equalsIgnoreCase("Admin")) {
            System.out.println("Invalid user type.");
            return;
        }

        System.out.print("Enter first name: ");
        String fn = scanner.nextLine().trim();
        System.out.print("Enter last name: ");
        String ln = scanner.nextLine().trim();
        System.out.print("Enter username: ");
        String un = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String pw = scanner.nextLine().trim();

        double money = 0.0;
        if (userType.equalsIgnoreCase("Customer")) {
            System.out.print("Enter initial money available ($): ");
            try { money = Double.parseDouble(scanner.nextLine().trim()); } catch (Exception e) {}
        }

        boolean success = dealership.registerUser(fn, ln, un, pw, userType, money);
        if (success) {
            System.out.println("New " + userType + " added successfully!");
            logger.writeLog(admin.getUsername(), "Added new " + userType + ": " + un);
        } else {
            System.out.println("Failed to add user (Username may exist).");
        }
    }

    private static void handleAdminUpdateMember(Scanner scanner, Admin admin) {
        System.out.println("\n--- UPDATE MEMBER ---");
        System.out.print("Enter ID, Name, or Username to update: ");
        String query = scanner.nextLine().trim();
        User target = dealership.findMember(query);
        if (target == null) {
            System.out.println("Member not found.");
            return;
        }

        System.out.println("1. Change Name\n2. Change Username\n3. Change Password\nChoice: ");
        String choice = scanner.nextLine().trim();
        if (choice.equals("1")) {
            System.out.print("New First Name: "); String fn = scanner.nextLine().trim();
            System.out.print("New Last Name: "); String ln = scanner.nextLine().trim();
            target.setFirstName(fn); target.setLastName(ln);
            System.out.println("Name updated.");
        } else if (choice.equals("2")) {
            System.out.print("New Username: "); String newUn = scanner.nextLine().trim();
            try {
                String oldUn = target.getUsername();
                target.setUsername(dealership.getUsers().keySet(), newUn);
                dealership.getUsers().remove(oldUn);
                dealership.getUsers().put(newUn, target);
                System.out.println("Username updated.");
            } catch (Exception e) { System.out.println(e.getMessage()); }
        } else if (choice.equals("3")) {
            System.out.print("New Password: "); String pw = scanner.nextLine().trim();
            target.setPassword(pw);
            System.out.println("Password updated.");
        }
        dealership.saveUsersToCSV();
    }

    private static void handleAdminDeleteMember(Scanner scanner, Admin admin) {
        System.out.println("\n--- DELETE MEMBER ---");
        System.out.print("Enter ID, Name, or Username to delete: ");
        User target = dealership.findMember(scanner.nextLine().trim());
        if (target == null) { System.out.println("Member not found."); return; }
        
        target.displayProfile();
        System.out.print("Confirm deletion? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            dealership.deleteUser(target);
            System.out.println("Member deleted.");
        }
    }

    // ==================== MANAGE CARS WORKFLOWS ====================

    private static void runManageCarsMenu(Scanner scanner, Admin admin) {
        boolean managing = true;
        while (managing) {
            manageCarsMenu.displayOptions();
            String opt = scanner.nextLine().trim();

            switch (opt) {
                case "1":
                    handleAdminAddCar(scanner, admin);
                    break;
                case "2":
                    runViewCarsMenu(scanner, admin);
                    break;
                case "3":
                    handleAdminUpdateCar(scanner, admin);
                    break;
                case "4":
                    handleAdminDeleteCar(scanner, admin);
                    break;
                case "5":
                    managing = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void runViewCarsMenu(Scanner scanner, Admin admin) {
        boolean viewing = true;
        while (viewing) {
            viewCarsMenu.displayOptions();
            String opt = scanner.nextLine().trim();
            if (opt.equals("1")) dealership.displayAllCars();
            else if (opt.equals("2")) {
                System.out.print("Enter ID, Type, or Model: ");
                dealership.searchCar(scanner.nextLine().trim());
            } else if (opt.equals("3")) viewing = false;
        }
    }

    private static void handleAdminAddCar(Scanner scanner, Admin admin) {
        System.out.println("\n--- ADD NEW CAR ---");
        System.out.print("Car Type (Sedan, SUV, Hatchback, Pickup): "); String type = scanner.nextLine().trim();
        System.out.print("Model: "); String model = scanner.nextLine().trim();
        System.out.print("Condition (New, Used): "); String cond = scanner.nextLine().trim();
        System.out.print("Color: "); String color = scanner.nextLine().trim();
        System.out.print("Capacity: "); int cap = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Mileage: "); int mil = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Fuel Type: "); String fuel = scanner.nextLine().trim();
        System.out.print("Transmission: "); String trans = scanner.nextLine().trim();
        System.out.print("VIN: "); String vin = scanner.nextLine().trim();
        System.out.print("Price ($): "); double price = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Cars Available: "); int stock = Integer.parseInt(scanner.nextLine().trim());

        dealership.addCarToInventory(type, model, cond, color, cap, mil, fuel, trans, vin, price, stock);
        System.out.println("Car added successfully!");
    }

    private static void handleAdminUpdateCar(Scanner scanner, Admin admin) {
        System.out.print("Enter ID, Type, or Model to update: ");
        Car car = dealership.findCar(scanner.nextLine().trim());
        if (car == null) { System.out.println("Car not found."); return; }

        System.out.println("1. Update Price\n2. Update Stock\nChoice: ");
        String opt = scanner.nextLine().trim();
        if (opt.equals("1")) {
            System.out.print("New Price: "); car.setPrice(Double.parseDouble(scanner.nextLine().trim()));
        } else if (opt.equals("2")) {
            System.out.print("New Stock: "); car.setCarsAvailable(Integer.parseInt(scanner.nextLine().trim()));
        }
        dealership.saveCarsToCSV();
        System.out.println("Car updated successfully!");
    }

    private static void handleAdminDeleteCar(Scanner scanner, Admin admin) {
        System.out.print("Enter ID, Type, or Model to delete: ");
        Car car = dealership.findCar(scanner.nextLine().trim());
        if (car == null) { System.out.println("Car not found."); return; }
        
        car.displayInformation();
        System.out.print("Confirm deletion? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            dealership.deleteCar(car);
            System.out.println("Car deleted.");
        }
    }

    // ==================== CUSTOMER MENUS & WORKFLOWS ====================

    private static void runCustomerMenu(Scanner scanner, Customer customer) {
        boolean sessionActive = true;
        while (sessionActive) {
            customerMenu.displayOptions();
            String opt = scanner.nextLine().trim();

            switch (opt) {
                case "1":
                    handleManageProfile(scanner, customer);
                    break;
                case "2":
                    runBrowseCarsMenu(scanner, customer);
                    break;
                case "3":
                case "Logout":
                    sessionActive = false;
                    System.out.println("Logged out successfully.");
                    logger.writeLog(customer.getUsername(), "Logged out.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

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

    private static void handleFilterCars(Scanner scanner) {
        System.out.println("\n--- ADVANCED CAR FILTER ---");
        System.out.println("Tip: Enter -1 to ignore ID or Price. Leave blank to ignore Type or Condition.");

        System.out.print("Enter Car ID (-1 to ignore): ");
        int idIn = -1;
        try {
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) idIn = Integer.parseInt(input);
        } catch (NumberFormatException e) { idIn = -1; }

        System.out.print("Enter Car Type (Sedan, SUV, Pickup, Hatchback or blank): ");
        String carTypeIn = scanner.nextLine().trim();
        if (carTypeIn.isEmpty()) carTypeIn = null;

        System.out.print("Enter Condition (New, Used or blank): ");
        String conditionIn = scanner.nextLine().trim();
        if (conditionIn.isEmpty()) conditionIn = null;

        System.out.print("Enter Target Price (-1 to ignore): ");
        double priceIn = -1.0;
        try {
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) priceIn = Double.parseDouble(input);
        } catch (NumberFormatException e) { priceIn = -1.0; }

        dealership.filterCars(idIn, carTypeIn, conditionIn, priceIn);
    }

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

        if (carId == 0) return;

        Car targetCar = null;
        for (Car c : dealership.getInventory()) {
            if (c.getId() == carId) { targetCar = c; break; }
        }

        if (targetCar == null || targetCar.getCarsAvailable() <= 0) {
            System.out.println("Error: Car not found or out of stock.");
            return;
        }

        boolean success = customer.purchaseCar(targetCar);
        if (success) {
            dealership.updateRevenue(targetCar.getPrice());
            targetCar.setCarsAvailable(targetCar.getCarsAvailable() - 1);
            dealership.saveCarsToCSV();

            System.out.println("\nCongratulations! Purchase successful!");
            System.out.println("You bought the " + targetCar.getModel() + ".");
            System.out.println("Remaining balance: $" + customer.getMoneyAvailable());
            logger.writeLog(customer.getUsername(), "Purchased car ID " + targetCar.getId());
        } else {
            System.out.println("Transaction failed: Insufficient funds.");
            logger.writeLog(customer.getUsername(), "Failed purchase attempt for car ID " + targetCar.getId());
        }
    }

    private static void handleManageProfile(Scanner scanner, Customer customer) {
        boolean managing = true;
        while (managing) {
            manageProfileMenu.displayOptions();
            String opt = scanner.nextLine().trim();

            switch (opt) {
                case "1":
                    customer.displayProfile();
                    logger.writeLog(customer.getUsername(), "Viewed profile details.");
                    break;
                case "2":
                    System.out.println("Update Profile feature coming soon.");
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
