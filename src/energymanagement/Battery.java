package energymanagement;

public class Battery {
    private int capacity;       
    private int currentCharge;  

    public Battery(int capacity) {
        this.capacity = capacity;
        this.currentCharge = 0; 
    }

    // Synchronized method to charge the battery-sadman
    public synchronized void charge(int amount, String source) {
        while (currentCharge + amount > capacity) {
            // Prevent overcharging
            try {
                System.out.println(source + " waiting to charge. Battery full.");
                wait(); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        currentCharge += amount;
        System.out.println(source + " charged " + amount + " units. Current charge: " + currentCharge);
        notifyAll();
    }
    
 // Synchronized method to discharge the battery
    public synchronized void discharge(int amount, String consumer) {
        while (currentCharge - amount < 0) {
            // Prevent over-discharging
            try {
                System.out.println(consumer + " waiting to discharge. Battery empty.");
                wait(); // Wait until there's enough charge
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        currentCharge -= amount;
        System.out.println(consumer + " discharged " + amount + " units. Current charge: " + currentCharge);
        notifyAll(); // Notify other threads that may be waiting
    }

   

    public synchronized int getCurrentCharge() {
        return currentCharge;
    }

    public int getCapacity() {
        return capacity;
    }
}
