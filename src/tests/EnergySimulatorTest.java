package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import energymanagement.EnergySimulator;

public class EnergySimulatorTest {

    private EnergySimulator simulator = new EnergySimulator();

    @Test
    public void testSimulateWindTurbineRange() {
        int output = simulator.simulateWindTurbine();
        int minExpected = 1;
        int maxExpected = simulator.getMaxWindTurbineGeneration() / 2;

        assertTrue(output >= minExpected && output <= maxExpected,
                "Wind Turbine output should be between " + minExpected + " and " + maxExpected + ", but was " + output);
    }

    @Test
    public void testSimulateSolarPanelRange() {
        int output = simulator.simulateSolarPanel();
        int minExpected = 1;
        int maxExpected = simulator.getMaxSolarPanelGeneration() / 2;

        assertTrue(output >= minExpected && output <= maxExpected,
                "Solar Panel output should be between " + minExpected + " and " + maxExpected + ", but was " + output);
    }

    @Test
    public void testSimulateLightingRange() {
        int output = simulator.simulateLighting();
        int minExpected = 1;
        int maxExpected = simulator.getMaxLightingConsumption() / 2;

        assertTrue(output >= minExpected && output <= maxExpected,
                "Lighting consumption should be between " + minExpected + " and " + maxExpected + ", but was " + output);
    }

    @Test
    public void testSetAndGetMaxLightingConsumption() {
        int expected = 400;
        simulator.setMaxLightingConsumption(expected);
        int actual = simulator.getMaxLightingConsumption();

        assertEquals(expected, actual, "Max Lighting Consumption should be updated to " + expected);
        
    }

    @Test
    public void testSetAndGetMaxSolarPanelGeneration() {
        int expected = 1000;
        simulator.setSolarPanelGenerationRate(expected);
        int actual = simulator.getMaxSolarPanelGeneration();

        assertEquals(expected, actual, "Max Solar Panel Generation should be updated to " + expected);
    }

    
}
