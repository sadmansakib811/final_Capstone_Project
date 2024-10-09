import java.util.Random;

public class EnergySimulator {
    private Random random = new Random();

    // Simulate wind turbine
    public int simulateWindTurbine() {
        return random.nextInt(1000);  
    }

    // Simulate  solar panel
    public int simulateSolarPanel() {
        return random.nextInt(800);  
    }

    // Simulate energy consumption for lighting
    public int simulateLighting() {
        return random.nextInt(300);  
    }

    // Simulate energy consumption for heating
    public int simulateHeating() {
        return random.nextInt(400); 
    }

    // Simulate energy consumption for H-Bahn
    public int simulateHBahn() {
        return random.nextInt(200);  
    }
}
