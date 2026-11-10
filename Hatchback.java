/**
 * Represents a Hatchback vehicle in the Miner Cars Dealership system.
 * Extends the abstract class {@link Car} and provides specific implementations 
 * for hatchback characteristics.
 * 
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 * @version 1.0
 * @since November 10, 2026
 */
public class Hatchback extends Car {

    /**
     * Constructs a new Hatchback with the specified attributes.
     * Automatically sets the car type to "Hatchback".
     * 
     * @param id            Unique identifier for the car
     * @param model         Model name of the car
     * @param condition     Condition of the car (e.g., New, Used)
     * @param price         Price of the car
     * @param color         Color of the car
     * @param capacity      Passenger seating capacity
     * @param mileage       Car mileage in miles
     * @param transmission  Transmission type (e.g., Automatic, Manual)
     * @param fuelType      Fuel type (e.g., Gasoline, Electric, Hybrid)
     * @param VIN           Vehicle Identification Number
     * @param carsAvailable Quantity available in inventory
     */
    public Hatchback(int id, String model, String condition, double price, String color, 
                     int capacity, int mileage, String transmission, String fuelType, 
                     String VIN, int carsAvailable) {
        super(id, "Hatchback", model, condition, price, color, capacity, mileage, transmission, fuelType, VIN, carsAvailable);
    }

    /**
     * Displays hatchback-specific specifications such as cargo volume and rear-door configuration.
     */
    @Override
    public void displayCarSpecs() {
        System.out.println("--- Hatchback Specific Specifications ---");
        System.out.println("Body Style: 5-Door Hatchback");
        System.out.println("Cargo Capacity: Expanded rear storage with folding seats");
        System.out.println("Design: Compact and versatile urban efficiency");
        System.out.println("-----------------------------------------");
    }
}
