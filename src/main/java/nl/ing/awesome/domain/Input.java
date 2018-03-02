package nl.ing.awesome.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 1-3-18.
 */
@Setter
@Getter
public class Input {
    private Grid grid;

    private int vehicleCount;

    private int rideCount;

    private int bonus;

    private int totalSteps;

    private List<Ride> rides = new ArrayList<>();
}
