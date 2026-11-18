import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Driver class for the dealership system. Handles inputs and overall flow of the system.
 * 
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 * @version 3.0
 * @since November 17, 2026
 */
public class RunShop {
    private static Log logger = new Log("log.txt");
    private static CarDealership dealership = new CarDealership("Miner Cars Dealership");
    private static UserAuthenticator authorizer = new UserAuthenticator(dealership);
    private static Menu mainMenu = new Menu(List.of("Register", "Login", "Exit"), dealership.getName() + " - MAIN MENU");
    private static Menu adminMenu = new Menu(List.of("Manage Users", "Manage Cars", "View Inventory", "Get Revenue", "Logout"), "ADMINISTRATOR MENU");
    private static Menu customerMenu = new Menu(List.of("Manage Profile", "Browse Cars", "Logout"), "CUSTOMER MENU");
    private static User currentUser;

    /**
     * Main method which initializes the dealership, runs the main application loop, and calls methods to handles user interaction with 
     * the system.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try {
            System.out.println("Starting " + dealership.getName() + " System..");
            
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
                    System.out.println("Exiting system. Thank you for visiting " + dealership.getName() + "!");
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
            System.out.println("A critical error occured, exiting the system.");
            t.printStackTrace();
        }
    }

/**
     * Handles user registration by a user who is not an admin by using the UserRegister class 
     * 
     * @param scanner active console scanner
     */
    private static void handleRegister(Scanner scanner) {
        new UserRegister(dealership, logger).register(scanner);
    }

    /**
     * Handles the user login workflow by validating credentials and routing 
     * the session to role-specific menus (Admin or Customer).
     * 
     * @param scanner active console scanner
     */
    private static boolean handleLogin(Scanner scanner) {
        System.out.println("\nLogin \n");
        System.out.print("Enter username: ");
        String username = Validator.validateString(scanner.nextLine());
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        User authenticatedUser = authorizer.authenticate(username, password);

        if (authenticatedUser == null) {
            System.out.println("Invalid username or password. Please try again.");
            logger.writeLog("", "Failed login attempt for username: " + username);
            return false;
        }
        System.out.println("\nLogin successful! Welcome back, " + authenticatedUser.getFirstName() + "!");
        logger.writeLog(username, "Successfully logged in.");

        if (authenticatedUser instanceof Admin) {
            runAdminMenu(scanner, (Admin) authenticatedUser);
        } else if (authenticatedUser instanceof Customer) {
            runCustomerMenu(scanner, (Customer) authenticatedUser);
        }
        return true;
    }

    /**
     * Displays the customer menu and reads user input to allow the customer to interact with the menu.
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
     * Interface for browsing cars, filtering, and making purchases 
     * 
     * @param scanner  active console scanner
     * @param customer the currently logged-in Customer instance
     */
    private static void runBrowseCarsMenu(Scanner scanner, Customer customer) {
        boolean browsing = true;
        Menu browseCarsMenu = new Menu(List.of("Display All Available Cars", "Filter Cars", "Purchase a Car", "Back to Customer Menu"), "BROWSE CARS");
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
     * Reads valid user input (Car ID, type, condition, or price) )to call the car filter method from the CarDealership class  
     * 
     * @param scanner active console scanner
     */
    private static void handleFilterCars(Scanner scanner) {
        // Initialize the menu
        double price_range = 5000.0;
        List<String> options = List.of("Filter by Car ID",
                                    "Filter by Car Type (Sedan, SUV, Hatchback, Pickup)",
                                    "Filter by Condition (New, Used)",
                                    "Filter by Price Range (+/- $" + (int) price_range + ")",
                                    "Back to Browse Cars Menu");
        Menu carFilterMenu = new Menu(options, "Filter Cars");
        boolean running = true;
        List<Car> result = new ArrayList<>();
        while (running) {
            carFilterMenu.displayOptions();
            String input = scanner.nextLine().trim();

            try {
                switch (input) {
                    case "1":
                        System.out.print("Enter Car ID: ");
                        int idIn = Validator.validateWholeNumber(scanner.nextLine(), "ID");
                        result = dealership.filterCars(idIn, null, null, 0, price_range);
                        break;
                    case "2":
                        System.out.print("Enter Car Type (Sedan, SUV, Hatchback, Pickup): ");
                        String typeIn = Validator.validateCarType(scanner.nextLine());
                        result = dealership.filterCars(0, typeIn, null, 0, price_range);
                        break;
                    case "3":
                        System.out.print("Enter Condition (New, Used): ");
                        String condIn = Validator.validateCondition(scanner.nextLine());
                        result = dealership.filterCars(0, null, condIn, 0, price_range);
                        break;
                    case "4":
                        System.out.print("Enter Price ($): ");
                        double priceIn = Validator.validatePrice(scanner.nextLine());
                        result = dealership.filterCars(0, null, null, priceIn, price_range);
                        System.out.println("\nprice within $" + price_range + " of $" + priceIn);
                        break;
                    case "5":
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option '" + input + "'. Please enter a number (1-" + carFilterMenu.getNumOptions()+ ").");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + " Stopping the search...");
                running = false;
            }
            if (running && input.matches("[1-4]")) {
                if (result.isEmpty()) System.out.println("No cars matched the criteria");
            else { dealership.displayCars(result); result.clear(); }
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
        System.out.println("\nPurchase a car\n");
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
            dealership.updateRevenue(targetCar.getPrice()*(customer.getIsMember() ? (1 - customer.MEMBERSHIP_DISCOUNT) : 1)); // Apply membership discount if applicable
            targetCar.setCarsAvailable(targetCar.getCarsAvailable() - 1);
            dealership.saveUsersToCSV();
            dealership.saveCarsToCSV();

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
     * @param scanner  active console scanner
     * @param customer the currently logged-in Customer instance
     */
    private static void handleManageProfile(Scanner scanner, Customer customer) {
        Menu manageProfileMenu = new Menu(List.of("View Profile", "Update Profile", "Back to Customer Menu"), "MANAGE PROFILE");
        boolean managing = true;
        while (managing) {
            manageProfileMenu.displayOptions();
            String opt = scanner.nextLine().trim();

            switch (opt) {
                case "1":
                    System.out.println(customer.toString());
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
        Menu updateProfileMenu = new Menu(List.of("Change Username", "Change Password", "Update Money", "Back to Manage Profile"), "UPDATE PROFILE");
        boolean updating = true;
        while (updating) {
            updateProfileMenu.displayOptions();
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
        System.out.println("\nChange username \n");
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
        System.out.println("\nChange password \n");
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
        System.out.println("\nUpdate Money\n");
        System.out.print("Enter additional money to add ($): ");
        try {
            double additionalMoney = Validator.validatePrice(scanner.nextLine());
            customer.updateMoneyAvailable(additionalMoney);
            dealership.saveUsersToCSV();   // persist the new balance
            double newTotal = customer.getMoneyAvailable();

            System.out.println("Money updated successfully!");
            System.out.println("Added: $" + additionalMoney + " | New Total Balance: $" + newTotal);
            logger.writeLog(customer.getUsername(), "Added $" + additionalMoney + " to balance. New total: $" + newTotal);
        } catch (IllegalArgumentException e) {
            logger.writeLog(customer.getUsername(), "Failed attempt to update account balance.");
            System.out.println(e.getMessage() + " Update cancelled.");
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
        Menu manageUsersMenu = new Menu(List.of("Add New User (Customer or Admin", "View All Members", "Update Member","Delete Member", "Back to Administrator Menu"), "MANAGE USERS");
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

    /**
     * Handles the workflow for an administrator to add a new user to the system.
     * Captures user attributes and delegates creation to the CarDealership class.
     * 
     * @param scanner active console scanner
     * @param admin   the currently logged-in Admin instance
     */
    private static void handleAdminAddUser(Scanner scanner, Admin admin) {
        new AdminRegister(dealership, logger, admin).register(scanner);
    }

    /**
     * Sub-menu interface for viewing and searching members.
     * 
     * @param scanner active console scanner
     * @param admin   the currently logged-in Admin instance
     */
    private static void runViewMembersMenu(Scanner scanner, Admin admin) {
        boolean viewing = true;
        Menu viewMembersMenu = new Menu(List.of("Display All Members", "Search for a Member (ID, Name, Username)", "Back to Manage Users Menu"), "VIEW MEMBERS");
        while (viewing) {
            viewMembersMenu.displayOptions();
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
        System.out.println("\nUpdate Member Profile\n");
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
        System.out.println(targetUser.toString());
        Menu updateMemberMenu = new Menu(List.of("Change Name", "Change Username", "Change Password", "Back to Manage Users Menu"), "UPDATE MEMBER");

        boolean updatingMember = true;
        while (updatingMember) {
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
                        try {
                            targetUser.setPassword(newPassword);
                            System.out.println("Password updated successfully!");
                            logger.writeLog(admin.getUsername(), "Updated password for user ID " + targetUser.getUserID());
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
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
        System.out.println("\nDelete member\n");
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
        System.out.println(targetUser.toString());

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
            adminMenu.displayOptions();
            String opt = scanner.nextLine().trim();

            if (opt.equals("5") || opt.equalsIgnoreCase("Logout")) {
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
            } else if (opt.equals("4")) {
                 System.out.println("Total Revenue: $" + dealership.getRevenue());
                logger.writeLog(admin.getUsername(), "Viewed revenue.");
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
        Menu manageCars = new Menu(List.of("Add New Car", "View Cars (Display All or Search)", "Delete Car", "Back to Administrator Menu"), "MANAGE CARS");
        boolean managing = true;
        while (managing) {
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
        System.out.println("\nAdd new car to the inventory \n");
        try {
            System.out.print("Enter Car Type (Sedan, SUV, Hatchback, Pickup): ");
            String carType = Validator.validateCarType(scanner.nextLine());
            System.out.print("Enter Model: ");
            String model = Validator.validateString(scanner.nextLine());
            System.out.print("Enter Condition (New, Used): ");
            String condition = Validator.validateCondition(scanner.nextLine());
            System.out.print("Enter Color: ");
            String color = Validator.validateString(scanner.nextLine());
            System.out.print("Enter Capacity (passengers): ");
            int capacity = Validator.validateWholeNumber(scanner.nextLine(), "Capacity");
            System.out.print("Enter Mileage: ");
            int mileage = Validator.validateNonnegativeInt(scanner.nextLine(), "Mileage");
            System.out.print("Enter Fuel Type: ");
            String fuelType = Validator.validateString(scanner.nextLine());
            System.out.print("Enter Transmission: ");
            String transmission = Validator.validateString(scanner.nextLine());
            System.out.print("Enter VIN: ");
            String VIN = Validator.validateString(scanner.nextLine());
            System.out.print("Enter Price ($): ");
            double price = Validator.validatePrice(scanner.nextLine());
            System.out.print("Enter Cars Available (stock): ");
            int stock = Validator.validateNonnegativeInt(scanner.nextLine(), "Cars available");

            boolean success = dealership.addCarToInventory(carType, model, condition, color, capacity,
                                                        mileage, fuelType, transmission, VIN, price, stock);
            if (success) {
                System.out.println("Car added successfully to inventory!");
                logger.writeLog(admin.getUsername(), "Added new car model: " + model);
            } else {
                System.out.println("Failed to add car.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + " Operation cancelled.");
        }
    }

    /**
     * Sub-menu interface for viewing or searching cars.
     */
    private static void runViewCarsMenu(Scanner scanner, Admin admin) {
        boolean viewing = true;
        Menu viewCarsMenu = new Menu(List.of("Display All Cars", "Search for a Car by ID, Type, or Model", "Back to Manage Cars Menu"), "VIEW CARS");
        while (viewing) {
            viewCarsMenu.displayOptions();
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
        System.out.println(targetCar.toString());;

        Menu updateCarMenu = new Menu(List.of("Change Price", "Update Cars Available (Stock)", "Back to Manage Cars Menu"), "UPDATE CAR");
        boolean updating = true;
        while (updating) {
            updateCarMenu.displayOptions();
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
        System.out.println("\nDelete Car\n");
        System.out.print("Enter ID, Type, or Model of the car to delete: ");
        String query = scanner.nextLine().trim();
        if (query.isEmpty()) return;

        Car targetCar = dealership.findCar(query);
        if (targetCar == null) {
            System.out.println("Information: The car matching '" + query + "' was not found.");
            return;
        }

        System.out.println("\nCar found:");
        System.out.println(targetCar.toString());

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
