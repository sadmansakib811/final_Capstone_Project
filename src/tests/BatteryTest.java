package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import energymanagement.Battery;

public class BatteryTest {

    private Battery battery;

    @BeforeEach
    public void setUp() {
        battery = new Battery(1000); // Initialize battery with capacity of 1000 units
    }

    @Test
    public void testInitialCharge() {
        assertEquals(0, battery.getCurrentCharge(), "Initial battery charge should be zero");
    }

    @Test
    public void testChargeWithinCapacity() {
        battery.charge(500, "TestSource");
        assertEquals(500, battery.getCurrentCharge(), "Battery charge should be 500 after charging 500 units");
    }

    @Test
    public void testChargeExceedingCapacity() {
        battery.charge(1200, "TestSource");
        assertEquals(1000, battery.getCurrentCharge(), "Battery charge should not exceed capacity");
    }

    @Test
    public void testDischargeWithinCurrentCharge() {
        battery.charge(800, "TestSource");
        battery.discharge(300, "TestConsumer");
        assertEquals(500, battery.getCurrentCharge(), "Battery charge should be 500 after discharging 300 units");
    }

    @Test
    public void testDischargeExceedingCurrentCharge() {
        battery.charge(200, "TestSource");
        battery.discharge(500, "TestConsumer");
        assertEquals(0, battery.getCurrentCharge(), "Battery charge should not go negative");
    }

    
}
