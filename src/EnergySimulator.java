import java.util.Random;

public class EnergySimulator {
    private Random random = new Random();

    // Simulate energy generation for wind turbine
    public int simulateWindTurbine() {
        return random.nextInt(1000);  // Generate random kWh between 0 and 1000
    }

    // Simulate energy generation for solar panel
    public int simulateSolarPanel() {
        return random.nextInt(800);  // Generate random kWh between 0 and 800
    }

    // Simulate energy consumption for lighting
    public int simulateLighting() {
        return random.nextInt(300);  // Consume random kWh between 0 and 300
    }

    // Simulate energy consumption for heating
    public int simulateHeating() {
        return random.nextInt(400);  // Consume random kWh between 0 and 400
    }

    // Simulate energy consumption for H-Bahn
    public int simulateHBahn() {
        return random.nextInt(200);  // Consume random kWh between 0 and 200
    }
}
