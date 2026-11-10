public abstract class Car{
    private final int id;
    private final String carType;
    private final String model;
    private String condition;
    private double price;
    private String color;
    private final int capacity;
    private int mileage;
    private final String transmission;
    private final String fuelType;
    private final String VIN;
    private int carsAvailable;         // number of cars available in the inventory 

    // Constructor 
    protected Car(int id, String carType, String model, String condition, double price, String color, int capacity, int mileage, String transmission, String fuelType, String VIN, int carsAvailable) {
        this.id = id;
        this.carType = carType;
        this.model = model;
        this.condition = condition;
        this.price = price;
        this.color = color;
        this.capacity = capacity;
        this.mileage = mileage;
        this.transmission = transmission;
        this.fuelType = fuelType;
        this.VIN = VIN;
        this.carsAvailable = carsAvailable;
    }

    // Setters
    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public void setCarsAvailable(int carsAvailable) {
        this.carsAvailable = carsAvailable;
    }

    // Getters 
    public int getId() {return this.id; }

    public String getCarType() {return this.carType;}

    public String getModel() {return this.model;}

    public String getCondition() {return this.condition;}

    public double getPrice() {return this.price;}

    public String getColor() {return this.color;}

    public int getCapacity() {return this.capacity;}

    public int getMileage() {return this.mileage;}

    public String getTransmission() {return this.transmission;}

    public String getFuelType() {return this.fuelType;}

    public String getVIN() {return this.VIN;}

    public int getCarsAvailable() {return this.carsAvailable;}

    // Print the car attributes and values 
    public void displayInformation(){
        System.out.println("-------------------------------------------");
        System.out.println("Car ID: " + this.id);
        System.out.println("Car Type: " + this.carType);
        System.out.println("Model: " + this.model);
        System.out.println("Condition: " + this.condition);
        System.out.println("Price: $" + this.price);
        System.out.println("Color: " + this.color);
        System.out.println("Capacity: " + this.capacity + " passengers");
        System.out.println("Mileage: " + this.mileage + " miles");
        System.out.println("Transmission: " + this.transmission);
        System.out.println("Fuel Type: " + this.fuelType);
        System.out.println("VIN: " + this.VIN);
        System.out.println("Cars Available: " + this.carsAvailable);
        System.out.println("-------------------------------------------");
    }

    // Method implemented by the subclasses to print car details specific for each car type (e.g., Sedan, SUV, Hatchback, etc.)
    public abstract void displayCarSpecs();
}