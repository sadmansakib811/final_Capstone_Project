package energymanagement;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
public class EnergySimulator {
    private Random random = new Random();
    private AtomicBoolean simulationRunning = new AtomicBoolean(true);
    private static final String PROPERTIES_FILE_PATH = "initial_settings.properties";
    // Declare shared variables as volatile
    private volatile int maxLightingConsumption;
    private volatile int maxHeatingConsumption;
    private volatile int maxHBahnConsumption;
    private volatile int maxCarChargingConsumption;
    

    // Variables to track last consumption amounts
    private volatile int lastLightingConsumption = 0;
    private volatile int lastHeatingConsumption = 0;
    private volatile int lastHBahnConsumption = 0;
    private volatile int lastCarChargingConsumption = 0;

   


    private Thread lightingThread;
    private Thread heatingThread;
    private Thread hbahnThread;
    private Thread carChargingThread;


  
   
    // Setter methods for adjusting parameters
    public void setMaxLightingConsumption(int max) { this.maxLightingConsumption = max; }
    public void setMaxHeatingConsumption(int max) { this.maxHeatingConsumption = max; }
    public void setMaxHBahnConsumption(int max) { this.maxHBahnConsumption = max; }
    public void setMaxCarChargingConsumption(int max) { this.maxCarChargingConsumption = max; }
    

    // **Added Getter methods for max values**
    public int getMaxLightingConsumption() { return maxLightingConsumption; }
    public int getMaxHeatingConsumption() { return maxHeatingConsumption; }
    public int getMaxHBahnConsumption() { return maxHBahnConsumption; }
    public int getMaxCarChargingConsumption() { return maxCarChargingConsumption; }
    

    // Getter methods for consumption
    public int getLastLightingConsumption() { return lastLightingConsumption; }
    public int getLastHeatingConsumption() { return lastHeatingConsumption; }
    public int getLastHBahnConsumption() { return lastHBahnConsumption; }
    public int getLastCarChargingConsumption() { return lastCarChargingConsumption; }

   

    // Simulation methods
   
    public int simulateLighting() { return random.nextInt(maxLightingConsumption / 2) + 1; }
    public int simulateHeating() { return random.nextInt(maxHeatingConsumption / 2) + 1; }
    public int simulateHBahn() { return random.nextInt(maxHBahnConsumption / 2) + 1; }
    public int simulateCarCharging() { return random.nextInt(maxCarChargingConsumption / 2) + 1; }

    // Start the simulation
    public void startSimulation(Battery battery) {
        simulationRunning.set(true);

        

        lightingThread = new Thread(new EnergyConsumer(battery, "Lighting"));
        heatingThread = new Thread(new EnergyConsumer(battery, "Heating"));
        hbahnThread = new Thread(new EnergyConsumer(battery, "H-Bahn"));
        carChargingThread = new Thread(new EnergyConsumer(battery, "Car Charging Station"));

      
        lightingThread.start();
        heatingThread.start();
        hbahnThread.start();
        carChargingThread.start();
    }

    // Stop the simulation
    public void stopSimulation() {
        simulationRunning.set(false);
        try {
          
            lightingThread.join();
            heatingThread.join();
            hbahnThread.join();
            carChargingThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

   




    // Inner class for energy consumers By Farhana
    class EnergyConsumer implements Runnable {
        private Battery battery;
        private String consumerName;

        public EnergyConsumer(Battery battery, String consumerName) {
            this.battery = battery;
            this.consumerName = consumerName;
        }

        @Override

        public void run() {

            while (simulationRunning.get()) {
                int amount = 0;
                switch (consumerName) {
                    case "Lighting":
                        amount = simulateLighting();
                        lastLightingConsumption = amount;
                        break;
                    case "Heating":
                        amount = simulateHeating();
                        lastHeatingConsumption = amount;
                        break;
                    case "H-Bahn":
                        amount = simulateHBahn();
                        lastHBahnConsumption = amount;
                        break;
                    case "Car Charging Station":
                        amount = simulateCarCharging();
                        lastCarChargingConsumption = amount;
                        break;
                }
                battery.discharge(amount, consumerName);
                // Removed console output

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
