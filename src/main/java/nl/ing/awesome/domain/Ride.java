package nl.ing.awesome.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Created on 1-3-18.
 */
@Getter
@Setter
public class Ride {

    private int rideNumber;

    private Position start;

    private Position end;

    private int earliestStart;

    private int latestFinish;

    private int cost;

    private int weight;

    private int score;

    private int vehicleStartPointStep;

    private int rideStartPointStep;

    private int actualStartStep;

    private int actualFinishStep;
}
