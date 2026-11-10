/**
 * Represents an SUV (Sport Utility Vehicle) in the Miner Cars Dealership system.
 * Extends the abstract class {@link Car} and provides specific implementations 
 * for SUV characteristics.
 * 
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 * @version 1.0
 * @since November 10, 2026
 */
public class SUV extends Car {

    /**
     * Constructs a new SUV with the specified attributes.
     * Automatically sets the car type to "SUV".
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
    public SUV(int id, String model, String condition, double price, String color, 
               int capacity, int mileage, String transmission, String fuelType, 
               String VIN, int carsAvailable) {
        super(id, "SUV", model, condition, price, color, capacity, mileage, transmission, fuelType, VIN, carsAvailable);
    }

    /**
     * Displays SUV-specific specifications such as drivetrain, ground clearance, and seating layout.
     */
    @Override
    public void displayCarSpecs() {
        System.out.println("--- SUV Specific Specifications ---");
        System.out.println("Body Style: Sport Utility Vehicle");
        System.out.println("Drivetrain: All-Wheel Drive (AWD) / Four-Wheel Drive (4WD)");
        System.out.println("Capacity & Utility: High ground clearance with spacious multi-row seating");
        System.out.println("-----------------------------------");
    }
}
