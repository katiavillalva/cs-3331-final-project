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
     * Automatically sets the user type to "Admin".
     * 
     * @param firstName the admin's first name
     * @param lastName  the admin's last name
     * @param userID    the admin's unique ID
     * @param username  the admin's unique username
     * @param password  the admin's password
     */
    public Admin(String firstName, String lastName, int userID, String username, String password) {
        super(firstName, lastName, userID, "Admin", username, password);
    }

    /**
     * Adds a new car to the dealership inventory.
     * 
     * @param dealership the dealership where the car is added
     * @param car        the Car object to add
     */
    public void addCar(CarDealership dealership, Car car) {
        dealership.addCar(car);
        System.out.println("Car successfully added to the dealership inventory.");
    }

    /**
     * Adds a new user (Customer or Admin) to the dealership system.
     * 
     * @param dealership the dealership managing the users
     * @param user       the User object to add
     */
    public void addUser(CarDealership dealership, User user) {
        dealership.addUser(user);
        System.out.println("User " + user.getUsername() + " successfully added to the system.");
    }

    /**
     * Updates the dealership's total revenue.
     * 
     * @param dealership the dealership whose revenue is updated
     * @param amount     the amount to add
     * @return true if updated successfully, false otherwise
     */
    public boolean updateRevenue(CarDealership dealership, double amount) {
        return dealership.updateRevenue(amount);
    }
}
