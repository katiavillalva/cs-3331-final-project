import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

/**
 * Represents a car dealership with a name, inventory of cars, list of users (admins and customers), and revenue.
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 */
public class CarDealership{
    private String name;
    private ArrayList<Car> inventory = new ArrayList<>();
    private Map<String, User> users = new HashMap<>();
    private double revenue = 0.0;

    /**
     * Constructs a new CarDealership with the specified name. Other attributes are assigned default values.
     * @param name the official name of the dealership
     */
    public CarDealership(String name) {
        this.name = name;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    // Getters
    public String getName() {return name; }

    public ArrayList<Car> getInventory() {return this.inventory;}

    public Map<String, User> getUsers() {return this.users;}

    public double getRevenue() {return revenue;}

    /**
     * Adds a car to the dealership's inventory list. 
     * @param car the Car object to add
     */
    public void addCar(Car car) {
        this.inventory.add(car);
    }

    /**
     * Adds a user to the dealership's list of users. 
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
     * Displays the dealership name and the information of all cars in the inventory. 
     */
    public void displayAllCars() {
        System.out.println(this.name + "Inventory :");
        for (Car car : this.inventory) {
            car.displayInformation();
        }
    }
 
    /*
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

    /** Displays the dealership name and the information of all available cars that match the search within 5000 of the inputted price 
     * @param idIn the car ID inputted by the user 
     * @param carTypeIn the car type inputted by the user
     * @param conditionIn the condition inputted by the user
     * @param priceIn the price inputted by the user
     */
    public void filterCars(int idIn, String carTypeIn, String conditionIn, double priceIn){
        int priceRange = 5000; // Define the price range for filtering
        for (Car car : this.inventory) {
            if ((idIn == -1 || car.getId() == idIn) &&
                (carTypeIn == null || car.getCarType().equalsIgnoreCase(carTypeIn)) &&
                (conditionIn == null || car.getCondition().equalsIgnoreCase(conditionIn)) &&
                (priceIn == -1 || (car.getPrice() <= priceIn + priceRange && car.getPrice() >= priceIn - priceRange))) && 
                car.getCarsAvailable() > 0) {
                car.displayInformation();
            }
        }
    }
}
