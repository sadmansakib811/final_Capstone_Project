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
        if (amount <= 0) {
            // If amount is zero or negative, do nothing
            return;
        }

        while (currentCharge <= 0) {
            // Battery is empty, wait until charge occurs
            try {
                // Removed console output
                // System.out.println(consumer + " waiting to discharge. Battery empty.");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        int actualDischarge = Math.min(amount, currentCharge);
        currentCharge -= actualDischarge;
        // Removed console output
        // System.out.println(consumer + " discharged " + actualDischarge + " units. Current charge: " + currentCharge);
        notifyAll();
    }

   

    public synchronized int getCurrentCharge() {
        return currentCharge;
    }

    public int getCapacity() {
        return capacity;
    }
}
