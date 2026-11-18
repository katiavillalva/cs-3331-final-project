import java.util.List;
/** Factory which returns the corresponding car subclass object given the car type. 
 * 
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 * @version 1.0
 * @since November 17, 2026
 */
public class CarFactory {
    /** Valid car types (lowercase). Must match the cases in createCar. */
    public static final List<String> CAR_TYPES = List.of("sedan", "suv", "hatchback", "pickup");

    /** The possible car conditions as lowercase*/
    public static final List<String> CAR_CONDITION_TYPES = List.of("new", "used");

    /**
     * Creates the Car subclass matching the given type.
     * @return the Car subclass object created or null if the type is not recognized
     */
    public Car createCar(int id, String carType, String model, String condition, double price,
                         String color, int capacity, int mileage, String transmission,
                         String fuelType, String VIN, int carsAvailable) {
        if (carType == null) {
            return null;
        }
        switch (carType.trim().toLowerCase()) {
            case "sedan":
                return new Sedan(id, model, condition, price, color, capacity, mileage, transmission, fuelType, VIN, carsAvailable);
            case "suv":
                return new SUV(id, model, condition, price, color, capacity, mileage, transmission, fuelType, VIN, carsAvailable);
            case "hatchback":
                return new Hatchback(id, model, condition, price, color, capacity, mileage, transmission, fuelType, VIN, carsAvailable);
            case "pickup":
                return new Pickup(id, model, condition, price, color, capacity, mileage, transmission, fuelType, VIN, carsAvailable);
            default:
                return null;
        }
    }
}