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

   

    public synchronized int getCurrentCharge() {
        return currentCharge;
    }

    public int getCapacity() {
        return capacity;
    }
}
