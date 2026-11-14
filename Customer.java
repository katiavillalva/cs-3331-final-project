/**
 * Represents a customer in the system who can purchase cars, maintain a budget, 
 * and opt for a Miner Cars membership. Extends the abstract class {@link User}.
 * 
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 * @version 2.0
 * @since November 14, 2026
 */
public class Customer extends User {
    private int numCarsPurchased = 0;
    private double moneyAvailable = 0.0;
    private boolean isMember = false;
    public final double MEMBERSHIP_DISCOUNT = 0.10;  

    /**
     * Constructs a new Customer with the specified personal and login details.
     * Automatically sets the user type to "Customer".
     * 
     * @param firstName the customer's first name
     * @param lastName  the customer's last name
     * @param userID    the customer's unique ID
     * @param username  the customer's unique username
     * @param password  the customer's password
     */
    public Customer(String firstName, String lastName, int userID, String username, String password) {
        super(firstName, lastName, userID, "Customer", username, password);
    }

    // ==================== Setters ====================

    /**
     * Sets the customer's membership status.
     * 
     * @param isMember true if the customer opts for membership, false otherwise
     */
    public void setIsMember(boolean isMember) { 
        this.isMember = isMember; 
    }

    // ==================== Getters ====================

    /**
     * Returns the number of cars purchased by the customer.
     * 
     * @return the number of cars purchased
     */
    public int getNumCarsPurchased() { 
        return this.numCarsPurchased; 
    }

    /**
     * Returns the customer's membership status.
     * 
     * @return true if the customer is a member, false otherwise
     */
    public boolean getIsMember() { 
        return this.isMember; 
    }

    /**
     * Returns the customer's available money budget.
     * 
     * @return the money available in the account
     */
    public double getMoneyAvailable() { 
        return this.moneyAvailable; 
    }

    // ==================== Core Methods ====================

    /**
     * Adds or deducts an amount from the customer's available money. 
     * Throws an exception if the resulting balance would be negative.
     * 
     * @param amountIn the amount to add (can be negative to deduct from the available money)
     * @throws IllegalArgumentException if the resulting balance drops below zero
     */
    public boolean updateMoneyAvailable(double amountIn) {
        if (this.moneyAvailable + amountIn < 0) {
            System.out.println("Insufficient funds. Cannot deduct $" + (-amountIn) + " from available money.");
            System.out.println("Current available money: $" + this.moneyAvailable);
            return false; // Insufficient funds, cannot deduct
        } 
        this.moneyAvailable += amountIn;
        return true;
    }

/**
     * Attempts to purchase the specified car. Validates if the customer has 
     * sufficient funds, deducts the car price from their available balance, 
     * and increments the number of cars purchased.
     * 
     * @param carIn the Car object to purchase
     * @return true if the purchase was successful, false if funds are insufficient or car is null
     */
    public boolean purchaseCar(Car carIn) {
        if (carIn == null) {
            return false;
        }
        double price = carIn.getPrice();
        if (this.isMember) {
            price -= price * MEMBERSHIP_DISCOUNT; // Apply membership discount
        }
        if (this.updateMoneyAvailable(-price) == false) {
            return false; // Insufficient funds, cannot purchase
        }
        this.numCarsPurchased++; // Increment the number of cars purchased
        return true;
    }

    /**
     * Displays the complete profile information of the customer, 
     * including ID, name, credentials, balance, cars purchased, 
     * membership status, and user type.
     */
    @Override
    public void displayProfile() {
        System.out.println("\n--- CUSTOMER PROFILE ---");
        System.out.println("User ID: " + getUserID());
        System.out.println("First Name: " + getFirstName());
        System.out.println("Last Name: " + getLastName());
        System.out.println("Username: " + getUsername());
        System.out.println("Password: " + getPassword());
        System.out.println("Money Available: $" + getMoneyAvailable());
        System.out.println("Cars Purchased: " + getNumCarsPurchased());
        System.out.println("MinerCars Membership: " + getIsMember());
        System.out.println("User Type: " + getUserType());
    }
}
