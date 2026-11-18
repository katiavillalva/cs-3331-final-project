/** Checks user credentials (username and password) to handle roles in in the driver class. 
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 * @version 3.0
 * @since November 17, 2026
 */
public class UserAuthenticator {
    /** The dealership to authenticate the user within */
    private final CarDealership dealership;

    /** One-argument constructor
     * @param dealership the dealership to link the authenticator to
     */
    public UserAuthenticator(CarDealership dealership) {
        this.dealership = dealership;
    }

    /** Checks that the user credential match a user in the system
    /** @return the User if the username and password match the user's in the system, null otherwise */
    public User authenticate(String username, String password) {
        User user = this.dealership.getUsers().get(username);
        if (user == null || !user.getPassword().equals(password)) {
            return null;
        }
        return user;
    }
}