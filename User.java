import java.util.Set;

/**
 * Represents a general registered user in the system.
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 */
public abstract class User extends Person {
    private final int userID;
    private final String userType;
    private String username;
    private String password;

    /**
     * Constructs a new User with the specified details.
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param username the user's unique username
     * @param password the user's password
     */
    protected User(String firstName, String lastName, int userID, String userType, String username, String password) {
        super(firstName, lastName);
        this.userID = userID;
        this.userType = userType;
        this.username = username;
        this.password = password;
    }

    /**
     * Sets the user's username to the inputted username if no other user has it, throws an exception if it is not unique.
     * @param username the requested username inputted by the user
     * @param usernames the set of all usernames of all users in the system
     */
    public void setUsername(Set<String> usernames, String username) {
        if (usernames.contains(username)) {
            throw new IllegalArgumentException("Username already exists. Please choose a different username.");
        }                                                                                                                               
        this.username = username;
    }

    /**
     * Sets the user's password to the inputted password if it is not the same as the old password, throws an exception if it is the same.
     * @param password the new password
     */
    public void setPassword(String password) {
        if (this.password.equals(password)) {
            throw new IllegalArgumentException("New password cannot be the same as your old password.");
        }
        this.password = password;
    }

    /**
     * Returns the user's unique ID.
     * @return the userID
     */
    public int getUserID() { return this.userID; }

    /**
     * Returns the user's type ("Admin" or "Customer").
     * @return the userType
     */
    public String getUserType() { return this.userType; }

    /**
     * Returns the user's unique username.
     * @return the username
     */
    public String getUsername() { return this.username; }

    /**
     * Returns the user's password.
     * @return the password
     */
    public String getPassword() { return this.password; }

    /**
     * Displays all User and Person attribute values.
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