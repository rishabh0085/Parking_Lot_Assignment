import java.util.*;
class Vehicle {  // Vehicle Class
    private String registrationNumber;
    private String color;
    private String type;
    public Vehicle(String registrationNumber, String color, String type) {
        this.registrationNumber = registrationNumber;
        this.color = color;
        this.type = type;
    }
    public String getRegistrationNumber()
    {
        return registrationNumber;
    }
    public String getType()
    {
        return type;
    }
}

// VehicleSpace Class
class VehicleSpace {
    private int spaceNumber;
    private boolean isOccupied;
    private String vehicleType;
    private Vehicle vehicle;
    public VehicleSpace(int spaceNumber, String vehicleType) {
        this.spaceNumber = spaceNumber;
        this.vehicleType = vehicleType;
        this.isOccupied = false;
    }
    public boolean isOccupied() { return isOccupied; }
    public String getVehicleType() { return vehicleType; }
    public void parkVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.isOccupied = true;
    }
    public Vehicle removeVehicle() {
        Vehicle removedVehicle = this.vehicle;
        this.vehicle = null;
        this.isOccupied = false;
        return removedVehicle;
    }
}

// Floor Class
class Floor {
    private int floorNumber;
    private Map<String, List<VehicleSpace>> spaces;
    public Floor(int floorNumber, Map<String, Integer> capacity) {
        this.floorNumber = floorNumber;
        spaces = new HashMap<>();

        for (Map.Entry<String, Integer> entry : capacity.entrySet()) {
            String type = entry.getKey();
            int count = entry.getValue();
            List<VehicleSpace> spaceList = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                spaceList.add(new VehicleSpace(i + 1, type));
            }
            spaces.put(type, spaceList);
        }
    }
    public List<VehicleSpace> getSpaces(String vehicleType) {
        return spaces.getOrDefault(vehicleType, Collections.emptyList());
    }
}

// CostStrategy Class
class CostStrategy {
    private Map<String, Double> rates;
    public CostStrategy() {
        rates = new HashMap<>();
        rates.put("Bike", 10.0);
        rates.put("Car", 20.0);
        rates.put("Truck", 30.0);
    }
    public double calculateCost(String vehicleType, long hours) {
        return rates.getOrDefault(vehicleType, 0.0) * hours;
    }
}

// ParkingLot Class
class ParkingLot {
    private List<Floor> floors;
    private CostStrategy costStrategy;
    private Map<String, ParkingTicket> ticketMap;

    public ParkingLot(int numFloors, Map<String, Integer> capacityPerFloor) {
        floors = new ArrayList<>();
        ticketMap = new HashMap<>();
        costStrategy = new CostStrategy();

        for (int i = 0; i < numFloors; i++) {
            floors.add(new Floor(i + 1, capacityPerFloor));
        }
    }
    public String addVehicle(Vehicle vehicle) {
        for (Floor floor : floors) {
            List<VehicleSpace> spaces = floor.getSpaces(vehicle.getType());
            for (VehicleSpace space : spaces) {
                if (!space.isOccupied()) {
                    space.parkVehicle(vehicle);
                    String token = UUID.randomUUID().toString();
                    ticketMap.put(token, new ParkingTicket(token, System.currentTimeMillis(), space));
                    return token;
                }
            }
        }
        throw new IllegalStateException("Parking Lot is Full!");
    }
    public double removeVehicle(String token, long exitTime) {
        ParkingTicket ticket = ticketMap.remove(token);
        if (ticket == null) throw new IllegalArgumentException("Invalid Token!");

        VehicleSpace space = ticket.getSpace();
        space.removeVehicle();

        long hours = (exitTime - ticket.getEntryTime()) / (1000 * 60 * 60);
        return costStrategy.calculateCost(space.getVehicleType(), hours);
    }
    public void displayStatus() {
        for (int i = 0; i < floors.size(); i++) {
            System.out.println("Floor " + (i + 1) + ":");
            for (String type : Arrays.asList("Bike", "Car", "Truck")) {
                long occupied = floors.get(i).getSpaces(type).stream().filter(VehicleSpace::isOccupied).count();
                long total = floors.get(i).getSpaces(type).size();
                System.out.println(type + " -> Occupied: " + occupied + ", Available: " + (total - occupied));
            }
        }
    }
}

// ParkingTicket Class
class ParkingTicket {
    private String token;
    private long entryTime;
    private VehicleSpace space;

    public ParkingTicket(String token, long entryTime, VehicleSpace space)
    {
        this.token = token;
        this.entryTime = entryTime;
        this.space = space;
    }

    public long getEntryTime()
    {
        return entryTime;
    }
    public VehicleSpace getSpace()
    {
        return space;
    }
}

// Main Class
public class Main {
    public static void main(String[] args) {

        Map<String, Integer> capacityPerFloor = new HashMap<>();
        capacityPerFloor.put("Car", 2);
        capacityPerFloor.put("Bike", 2);
        capacityPerFloor.put("Truck", 1);
        ParkingLot parkingLot = new ParkingLot(4, capacityPerFloor);

        // Add Vehicles
        String token1 = parkingLot.addVehicle(new Vehicle("UP32AB1234", "Red", "Car"));
        String token2 = parkingLot.addVehicle(new Vehicle("UP32CD5678", "Blue", "Car"));
        System.out.println("Vehicle tokens: " + token1 + ", " + token2);


        parkingLot.displayStatus();

        // Remove Vehicle
        double cost = parkingLot.removeVehicle(token1, System.currentTimeMillis() + 7200000);
        System.out.println("Cost for vehicle: " + cost);

        // Display Status after removal
        parkingLot.displayStatus();
    }
}
