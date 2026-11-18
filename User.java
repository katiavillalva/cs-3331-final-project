import java.util.Set;

/**
 * Represents a general registered user in the system. 
 * Extends Person and serves as the superclass for Customer and Admin.
 * 
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 * @version 2.0
 * @since November 17, 2026
 */
public abstract class User extends Person {
    protected final int userID;
    protected final String userType;
    protected String username;
    protected String password;
    public static final String HEADER = buildHeader();

    // ==================== Constructor  ====================
    /**
     * Constructs a new User with the specified details.
     * 
     * @param firstName the user's first name
     * @param lastName  the user's last name
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
     * Sets the username to the inputted username if no other user has it.
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
     * Sets the password to the inputted password if it is different from the old password.
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

    /** Getter for userID
     *  @return the user's unique ID 
     * */
    public int getUserID() { return this.userID; }

    /** Getter for userType 
    * @return the user's type ("Admin" or "Customer") 
    */
    public String getUserType() { return this.userType; }

    /** Getter for username
     * @return the user's unique username */
    public String getUsername() { return this.username; }

    /** Getter for password
     * @return the user's password */
    public String getPassword() { return this.password; }

    // ==================== toString method ====================
    /**
     * Builds the column labels for the user table, lined up with toString().
     * @return the labels followed by a divider line
     */
    private static String buildHeader() {
        String labels = String.format("%-5s %-15s %-15s %-15s %-13s %-8s %-8s",
            "ID", "First Name", "Last Name", "Username", "Balance", "Member", "Type");
        return labels + "\n" + "-".repeat(labels.length());
    }

    @Override
     /** Overridden toString method 
     * @return String containing all user information except the password
     */
    public String toString() {
        return "----------- User Profile: -----------\n"
            + "Name: " + this.getFirstName() + " " + this.getLastName() + "\n"
            + "User Type: " + this.userType + "\n"
            + "User ID: " + this.userID + "\n"
            + "Username: " + this.getUsername() + "\n"
            + "-------------------------------------";
    }
}
