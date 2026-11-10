/**
 * Represents a customer in the system who can purchase cars, maintain a budget, 
 * and opt for a Miner Cars membership. Extends the abstract class {@link User}.
 * 
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 * @version 1.0
 * @since November 10, 2026
 */
public class Customer extends User {
    private int numCarsPurchased = 0;
    private double moneyAvailable = 0.0;
    private boolean isMember = false;

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
    public void updateMoneyAvailable(double amountIn) {
        if (this.moneyAvailable + amountIn < 0) {
            throw new IllegalArgumentException("Insufficient funds. Cannot deduct more than the available balance.");
        } 
        this.moneyAvailable += amountIn;
    }

    /**
     * Attempts to purchase a car from the dealership. Verifies availability and 
     * updates the customer's budget, car inventory stock, purchase count, and dealership revenue.
     * 
     * @param car        the car object the customer wishes to purchase
     * @param dealership the dealership managing the vehicle and overall revenue
     * @return true if the purchase is successful, false if the car is unavailable
     */
    public boolean purchaseCar(Car car, CarDealership dealership) {
        if (car.getCarsAvailable() <= 0) {
            System.out.println("Sorry, this car is not available for purchase.");
            return false;
        }

        // Deduct price from customer budget, reduce stock, increment purchase counter, and add revenue
        this.updateMoneyAvailable(-car.getPrice());
        car.setCarsAvailable(car.getCarsAvailable() - 1);
        this.numCarsPurchased++;
        dealership.updateRevenue(car.getPrice());
        
        System.out.println("You have successfully purchased the " + car.getModel() + 
                           ". The remaining balance in your account is: $" + this.moneyAvailable);
        return true;
    }
}
