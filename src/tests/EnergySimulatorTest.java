package tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import energymanagement.EnergySimulator;

public class EnergySimulatorTest {

    private EnergySimulator simulator;
    @BeforeAll
    public static void init() {
        // This will print the message once before all tests
        System.out.println("EnergySimulator.java class test result:");
    }
    @BeforeEach
    public void setUp() {
    	
        simulator = new EnergySimulator();
      
    }
   
    @Test
    public void testSimulateWindTurbineRange() {
        System.out.println("Starting test: testSimulateWindTurbineRange");
        int output = simulator.simulateWindTurbine();
        assertTrue(output >= 0 && output < 1000, "Wind Turbine output should be between 0 and 999");
        System.out.println("Test passed: testSimulateWindTurbineRange");
      
    }

    @Test
    public void testSimulateSolarPanelRange() {
        System.out.println("Starting test: testSimulateSolarPanelRange");
        int output = simulator.simulateSolarPanel();
        assertTrue(output >= 0 && output < 800, "Solar Panel output should be between 0 and 799");
        System.out.println("Test passed: testSimulateSolarPanelRange");
        
    }

    @Test
    public void testSimulateLightingRange() {
        System.out.println("Starting test: testSimulateLightingRange");
        int output = simulator.simulateLighting();
        assertTrue(output >= 0 && output < 300, "Lighting consumption should be between 0 and 299");
        System.out.println("Test passed: testSimulateLightingRange");
       
    }

    @Test
    public void testSimulateHeatingRange() {
        System.out.println("Starting test: testSimulateHeatingRange");
        int output = simulator.simulateHeating();
        assertTrue(output >= 0 && output < 400, "Heating consumption should be between 0 and 399");
        System.out.println("Test passed: testSimulateHeatingRange");
     
    }

    @Test
    public void testSimulateHBahnRange() {
        System.out.println("Starting test: testSimulateHBahnRange");
        int output = simulator.simulateHBahn();
        assertTrue(output >= 0 && output < 200, "H-Bahn consumption should be between 0 and 199");
        System.out.println("Test passed: testSimulateHBahnRange");
        
    }
    @AfterAll
    public static void tearDown() {
    
        System.out.println("***************************************************************************************");
    }
}
