package nl.ing.awesome;

import org.junit.Test;

/**
 * Created on 1-3-18.
 */
public class SimulationTest {

    private static final String CASE_A = "a_example";
    private static final String CASE_B = "b_should_be_easy";
    private static final String CASE_C = "c_no_hurry";
    private static final String CASE_D = "d_metropolis";
    private static final String CASE_E = "e_high_bonus";

    private Simulation simulation = new Simulation();

    @Test
    public void testExample() {
        simulation.simulate(CASE_A);
    }

    @Test
    public void testEasy() {
        simulation.simulate(CASE_B);
    }

    @Test
    public void testNoHurry() {
        simulation.simulate(CASE_C);
    }

    @Test
    public void testMetropolis() {
        simulation.simulate(CASE_D);
    }

    @Test
    public void testHighBonus() {
        simulation.simulate(CASE_E);
    }
}