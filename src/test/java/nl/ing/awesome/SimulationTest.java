package nl.ing.awesome;

import org.junit.Test;

/**
 * Created on 1-3-18.
 */
public class SimulationTest {
    private static final String FOLDER = "/tmp/hc/";
    private static final String A_FILE = "a_example";
    private static final String B_FILE = "b_should_be_easy";
    private static final String C_FILE = "c_no_hurry";
    private static final String D_FILE = "d_metropolis";
    private static final String E_FILE = "e_high_bonus";

    private Simulation simulation = new Simulation();

    @Test
    public void testExample() {
        simulation.simulate(in(A_FILE), out(A_FILE), history(A_FILE));
    }

    @Test
    public void testEasy() {
        simulation.simulate(in(B_FILE), out(B_FILE), history(B_FILE));
    }

    @Test
    public void testNoHurry() {
        simulation.simulate(in(C_FILE), out(C_FILE), history(C_FILE));
    }

    @Test
    public void testMetropolis() {
        simulation.simulate(in(D_FILE), out(D_FILE), history(D_FILE));
    }

    @Test
    public void testHighBonus() {
        simulation.simulate(in(E_FILE), out(E_FILE), history(E_FILE));
    }

    private String in(String which) {
        return FOLDER + which + ".in";
    }

    private String out(String which) {
        return FOLDER + which + ".out";
    }

    private String history(String which) {
        return FOLDER + which + ".hi";
    }
}