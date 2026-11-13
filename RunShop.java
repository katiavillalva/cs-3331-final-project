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
    private static CarDealership dealership = new CarDealership("Miner Cars Dealership");

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

            List<String> mainOptions = List.of("Register", "Login", "Exit");
            Menu mainMenu = new Menu(mainOptions);

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
            System.out.println("\n========================================");
            System.out.println("             CUSTOMER MENU              ");
            System.out.println("========================================");
            System.out.println("1. Manage Profile");
            System.out.println("2. Browse Cars (View, Filter, Purchase)");
            System.out.println("3. Logout");
            System.out.println("========================================");
            System.out.print("Please select an option: ");
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
            System.out.println("\n========================================");
            System.out.println("              BROWSE CARS               ");
            System.out.println("========================================");
            System.out.println("1. Display All Available Cars");
            System.out.println("2. Filter Cars (Advanced Search)");
            System.out.println("3. Purchase a Car");
            System.out.println("4. Back to Customer Menu");
            System.out.println("========================================");
            System.out.print("Please select an option: ");
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
     * Interactive sub-menu for advanced car filtering, allowing users to choose 
     * specific search criteria (ID, Car Type, Condition, or Price range +/- $5,000).
     * 
     * @param scanner active console scanner
     */
    private static void handleFilterCars(Scanner scanner) {
        boolean filtering = true;
        while (filtering) {
            System.out.println("\n========================================");
            System.out.println("            ADVANCED CAR FILTER         ");
            System.out.println("========================================");
            System.out.println("1. Filter by Car ID");
            System.out.println("2. Filter by Car Type (Sedan, SUV, etc.)");
            System.out.println("3. Filter by Condition (New, Used)");
            System.out.println("4. Filter by Price Range (+/- $5,000)");
            System.out.println("5. Back to Browse Cars Menu");
            System.out.println("========================================");
            System.out.print("Please select a filter option: ");
            String opt = scanner.nextLine().trim();

            switch (opt) {
                case "1":
                    System.out.print("Enter Car ID: ");
                    try {
                        int idIn = Integer.parseInt(scanner.nextLine().trim());
                        dealership.filterCars(idIn, null, null, -1.0);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID format. Search cancelled.");
                    }
                    break;
                case "2":
                    System.out.print("Enter Car Type (Sedan, SUV, Hatchback, Pickup): ");
                    String typeIn = scanner.nextLine().trim();
                    dealership.filterCars(-1, typeIn.isEmpty() ? null : typeIn, null, -1.0);
                    break;
                case "3":
                    System.out.print("Enter Condition (New, Used): ");
                    String condIn = scanner.nextLine().trim();
                    dealership.filterCars(-1, null, condIn.isEmpty() ? null : condIn, -1.0);
                    break;
                case "4":
                    System.out.print("Enter Target Price ($): ");
                    try {
                        double priceIn = Double.parseDouble(scanner.nextLine().trim());
                        dealership.filterCars(-1, null, null, priceIn);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid price format. Search cancelled.");
                    }
                    break;
                case "5":
                    filtering = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
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
     */
    private static void handleManageProfile(Scanner scanner, Customer customer) {
        boolean managing = true;
        while (managing) {
            System.out.println("\n========================================");
            System.out.println("             MANAGE PROFILE             ");
            System.out.println("========================================");
            System.out.println("1. View Profile");
            System.out.println("2. Update Profile");
            System.out.println("3. Back to Customer Menu");
            System.out.println("========================================");
            System.out.print("Please select an option: ");
            String opt = scanner.nextLine().trim();

            switch (opt) {
                case "1":
                    // Delegating profile display to the Customer class (UML compliance)
                    customer.displayProfile();
                    logger.writeLog(customer.getUsername(), "Viewed profile details.");
                    break;
                case "2":
                    handleUpdateProfile(scanner, customer);
                    break;
                case "3":
                    managing = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Manages the update profile sub-menu (Change username, Change password, Update Money, Back).
     * 
     * @param scanner  active console scanner
     * @param customer the currently logged-in Customer instance
     */
    private static void handleUpdateProfile(Scanner scanner, Customer customer) {
        boolean updating = true;
        while (updating) {
            System.out.println("\n========================================");
            System.out.println("             UPDATE PROFILE             ");
            System.out.println("========================================");
            System.out.println("1. Change Username");
            System.out.println("2. Change Password");
            System.out.println("3. Update Money");
            System.out.println("4. Back to Manage Profile");
            System.out.println("========================================");
            System.out.print("Please select an option: ");
            String opt = scanner.nextLine().trim();

            switch (opt) {
                case "1":
                    handleChangeUsername(scanner, customer);
                    break;
                case "2":
                    handleChangePassword(scanner, customer);
                    break;
                case "3":
                    handleUpdateMoney(scanner, customer);
                    break;
                case "4":
                    updating = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

/**
     * Handles the username change workflow. Delegates validation and updating 
     * to the Customer/User class, handles exceptions, and updates the dealership mapping.
     * 
     * @param scanner  active console scanner
     * @param customer the currently logged-in Customer instance
     */
    private static void handleChangeUsername(Scanner scanner, Customer customer) {
        System.out.println("\n--- CHANGE USERNAME ---");
        System.out.print("Enter new username: ");
        String newUsername = scanner.nextLine().trim();

        if (newUsername.isEmpty()) {
            System.out.println("Username cannot be empty. Update cancelled.");
            return;
        }

        try {
            String oldUsername = customer.getUsername();
            
            // Encapsulated validation and setting handled by User class
            customer.setUsername(dealership.getUsers().keySet(), newUsername);

            // Re-indexing the dealership map since the key (username) changed
            dealership.getUsers().remove(oldUsername);
            dealership.getUsers().put(newUsername, customer);

            System.out.println("Username updated successfully to: " + newUsername);
            logger.writeLog(newUsername, "Changed username from " + oldUsername + " to " + newUsername);
            
        } catch (IllegalArgumentException e) {
            // Displaying the validation error message thrown by User class
            System.out.println(e.getMessage());
            logger.writeLog(customer.getUsername(), "Failed username change attempt (Username already taken: " + newUsername + ")");
        }
    }

/**
     * Handles changing the customer's password and handles validation exceptions.
     * 
     * @param scanner  active console scanner
     * @param customer the currently logged-in Customer instance
     */
    private static void handleChangePassword(Scanner scanner, Customer customer) {
        System.out.println("\n--- CHANGE PASSWORD ---");
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine().trim();

        if (newPassword.isEmpty()) {
            System.out.println("Password cannot be empty. Update cancelled.");
            return;
        }

        try {
            customer.setPassword(newPassword);
            dealership.saveUsersToCSV();
            System.out.println("Password updated successfully!");
            logger.writeLog(customer.getUsername(), "Changed password successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            logger.writeLog(customer.getUsername(), "Failed password change attempt: New password matches old password.");
        }
    }

    /**
     * Handles adding additional money to the customer's available balance.
     * 
     * @param scanner  active console scanner
     * @param customer the currently logged-in Customer instance
     */
    private static void handleUpdateMoney(Scanner scanner, Customer customer) {
        System.out.println("\n--- UPDATE MONEY ---");
        System.out.print("Enter additional money to add ($): ");
        try {
            double additionalMoney = Double.parseDouble(scanner.nextLine().trim());
            if (additionalMoney < 0) {
                System.out.println("Amount cannot be negative. Update cancelled.");
                return;
            }

            double newTotal = customer.getMoneyAvailable() + additionalMoney;
            customer.updateMoneyAvailable(newTotal);

            System.out.println("Money updated successfully!");
            System.out.println("Added: $" + additionalMoney + " | New Total Balance: $" + newTotal);
            logger.writeLog(customer.getUsername(), "Added $" + additionalMoney + " to balance. New total: $" + newTotal);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Update cancelled.");
        }
    }

 
/**
     * Sub-menu interface for managing users (Add, View, Update, Delete, Back).
     * 
     * @param scanner active console scanner
     * @param admin   the currently logged-in Admin instance
     */
    private static void runManageUsersMenu(Scanner scanner, Admin admin) {
        boolean managing = true;
        while (managing) {
            System.out.println("\n========================================");
            System.out.println("             MANAGE USERS               ");
            System.out.println("========================================");
            System.out.println("1. Add New User (Customer or Admin)");
            System.out.println("2. View Members");
            System.out.println("3. Update Member");
            System.out.println("4. Delete Member");
            System.out.println("5. Back to Administrator Menu");
            System.out.println("========================================");
            System.out.print("Please select an option: ");
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

    /**
     * Handles the workflow for an administrator to add a new user to the system.
     * Captures user attributes and delegates creation to the CarDealership class.
     * 
     * @param scanner active console scanner
     * @param admin   the currently logged-in Admin instance
     */
    private static void handleAdminAddUser(Scanner scanner, Admin admin) {
        System.out.println("\n--- ADD NEW USER ---");
        System.out.print("Enter user type (Customer / Admin): ");
        String userType = scanner.nextLine().trim();

        if (!userType.equalsIgnoreCase("Customer") && !userType.equalsIgnoreCase("Admin")) {
            System.out.println("Invalid user type. Operation cancelled.");
            return;
        }

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine().trim();
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        double money = 0.0;
        if (userType.equalsIgnoreCase("Customer")) {
            System.out.print("Enter initial money available ($): ");
            try {
                money = Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                money = 0.0;
            }
        }

        boolean success = dealership.registerUser(firstName, lastName, username, password, userType, money);

        if (success) {
            System.out.println("New " + userType + " added successfully!");
            logger.writeLog(admin.getUsername(), "Added new " + userType + " account: " + username);
        } else {
            System.out.println("Failed to add user: Username may already exist or invalid data.");
            logger.writeLog(admin.getUsername(), "Failed attempt to add user: " + username);
        }
    }

    /**
     * Sub-menu interface for viewing and searching members.
     * 
     * @param scanner active console scanner
     * @param admin   the currently logged-in Admin instance
     */
    private static void runViewMembersMenu(Scanner scanner, Admin admin) {
        boolean viewing = true;
        while (viewing) {
            System.out.println("\n========================================");
            System.out.println("              VIEW MEMBERS              ");
            System.out.println("========================================");
            System.out.println("1. Display All Members");
            System.out.println("2. Search for a Member (ID, Name, Username)");
            System.out.println("3. Back to Manage Users Menu");
            System.out.println("========================================");
            System.out.print("Please select an option: ");
            String opt = scanner.nextLine().trim();

            switch (opt) {
                case "1":
                    dealership.displayAllMembers();
                    logger.writeLog(admin.getUsername(), "Displayed all dealership members.");
                    break;
                case "2":
                    System.out.print("Enter ID, Name, or Username to search: ");
                    String query = scanner.nextLine().trim();
                    if (!query.isEmpty()) {
                        dealership.searchMember(query);
                        logger.writeLog(admin.getUsername(), "Searched for member: " + query);
                    } else {
                        System.out.println("Search query cannot be empty.");
                    }
                    break;
                case "3":
                    viewing = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Handles the workflow for an administrator to update an existing member's details
     * (Name, Username with uniqueness validation, or Password).
     * 
     * @param scanner active console scanner
     * @param admin   the currently logged-in Admin instance
     */
    private static void handleAdminUpdateMember(Scanner scanner, Admin admin) {
        System.out.println("\n--- UPDATE MEMBER PROFILE ---");
        System.out.print("Enter ID, Name, or Username of the member to update: ");
        String query = scanner.nextLine().trim();

        if (query.isEmpty()) {
            System.out.println("Search query cannot be empty.");
            return;
        }

        User targetUser = dealership.findMember(query);

        if (targetUser == null) {
            System.out.println("Information: The member matching '" + query + "' was not found in the system.");
            logger.writeLog(admin.getUsername(), "Failed update search: Member not found (" + query + ")");
            return;
        }

        System.out.println("\nMember found:");
        targetUser.displayProfile();

        boolean updatingMember = true;
        while (updatingMember) {
            System.out.println("\n========================================");
            System.out.println("           UPDATE MEMBER MENU           ");
            System.out.println("========================================");
            System.out.println("1. Change Name");
            System.out.println("2. Change Username");
            System.out.println("3. Change Password");
            System.out.println("4. Back to Manage Users Menu");
            System.out.println("========================================");
            System.out.print("Please select an option: ");
            String opt = scanner.nextLine().trim();

            switch (opt) {
                case "1":
                    System.out.print("Enter new first name: ");
                    String newFirst = scanner.nextLine().trim();
                    System.out.print("Enter new last name: ");
                    String newLast = scanner.nextLine().trim();
                    
                    if (!newFirst.isEmpty() && !newLast.isEmpty()) {
                        targetUser.setFirstName(newFirst);
                        targetUser.setLastName(newLast);
                        System.out.println("Name updated successfully!");
                        logger.writeLog(admin.getUsername(), "Updated name for user ID " + targetUser.getUserID());
                    } else {
                        System.out.println("Names cannot be empty. Update cancelled.");
                    }
                    break;

                case "2":
                    System.out.print("Enter new username: ");
                    String newUsername = scanner.nextLine().trim();
                    if (newUsername.isEmpty()) {
                        System.out.println("Username cannot be empty.");
                        break;
                    }

                    try {
                        String oldUsername = targetUser.getUsername();
                        
                        // Using User's built-in validation method with the dealership's username set
                        targetUser.setUsername(dealership.getUsers().keySet(), newUsername);

                        // Re-indexing dealership map since the key changed
                        dealership.getUsers().remove(oldUsername);
                        dealership.getUsers().put(newUsername, targetUser);
                        dealership.saveUsersToCSV();

                        System.out.println("Username updated successfully to: " + newUsername);
                        logger.writeLog(admin.getUsername(), "Updated username from " + oldUsername + " to " + newUsername);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "3":
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.nextLine().trim();
                    if (!newPassword.isEmpty()) {
                        targetUser.setPassword(newPassword);
                        System.out.println("Password updated successfully!");
                        logger.writeLog(admin.getUsername(), "Updated password for user ID " + targetUser.getUserID());
                    } else {
                        System.out.println("Password cannot be empty.");
                    }
                    break;

                case "4":
                    updatingMember = false;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Handles the workflow for an administrator to delete an existing member.
     * Searches for the member, displays their profile, asks for confirmation, 
     * and deletes them upon confirmation.
     * 
     * @param scanner active console scanner
     * @param admin   the currently logged-in Admin instance
     */
    private static void handleAdminDeleteMember(Scanner scanner, Admin admin) {
        System.out.println("\n--- DELETE MEMBER ---");
        System.out.print("Enter ID, Name, or Username of the member to delete: ");
        String query = scanner.nextLine().trim();

        if (query.isEmpty()) {
            System.out.println("Search query cannot be empty.");
            return;
        }

        User targetUser = dealership.findMember(query);

        if (targetUser == null) {
            System.out.println("Information: The member matching '" + query + "' was not found in the system.");
            logger.writeLog(admin.getUsername(), "Failed delete search: Member not found (" + query + ")");
            return;
        }

        System.out.println("\nMember found:");
        targetUser.displayProfile();

        System.out.print("Are you sure you want to delete this member? (y/n): ");
        String confirmation = scanner.nextLine().trim();

        if (confirmation.equalsIgnoreCase("y") || confirmation.equalsIgnoreCase("yes")) {
            boolean success = dealership.deleteUser(targetUser);
            if (success) {
                System.out.println("Member deleted successfully.");
                logger.writeLog(admin.getUsername(), "Deleted member ID " + targetUser.getUserID() + " (" + targetUser.getUsername() + ")");
            } else {
                System.out.println("Error: Could not complete member deletion.");
                logger.writeLog(admin.getUsername(), "Failed deletion attempt for user ID " + targetUser.getUserID());
            }
        } else {
            System.out.println("Deletion cancelled.");
            logger.writeLog(admin.getUsername(), "Cancelled deletion for user ID " + targetUser.getUserID());
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
            System.out.println("\n========================================");
            System.out.println("           ADMINISTRATOR MENU           ");
            System.out.println("========================================");
            System.out.println("1. Manage Users");
            System.out.println("2. Manage Cars");
            System.out.println("3. View Dealership Inventory");
            System.out.println("4. Logout");
            System.out.println("========================================");
            System.out.print("Please select an option: ");
            String opt = scanner.nextLine().trim();

            if (opt.equals("4") || opt.equalsIgnoreCase("Logout")) {
                sessionActive = false;
                System.out.println("Logged out successfully.");
                logger.writeLog(admin.getUsername(), "Admin logged out.");
            } else if (opt.equals("1")) {
                runManageUsersMenu(scanner, admin);
            } else if (opt.equals("2")) {
                runManageCarsMenu(scanner, admin);
            } else if (opt.equals("3")) {
                dealership.displayAllCars();
                logger.writeLog(admin.getUsername(), "Viewed full dealership inventory.");
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Sub-menu interface for managing cars (Add, View, Update, Delete, Back).
     * 
     * @param scanner active console scanner
     * @param admin   the currently logged-in Admin instance
     */
    private static void runManageCarsMenu(Scanner scanner, Admin admin) {
        boolean managing = true;
        while (managing) {
            System.out.println("\n========================================");
            System.out.println("              MANAGE CARS               ");
            System.out.println("========================================");
            System.out.println("1. Add New Car");
            System.out.println("2. View Cars (Display All or Search)");
            System.out.println("3. Update Car (Price or Stock)");
            System.out.println("4. Delete Car");
            System.out.println("5. Back to Administrator Menu");
            System.out.println("========================================");
            System.out.print("Please select an option: ");
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
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Handles workflow for adding a new car.
     */
    private static void handleAdminAddCar(Scanner scanner, Admin admin) {
        System.out.println("\n--- ADD NEW CAR ---");
        System.out.print("Enter Car Type (Sedan, SUV, Hatchback, Pickup): ");
        String carType = scanner.nextLine().trim();
        if (!carType.equalsIgnoreCase("Sedan") && !carType.equalsIgnoreCase("SUV") && 
            !carType.equalsIgnoreCase("Hatchback") && !carType.equalsIgnoreCase("Pickup")) {
            System.out.println("Invalid car type. Operation cancelled.");
            return;
        }

        System.out.print("Enter Model: ");
        String model = scanner.nextLine().trim();
        System.out.print("Enter Condition (New, Used): ");
        String condition = scanner.nextLine().trim();
        System.out.print("Enter Color: ");
        String color = scanner.nextLine().trim();
        
        System.out.print("Enter Capacity (passengers): ");
        int capacity = 0;
        try { capacity = Integer.parseInt(scanner.nextLine().trim()); } catch (Exception e) {}

        System.out.print("Enter Mileage: ");
        int mileage = 0;
        try { mileage = Integer.parseInt(scanner.nextLine().trim()); } catch (Exception e) {}

        System.out.print("Enter Fuel Type: ");
        String fuelType = scanner.nextLine().trim();
        System.out.print("Enter Transmission: ");
        String transmission = scanner.nextLine().trim();
        System.out.print("Enter VIN: ");
        String VIN = scanner.nextLine().trim();

        System.out.print("Enter Price ($): ");
        double price = 0.0;
        try { price = Double.parseDouble(scanner.nextLine().trim()); } catch (Exception e) {}

        System.out.print("Enter Cars Available (stock): ");
        int stock = 0;
        try { stock = Integer.parseInt(scanner.nextLine().trim()); } catch (Exception e) {}

        boolean success = dealership.addCarToInventory(carType, model, condition, color, capacity, mileage, fuelType, transmission, VIN, price, stock);
        if (success) {
            System.out.println("Car added successfully to inventory!");
            logger.writeLog(admin.getUsername(), "Added new car model: " + model);
        } else {
            System.out.println("Failed to add car.");
        }
    }

    /**
     * Sub-menu interface for viewing or searching cars.
     */
    private static void runViewCarsMenu(Scanner scanner, Admin admin) {
        boolean viewing = true;
        while (viewing) {
            System.out.println("\n========================================");
            System.out.println("               VIEW CARS                ");
            System.out.println("========================================");
            System.out.println("1. Display All Cars");
            System.out.println("2. Search for a Car (ID, Type, Model)");
            System.out.println("3. Back to Manage Cars Menu");
            System.out.println("========================================");
            System.out.print("Please select an option: ");
            String opt = scanner.nextLine().trim();

            switch (opt) {
                case "1":
                    dealership.displayAllCars();
                    logger.writeLog(admin.getUsername(), "Displayed all inventory cars.");
                    break;
                case "2":
                    System.out.print("Enter ID, Car Type, or Model to search: ");
                    String query = scanner.nextLine().trim();
                    if (!query.isEmpty()) {
                        dealership.searchCar(query);
                        logger.writeLog(admin.getUsername(), "Searched for car query: " + query);
                    } else {
                        System.out.println("Search query cannot be empty.");
                    }
                    break;
                case "3":
                    viewing = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Handles updating car price or available stock.
     */
    private static void handleAdminUpdateCar(Scanner scanner, Admin admin) {
        System.out.println("\n--- UPDATE CAR ---");
        System.out.print("Enter ID, Type, or Model of the car to update: ");
        String query = scanner.nextLine().trim();
        if (query.isEmpty()) return;

        Car targetCar = dealership.findCar(query);
        if (targetCar == null) {
            System.out.println("Information: The car matching '" + query + "' was not found.");
            return;
        }

        System.out.println("\nCar found:");
        targetCar.displayInformation();

        boolean updating = true;
        while (updating) {
            System.out.println("\n========================================");
            System.out.println("            UPDATE CAR MENU             ");
            System.out.println("========================================");
            System.out.println("1. Change Price");
            System.out.println("2. Change Cars Available (Stock)");
            System.out.println("3. Back to Manage Cars Menu");
            System.out.println("========================================");
            System.out.print("Please select an option: ");
            String opt = scanner.nextLine().trim();

            switch (opt) {
                case "1":
                    System.out.print("Enter new price ($): ");
                    try {
                        double newPrice = Double.parseDouble(scanner.nextLine().trim());
                        if (newPrice >= 0) {
                            targetCar.setPrice(newPrice);
                            dealership.saveCarsToCSV();
                            System.out.println("Price updated successfully!");
                            logger.writeLog(admin.getUsername(), "Updated price for car ID " + targetCar.getId());
                        } else {
                            System.out.println("Price cannot be negative.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number format.");
                    }
                    break;
                case "2":
                    System.out.print("Enter new available count: ");
                    try {
                        int newStock = Integer.parseInt(scanner.nextLine().trim());
                        if (newStock >= 0) {
                            targetCar.setCarsAvailable(newStock);
                            dealership.saveCarsToCSV();
                            System.out.println("Stock updated successfully!");
                            logger.writeLog(admin.getUsername(), "Updated stock for car ID " + targetCar.getId());
                        } else {
                            System.out.println("Stock cannot be negative.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number format.");
                    }
                    break;
                case "3":
                    updating = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Handles deleting a car with confirmation.
     */
    private static void handleAdminDeleteCar(Scanner scanner, Admin admin) {
        System.out.println("\n--- DELETE CAR ---");
        System.out.print("Enter ID, Type, or Model of the car to delete: ");
        String query = scanner.nextLine().trim();
        if (query.isEmpty()) return;

        Car targetCar = dealership.findCar(query);
        if (targetCar == null) {
            System.out.println("Information: The car matching '" + query + "' was not found.");
            return;
        }

        System.out.println("\nCar found:");
        targetCar.displayInformation();

        System.out.print("Are you sure you want to delete this car? (y/n): ");
        String confirmation = scanner.nextLine().trim();

        if (confirmation.equalsIgnoreCase("y") || confirmation.equalsIgnoreCase("yes")) {
            boolean success = dealership.deleteCar(targetCar);
            if (success) {
                System.out.println("Car deleted successfully from inventory.");
                logger.writeLog(admin.getUsername(), "Deleted car ID " + targetCar.getId());
            } else {
                System.out.println("Error completing car deletion.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    

}
