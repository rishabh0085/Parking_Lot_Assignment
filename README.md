# Parking Lot Assignment

This project is a Parking Lot System implemented in Core Java. It allows managing parking spaces for different types of vehicles with flexible configurations and cost strategies. It supports scalable and loosely coupled design principles for ease of extension.

# Features

1.Initialize Parking Lot: Supports multiple floors and configurable spaces for each vehicle type.

2.Add Vehicle: Allows adding vehicles with type, registration number, and color. Generates a unique token for each vehicle.

3.Remove Vehicle: Vehicles can be removed using their unique token, and parking fees are calculated based on the duration.

4.Check Availability: Provides details of available and occupied spaces for each vehicle type on each floor.

5.Display Status: Displays the current status of all floors, showing occupied and available spaces.

6.Cost Strategy: Configurable pricing based on vehicle type and supports different rates.

7.Edge Cases Handled: Invalid tokens, full parking lot errors, and dynamic scalability.

# Technologies Used

1.Java (Core Java, Object-Oriented Programming)

2.Java Collections (Map, List, and HashMap for in-memory storage)

# Classes and Attrinutes

1. Vehicle

Represents a vehicle with:

Registration Number

Color

Type (Car, Bike, Truck, etc.)

2. VehicleSpace

Manages parking slots with:

Space Number

Availability Status

Vehicle Type

Parked Vehicle Details

3. Floor

Handles a floor with:

Floor Number

Configurable Spaces for each Vehicle Type

4. CostStrategy

Implements parking fee calculation:

Charges based on vehicle type

Example Rates:

Bike: ₹10/hour

Car: ₹20/hour

Truck: ₹30/hour

5. ParkingLot

Central management system for:

Adding and Removing Vehicles

Tracking Tokens and Time

Displaying Parking Status

Configuring Cost Strategy

6. ParkingTicket

Manages:

Unique Token for each parked vehicle

Entry Timestamp

Associated VehicleSpace

7. Main

Demonstrates end-to-end functionalities, including:

Initialization of Parking Lot

Adding and Removing Vehicles

Displaying Parking Lot Status

# Assumptions

1.Parking fees are based on hours (rounded down).

2.Vehicles are identified uniquely using tokens.

3.No database is used; Java Collections handle storage.

4.Vehicles are assigned spaces based on availability.

5.Floor capacity and costs are configurable during initialization.

# Future Enhancements

1.Database Integration: Replace in-memory storage with persistent storage.

2.UI Interface: Add graphical or web-based user interfaces.

3.Reservation System: Allow pre-booking of parking spaces.

4.Dynamic Pricing: Support surge pricing based on time or demand.

