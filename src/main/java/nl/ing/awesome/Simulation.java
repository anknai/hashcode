package nl.ing.awesome;

import nl.ing.awesome.domain.Input;
import nl.ing.awesome.domain.Position;
import nl.ing.awesome.domain.Ride;
import nl.ing.awesome.domain.Vehicle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

/**
 * Created on 1-3-18.
 */
class Simulation {

    private Input input;

    private boolean more = true;

    void simulate(String inputFile, String outputFile, String historyFile) {
        input = FileReaderWriter.readFile(inputFile);
        List<Vehicle> vehicles = allVehicles();
        while (more) {
            start(vehicles, input.getRides());
        }
        FileReaderWriter.writeFile(outputFile, vehicles);
        FileReaderWriter.writeVehicleHistory(historyFile, vehicles, input.getTotalSteps());
    }

    private List<Vehicle> allVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        System.out.println("Total vehicles " + input.getVehicleCount() + " Rides: " + input.getRideCount());
        Position position = new Position(0, 0);
        for (int i = 0; i < input.getVehicleCount(); i ++ ) {
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleNumber(i + 1);
            vehicle.setNextFreePosition(position);
            vehicles.add(vehicle);
        }
        return vehicles;
    }

    private void start(final List<Vehicle> vehicles, final List<Ride> rides) {
        more = false;
        vehicles.forEach(vehicle -> {
            TreeMap<Integer, StepsCalculator> availableRides = new TreeMap<>(Collections.reverseOrder());
            rides.forEach(ride -> {
                StepsCalculator stepsCalculator = new StepsCalculator(vehicle, ride, input.getBonus());
                int weight = stepsCalculator.getWeight();
                if (stepsCalculator.isValidRide()) {
                    availableRides.put(weight, stepsCalculator);
                }
            });

            //Get the one with least weight and reset more to true.
            if (!availableRides.isEmpty()) {
                more = true;
                StepsCalculator assignedRide = availableRides.firstEntry().getValue();
                assignedRide.assignVehicle();
                rides.remove(assignedRide.getRide());
            }
        });
    }
}
