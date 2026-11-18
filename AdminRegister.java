
import java.util.Scanner;

/**
 * Class which handles the logic for a logged-in admin registering a new user in the system.
 * Admins can register both customer and admin accounts.
 *
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 * @version 1.0
 * @since November 17, 2026
 */
public class AdminRegister extends Register {
    /** The logged-in admin who is registering the new user */
    private final Admin admin;

    /** The type of user being registered, set in getUserType */
    private String userType;

    /**
     * Constructs an AdminRegister object with a given dealership, logger, and admin.
     *
     * @param dealership the dealership that stores the new user
     * @param logger     the logger that records the registration
     * @param admin      the logged-in admin registering the new user
     */
    public AdminRegister(CarDealership dealership, Log logger, Admin admin) {
        super(dealership, logger);
        this.admin = admin;
    }

    /**
     * Gets the type of user to register from the user input.
     * @param scanner the scanner reading input from the console
     * @return "admin" or "customer"
     * @throws IllegalArgumentException if the input is not a valid user type
     */
    @Override
    protected String getUserType(Scanner scanner) {
        System.out.print("Enter user type " + UserFactory.USER_TYPES + ": ");
        userType = Validator.validateUserType(scanner.nextLine());
        return userType;
    }

    /**
     * Gets the new user's starting balance from the user input.
     * Only customers have a balance, so admins start at 0.
     * @param scanner the scanner reading input from the console
     * @return the initial money available
     * @throws IllegalArgumentException if the input is invalid
     */
    @Override
    protected double getMoneyAvailable(Scanner scanner) {
        if (userType.equals("admin")) {
            return 0.0;
        }
        System.out.print("Enter initial money available ($): ");
        return Validator.validatePrice(scanner.nextLine());
    }

    /**
     * Gets whether the new user is a member.
     * Only customers can be members, so admins are never members.
     * @param scanner the scanner reading input from the console
     * @return true if the new user is a member, false otherwise
     * @throws IllegalArgumentException if the input is not y or n
     */
    @Override
    protected boolean getIsMember(Scanner scanner) {
        if (userType.equals("admin")) {
            return false;
        }
        System.out.print("Make this customer a member? (y/n): ");
        String input = Validator.validateString(scanner.nextLine()).toLowerCase();
        if (!input.equals("y") && !input.equals("n")) {
            throw new IllegalArgumentException("Invalid. Please enter y or n.");
        }
        return input.equals("y");
    }

    /**
     * Writes the successful registration to the log under the admin's username.
     * @param username the username of the new user
     */
    @Override
    protected void logRegistration(String username) {
        logger.writeLog(admin.getUsername(), "Added new " + userType + " account with username: " + username);
    }
}
