package nl.ing.awesome;

import nl.ing.awesome.domain.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created on 1-3-18.
 */
class FileReaderWriter {

    private static final String IN_FOLDER = "in/";
    private static final String OUT_FOLDER = "out/";

    private static int simulationSteps = 500;

    private static ClassLoader classLoader = FileReaderWriter.class.getClassLoader();

    static Input readFile(final String testCase) {
        String inputFile = in(testCase);
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

    static void writeFile(String testCase, final List<Vehicle> vehicleList) {
        String outputFile = out(testCase);
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

    static void writeVehicleHistory(String testCase, List<Vehicle> vehicles, int simulationSteps) {
        String historyFile = history(testCase);
        int score = 0;
        FileReaderWriter.simulationSteps = simulationSteps;
        File history = new File(historyFile);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(history))) {
            for (Vehicle vehicle : vehicles) {
                writer.write(vehicle.getVehicleNumber() + "-");
                for (Ride ride : vehicle.getRides()) {
                    score += ride.getScore();
                    writer.write(ride.getRideNumber() + pick(ride) + wait(ride) + ride(ride));
                }
                writer.write('\n');
                writer.flush();
            }
            writeScore(testCase, score);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeScore(final String testCase, final int score) {
        File scoreFile = new File(scoreFile(testCase));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        String scoreLine = dateFormat.format(Calendar.getInstance().getTime()) + "\n" + testCase + ": " + score + "\n\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scoreFile, true))) {
            writer.append(scoreLine);
            writer.flush();
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

    private static String in(String which) {
        return IN_FOLDER + which + ".in";
    }

    private static String out(String which) {
        return OUT_FOLDER + which + ".out";
    }

    private static String history(String which) {
        return OUT_FOLDER + which + ".hi";
    }

    private static String scoreFile(String which) {
        return OUT_FOLDER + "score.sc";
    }

}
