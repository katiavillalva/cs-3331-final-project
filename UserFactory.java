import java.util.List;

/** Factory which returns the corresponding User subclass object given the user type.
 *
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 * @version 1.0
 * @since November 17, 2026
 */
public class UserFactory {
    /** The valid user types (lowercase). Must match the cases in createUser. */
    public static final List<String> USER_TYPES = List.of("admin", "customer");

    /**
     * Creates the User subclass matching the given type.
     * moneyAvailable and isMember are only applied to Customers.
     * @return an Admin or Customer, or null if the type is not recognized
     */
    public User createUser(int userID, String firstName, String lastName, String username,
                           String password, String userType, double moneyAvailable, boolean isMember) {
        if (userType == null) {
            return null;
        }
        switch (userType.trim().toLowerCase()) {
            case "admin":
                return new Admin(firstName, lastName, userID, username, password);
            case "customer": {
                Customer customer = new Customer(firstName, lastName, userID, username, password);
                customer.updateMoneyAvailable(moneyAvailable);
                customer.setIsMember(isMember);
                return customer;
            }
            default:
                return null;
        }
    }
}