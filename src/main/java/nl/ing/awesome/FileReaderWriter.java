package nl.ing.awesome;

import nl.ing.awesome.domain.Grid;
import nl.ing.awesome.domain.Input;
import nl.ing.awesome.domain.Position;
import nl.ing.awesome.domain.Ride;
import nl.ing.awesome.domain.Vehicle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 1-3-18.
 */
class FileReaderWriter {

    private static int simulationSteps = 500;

    private static ClassLoader classLoader = FileReaderWriter.class.getClassLoader();

    static Input readFile(final String inputFile) {
        File file = new File(classLoader.getResource(inputFile).getFile());
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(file))) {
            String line;

            Grid grid;
            Input input;

            grid = new Grid();
            input = new Input();
            List<Ride> rideList = new ArrayList<>();
            // First line baby!
            if ((line = reader.readLine()) != null) {
                String[] values = line.split(" ");
                grid.setRows(new Integer(values[0]));
                grid.setColumns(new Integer(values[1]));
                input.setGrid(grid);
                input.setVehicleCount(new Integer(values[2]));
                input.setRideCount(new Integer(values[3]));
                input.setBonus(new Integer(values[4]));
                input.setTotalSteps(new Integer(values[5]));
            }

            int count = 1;
            // All the rides
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(" ");
                Ride r = new Ride();
                r.setRideNumber(count - 1);
                r.setStart(new Position(new Integer(values[0]),new Integer(values[1])));
                r.setEnd(new Position(new Integer(values[2]),new Integer(values[3])));
                r.setEarliestStart(new Integer(values[4]));
                r.setLatestFinish(new Integer(values[5]));
                rideList.add(r);
                count++;
            }
            input.setRides(rideList);
            return input;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static void writeFile(String outputFile, final List<Vehicle> vehicleList) {
        File output = new File(outputFile);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
            vehicleList.iterator().forEachRemaining(vehicle -> {
                try {
                    writer.write(vehicle.getRides().size() + " " + vehicle.getAllRides());
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void writeVehicleHistory(String historyFile, List<Vehicle> vehicles, int simulationSteps) {
        FileReaderWriter.simulationSteps = simulationSteps;
        File history = new File(historyFile);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(history))) {
            for (Vehicle vehicle : vehicles) {
                writer.write(vehicle.getVehicleNumber() + "-");
                for (Ride ride : vehicle.getRides()) {
                    writer.write(ride.getRideNumber() + pick(ride) + wait(ride) + ride(ride));
                }
                writer.write('\n');
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String pick(final Ride ride) {
        int pickSteps = ride.getRideStartPointStep() - ride.getVehicleStartPointStep();
        return convert(pickSteps, '.');
    }

    private static String wait(final Ride ride) {
        int pickSteps = ride.getActualStartStep() - ride.getRideStartPointStep();
        return convert(pickSteps, '|');
    }

    private static String ride(final Ride ride) {
        int pickSteps = ride.getActualFinishStep() - ride.getActualStartStep();
        return convert(pickSteps, '-');
    }

    private static String convert(int number, char c) {
        int step = simulationSteps / 200;
        if (step > 1) {
            number = number / step;
        }
        number = (number == 0) ? 1: number;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < number; i ++) {
            builder.append(c);
        }
        return builder.toString();
    }
}
