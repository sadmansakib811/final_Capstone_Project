package energymanagement;

public class Battery {
    private int capacity;
    private int currentCharge;

    public Battery(int capacity) {
        this.capacity = capacity;
        this.currentCharge = 0;
    }

    // Synchronized method to charge the battery
    public synchronized void charge(int amount, String source) {
        if (amount <= 0) {
            // If amount is zero or negative, do nothing
            return;
        }

        while (currentCharge >= capacity) {
            // Battery is full, wait until discharge occurs
            try {
                // Removed console output
                // System.out.println(source + " waiting to charge. Battery full.");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        int availableCapacity = capacity - currentCharge;
        int actualCharge = Math.min(amount, availableCapacity);
        currentCharge += actualCharge;
        // Removed console output
        // System.out.println(source + " charged " + actualCharge + " units. Current charge: " + currentCharge);
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

    public synchronized void setCapacity(int capacity) {
        this.capacity = capacity;
        // Removed console output
        // System.out.println("Battery capacity updated to: " + capacity);
        notifyAll();
    }

    public synchronized int getCapacity() {
        return capacity;
    }
}
