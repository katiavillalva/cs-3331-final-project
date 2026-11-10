import java.util.Set;

/**
 * Represents a general registered user in the system. 
 * Extends the abstract class {@link Person} and serves as the superclass for Customer and Admin.
 * 
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 * @version 1.0
 * @since November 10, 2026
 */
public abstract class User extends Person {
    private final int userID;
    private final String userType;
    private String username;
    private String password;

    /**
     * Constructs a new User with the specified details.
     * 
     * @param firstName the user's first name
     * @param lastName  the user's last name
     * @param userID    the user's unique read-only ID
     * @param userType  the type of user (e.g., "Customer" or "Admin")
     * @param username  the user's unique username
     * @param password  the user's password
     */
    protected User(String firstName, String lastName, int userID, String userType, String username, String password) {
        super(firstName, lastName);
        this.userID = userID;
        this.userType = userType;
        this.username = username;
        this.password = password;
    }

    // ==================== Setters with Validation ====================

    /**
     * Sets the user's username to the inputted username if no other user has it.
     * Throws an exception if the username already exists in the system.
     * 
     * @param usernames the set of all existing usernames in the system
     * @param username  the requested new username
     * @throws IllegalArgumentException if the username is not unique
     */
    public void setUsername(Set<String> usernames, String username) {
        if (usernames.contains(username)) {
            throw new IllegalArgumentException("Username already exists. Please choose a different username.");
        }                        
        this.username = username;
    }

    /**
     * Sets the user's password to the inputted password if it is different from the old password.
     * Throws an exception if the new password matches the current one.
     * 
     * @param password the new password
     * @throws IllegalArgumentException if the new password is the same as the old password
     */
    public void setPassword(String password) {
        if (this.password.equals(password)) {
            throw new IllegalArgumentException("New password cannot be the same as your old password.");
        }
        this.password = password;
    }

    // ==================== Getters ====================

    /** @return the user's unique ID */
    public int getUserID() { return this.userID; }

    /** @return the user's type ("Admin" or "Customer") */
    public String getUserType() { return this.userType; }

    /** @return the user's unique username */
    public String getUsername() { return this.username; }

    /** @return the user's password */
    public String getPassword() { return this.password; }

    // ==================== Display Methods ====================

    /**
     * Displays all User and Person attribute values in a formatted console block.
     */
    public void displayProfile() {
        System.out.println("----------- User Profile: -----------");
        System.out.println("Name: " + this.getFirstName() + " " + this.getLastName());
        System.out.println("User Type: " + this.userType);
        System.out.println("User ID: " + this.userID);
        System.out.println("Username: " + this.getUsername());
        System.out.println("Password: " + this.getPassword());
        System.out.println("-------------------------------------");
    }
}
