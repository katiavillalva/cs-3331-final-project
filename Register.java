
import java.util.Scanner;

/**
 * Abstract base class with template method for registering a user.
 * Subclasses decide the user type, the starting balance, the membership, and how the
 * registration is logged: {@link UserRegister} for guests and {@link AdminRegister} for admins.
 *
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 * @version 2.0
 * @since November 17, 2026
 */
public abstract class Register {
    /** The dealership the new user will register with */
    protected final CarDealership dealership;

    /** Records system activity for the dealership */
    protected final Log logger;

    /**
     * Constructs a Register object with a given dealership and logger.
     *
     * @param dealership the dealership that stores the new user
     * @param logger     the logger that records the registration
     */
    protected Register(CarDealership dealership, Log logger) {
        this.dealership = dealership;
        this.logger = logger;
    }

    /**
     * Template method for registration logic. The steps always run in the same order.
     * @param scanner the active scanner reading input from the console
     * @return true if the registration was successful, false otherwise
     */
    public final boolean register(Scanner scanner) {
        try {
            System.out.println("\nRegistering a new user\n");
            String userType = getUserType(scanner);
            System.out.print("Enter first name: ");
            String firstName = Validator.validateString(scanner.nextLine());
            System.out.print("Enter last name: ");
            String lastName = Validator.validateString(scanner.nextLine());
            System.out.print("Enter username: ");
            String username = Validator.validateString(scanner.nextLine());

            if (dealership.getUsers().containsKey(username)) {
                throw new IllegalArgumentException("Invalid. Username already exists.");
            }
            System.out.print("Enter password: ");
            String password = Validator.validateString(scanner.nextLine());
            double moneyAvailable = getMoneyAvailable(scanner);
            boolean isMember = getIsMember(scanner);

            boolean success = dealership.registerUser(firstName, lastName, username, password, userType, moneyAvailable, isMember);
            if (success) {
                System.out.println("\nRegistered successfully!\n");
                logRegistration(username);
            } else {
                System.out.println("\nRegistration failed. The user could not be saved.\n");
            }
            return success;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + " Operation cancelled.");
            return false;
        }
    }

    /**
     * Gets the type of user to register.
     * @param scanner the active scanner reading input from the console
     * @return "admin" or "customer"
     */
    protected abstract String getUserType(Scanner scanner);

    /**
     * Gets the new user's starting balance.
     * @param scanner the active scanner reading input from the console
     * @return the initial money available (0 for admins)
     */
    protected abstract double getMoneyAvailable(Scanner scanner);

    /**
     * Gets whether the new user is a member.
     * @param scanner the active scanner reading input from the console
     * @return true if the new user is a member, false otherwise
     */
    protected abstract boolean getIsMember(Scanner scanner);

    /**
     * Writes the successful registration to the log.
     * @param username the username of the new user
     */
    protected abstract void logRegistration(String username);
}
