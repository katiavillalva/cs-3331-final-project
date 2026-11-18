/**
 * Represents an Administrator in the system who can manage users, cars, and view financial revenue.
 * Extends the abstract class {@link User}.
 * 
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 * @version 1.0
 * @since November 10, 2026
 */
public class Admin extends User {

    /**
     * Constructs a new Admin with the specified personal and login details.
     * Automatically sets the user type to "admin".
     * 
     * @param firstName the admin's first name
     * @param lastName  the admin's last name
     * @param userID    the admin's unique ID
     * @param username  the admin's unique username
     * @param password  the admin's password
     */
    public Admin(String firstName, String lastName, int userID, String username, String password) {
        super(firstName, lastName, userID, "admin", username, password);
    }
}
