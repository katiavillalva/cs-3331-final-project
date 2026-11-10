/**
 * Represents a person with a first name and last name.
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 */
public abstract class Person {
    private String firstName; 
    private String lastName;

    /**
     * Constructs a new Person with the specified first name and last name.
     * @param firstName the person's first name
     * @param lastName the person's last name
     */
    protected Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Sets the person's first name.
     * @param firstName the new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the person's last name.
     * @param lastName the new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the person's first name.
     * @return the first name
     */
    public String getFirstName() { return this.firstName; }

    /**
     * Returns the person's last name.
     * @return the last name
     */
    public String getLastName() { return this.lastName; }
}