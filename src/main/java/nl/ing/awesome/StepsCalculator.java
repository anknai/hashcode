package nl.ing.awesome;

import lombok.Getter;
import nl.ing.awesome.domain.Position;
import nl.ing.awesome.domain.Ride;
import nl.ing.awesome.domain.Vehicle;

@Getter
public class StepsCalculator {

    private Vehicle vehicle;

    private Ride ride;

    private int bonus;

    private int cost = 0;

    private int score = 0;

    private int weight = 0;

    private boolean validRide;

    StepsCalculator(Vehicle vehicle, Ride ride, int bonus) {
        this.vehicle = vehicle;
        this.ride = ride;
        this.bonus = bonus;
        cost = pickupSteps() + waitSteps() + rideSteps();
        score = calculateScore();
        weight = calculateWeight();
        validRide = canPick();
    }

    private int rideSteps() {
        return calculateDistance(ride.getStart(), ride.getEnd());
    }

    private int pickupSteps() {
        return calculateDistance(vehicle.getNextFreePosition(), ride.getStart());
    }

    private int waitSteps() {
        int wait = ride.getEarliestStart() - (vehicle.getNextFreeStep() + pickupSteps());
        if (wait < 0){
            return 0;
        }
        return wait;
    }

    private static int calculateDistance(Position start, Position end) {
        int rDiff = Math.abs(end.getRow() - start.getRow());
        int cDiff = Math.abs(end.getColumn() - start.getColumn());
        return rDiff + cDiff;
    }

    private int calculateScore() {
        score  = 0;
        if (!canPick()) {
            return score;
        }
        score += rideSteps();
        if (ride.getEarliestStart() >= vehicle.getNextFreeStep() + pickupSteps()) {
            score += bonus;
        }
        return score;
    }

    private int calculateWeight() {
        double points = (double) score / cost;
        return (int) (points * 100);
    }

    private boolean canPick() {
        return ride.getLatestFinish() >= vehicle.getNextFreeStep() + cost;
    }

    void assignVehicle() {
        ride.setCost(cost);
        ride.setWeight(weight);
        ride.setScore(score);
        ride.setVehicleStartPointStep(vehicle.getNextFreeStep());
        ride.setRideStartPointStep(vehicle.getNextFreeStep() + pickupSteps());
        ride.setActualStartStep(ride.getRideStartPointStep() + waitSteps());
        ride.setActualFinishStep(ride.getActualStartStep() + rideSteps());
        vehicle.addRide(ride);

        System.out.println("Assigned vehicle " + vehicle.getVehicleNumber() + " to ride " + ride.getRideNumber() + " at " + vehicle.getNextFreeStep() + " for cost " + ride.getCost() + " weight " + ride.getWeight());
    }
}
