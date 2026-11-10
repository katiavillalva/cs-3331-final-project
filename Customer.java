/**
 * Represents a customer in the system who can purchase cars, has a membership status, and maintains a budget.
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 */
public class Customer extends User{
    private int numCarsPurchased = 0;
    private double moneyAvailable = 0.0;
    private boolean isMember = false;


    /**
     * Constructs a new Customer with the specified details.
     * @param firstName the customer's first name
     * @param lastName the customer's last name
     * @param userID the customer's unique ID
     * @param username the customer's unique username
     * @param password the customer's password
     */
    public Customer(String firstName, String lastName, int userID, String username, String password) {
        super(firstName, lastName, userID, "Customer", username, password);
    }

    /**
     * Sets the customer's membership status.
     * @param isMember the customer's membership status
     */
    public void setIsMember(boolean isMember) { this.isMember = isMember; }

    /**
     * Returns the number of cars purchased by the customer.
     * @return the number of cars purchased
     */
    public int getNumCarsPurchased() { return this.numCarsPurchased; }

    /**
     * Returns the customer's membership status.
     * @return true if the customer is a member, false otherwise
     */
    public boolean getIsMember() { return this.isMember; }

    /**
     * Returns the customer's available money.
     * @return the money available
     */
    public double getMoneyAvailable() { return this.moneyAvailable; }

    /**
     * Adds an amount to the customer's available money. Throws an exception if the resulting balance would be negative.
     * @param amountIn the amount to add (can be negative to deduct from the available money)
     */
    public void updateMoneyAvailable(double amountIn) {
        if (this.moneyAvailable + amountIn < 0) {
            throw new IllegalArgumentException("Insufficient funds. Cannot deduct more than the available balance.");
        } 
        this.moneyAvailable += amountIn;
    }


    /**
     * Attempts to purchase a car from the dealership.
     * @param car the car to purchase
     * @param dealership the dealership from which to purchase the car
     * @return true if the purchase is successful, false otherwise
     */
    public boolean purchaseCar(Car car, CarDealership dealership) {
        if (car.getCarsAvailable() <= 0) {
            System.out.println("Sorry, this car is not available for purchase.");
            return false;
        }

        this.updateMoneyAvailable(-car.getPrice());
        car.setCarsAvailable(car.getCarsAvailable() - 1);
        this.numCarsPurchased++;
        dealership.updateRevenue(car.getPrice());
        System.out.println("You have successfully purchased the " + car.getModel() + ". The remaining balance in your account is: $" + this.moneyAvailable);
        return true;
    }
}