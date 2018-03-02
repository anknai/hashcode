package nl.ing.awesome.domain;

import lombok.Getter;
import lombok.Setter;
import nl.ing.awesome.StepsCalculator;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Vehicle {

    private int vehicleNumber;

    private List<Ride> rides = new ArrayList<>();

    private int nextFreeStep;

    private Position nextFreePosition;

    public String getAllRides() {
        String rideNumbers = "";
        for (Ride ride : rides) {
            rideNumbers = rideNumbers.concat(ride.getRideNumber() + " ");
        }
        return rideNumbers;
    }

    public void addRide(final Ride ride) {
        rides.add(ride);
        nextFreePosition = ride.getEnd();
        nextFreeStep += ride.getCost();
    }
}
