import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a car dealership managing inventory, users, and financial revenue.
 * 
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 * @version 1.0
 * @since November 10, 2026
 */
public class CarDealership {
    private String name;
    private ArrayList<Car> inventory = new ArrayList<>();
    private Map<String, User> users = new HashMap<>();
    private double revenue = 0.0;

    /**
     * Constructs a new CarDealership with the specified name. 
     * Initializes an empty inventory, an empty user map, and sets revenue to 0.0.
     * 
     * @param name the official name of the dealership
     */
    public CarDealership(String name) {
        this.name = name;
    }

    // ==================== Setters ====================

    /**
     * Updates the name of the dealership.
     * @param name the new name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    // ==================== Getters ====================

    /** @return the name of the dealership */
    public String getName() { return name; }

    /** @return the list of cars in the inventory */
    public ArrayList<Car> getInventory() { return this.inventory; }

    /** @return the map of users registered in the system */
    public Map<String, User> getUsers() { return this.users; }

    /** @return the total revenue accumulated by the dealership */
    public double getRevenue() { return revenue; }

    // ==================== Core Methods ====================

    /**
     * Adds a car to the dealership's inventory list. 
     * @param car the Car object to add
     */
    public void addCar(Car car) {
        this.inventory.add(car);
    }

    /**
     * Adds a user to the dealership's map of users using their username as the key. 
     * @param user the User object to add
     */
    public void addUser(User user) {
        this.users.put(user.getUsername(), user);
    }
    
    /**
     * Updates the dealership's revenue by adding the amount earned from car sale(s). 
     * @param amountIn the amount to add to the revenue
     * @return true if the update was successful, false if the amount is negative
     */
    public boolean updateRevenue(double amountIn) {
        if (amountIn < 0) {
            return false;
        }
        this.revenue += amountIn;
        return true;
    }

    /**
     * Displays the dealership name and the information of all cars in the inventory, regardless of availability. 
     */
    public void displayAllCars() {
        System.out.println(this.name + " Inventory:");
        for (Car car : this.inventory) {
            car.displayInformation();
        }
    }
 
    /**
     * Displays the dealership name and the information of all cars in the inventory that have at least one car available. 
     */
    public void displayAvailableCars() {
        System.out.println(this.name + " Available Cars:");
        for (Car car : this.inventory) {
            if (car.getCarsAvailable() > 0) {
                car.displayInformation();
            }
        }
    }

    /**
     * Displays the information of available cars that match the specified search criteria 
     * (ID, car type, condition, or a price range within +/- $5,000).
     * 
     * @param idIn        the car ID inputted by the user (-1 to ignore)
     * @param carTypeIn   the car type inputted by the user (null to ignore)
     * @param conditionIn the condition inputted by the user (null to ignore)
     * @param priceIn     the target price inputted by the user (-1 to ignore)
     */
    public void filterCars(int idIn, String carTypeIn, String conditionIn, double priceIn) {
        System.out.println(this.name + " Filtered Cars:");
        for (Car car : this.inventory) {
            if ((idIn == -1 || car.getId() == idIn) &&
                (carTypeIn == null || car.getCarType().equalsIgnoreCase(carTypeIn)) &&
                (conditionIn == null || car.getCondition().equalsIgnoreCase(conditionIn)) &&
                (priceIn == -1 || (car.getPrice() <= priceIn + 5000 && car.getPrice() >= priceIn - 5000)) && 
                car.getCarsAvailable() > 0) {
                car.displayInformation();
            }
        }
    }
}
