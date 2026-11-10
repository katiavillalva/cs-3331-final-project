/**
 * Abstract base class representing a generic person with a first name and last name.
 * Serves as the superclass for the user hierarchy in the system.
 * 
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 * @version 1.0
 * @since November 10, 2026
 */
public abstract class Person {
    private String firstName; 
    private String lastName;

    /**
     * Constructs a new Person with the specified first name and last name.
     * 
     * @param firstName the person's first name
     * @param lastName  the person's last name
     */
    protected Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // ==================== Setters ====================

    /**
     * Sets the person's first name.
     * 
     * @param firstName the new first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the person's last name.
     * 
     * @param lastName the new last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // ==================== Getters ====================

    /**
     * Returns the person's first name.
     * 
     * @return the first name
     */
    public String getFirstName() { 
        return this.firstName; 
    }

    /**
     * Returns the person's last name.
     * 
     * @return the last name
     */
    public String getLastName() { 
        return this.lastName; 
    }
}
