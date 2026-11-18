
import java.util.Scanner;

/**
 * Class which handles the logic for a user who is not an admin registering an new account in the system.
 *
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 * @version 1.0
 * @since November 17, 2026
 */
public class UserRegister extends Register {
    /**
     * Constructs a UserRegister object with a given dealership and logger.
     * @param dealership the dealership that stores the new user
     * @param logger the logger that records the registration
     */
    public UserRegister(CarDealership dealership, Log logger) {
        super(dealership, logger);
    }

    /**
     * Gets the type of user to register.
     * @param scanner the scanner reading input from the console
     * @return "customer"
     */
    @Override
    protected String getUserType(Scanner scanner) {
        return "customer"; // users who are not admins can only register customers
    }

    /**
     * Gets the new user's starting balance from the user input.
     * @param scanner the scanner reading input from the console
     * @return the initial money available
     * @throws IllegalArgumentException if the input is invalid
     */
    @Override
    protected double getMoneyAvailable(Scanner scanner) {
        System.out.print("Enter initial money available ($): ");
        return Validator.validatePrice(scanner.nextLine());
    }

    /**
     * Gets whether the new user is a member of the dealership.
     * @param scanner the scanner reading input from the console
     * @return true if the new user is a member, false otherwise
     * @throws IllegalArgumentException if the input is not y or n
     */
    @Override
    protected boolean getIsMember(Scanner scanner) {
        System.out.print("Become a member? (y/n): ");
        String input = Validator.validateString(scanner.nextLine()).toLowerCase();
        if (!input.equals("y") && !input.equals("n")) {
            throw new IllegalArgumentException("Invalid. Please enter y or n.");
        }
        return input.equals("y");
    }

    /**
     * Writes the successful registration to the log.
     * @param username the username of the new customer
     */
    @Override
    protected void logRegistration(String username) {
        logger.writeLog("Guest", "Added new customer account with username: " + username);
    }
}
