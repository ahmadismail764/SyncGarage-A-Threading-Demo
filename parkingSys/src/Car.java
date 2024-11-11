import java.util.concurrent.Semaphore;

public class Car extends Thread {
    private String carId;
    private int arrivalTime;
    private int parkDuration;
    private Semaphore parkingSlots;
    private Gate gate;

    public Car(Gate gate, String carId, int arrivalTime, int parkDuration, Semaphore parkingSlot) {
        this.gate = gate;
        this.carId = carId;
        this.arrivalTime = arrivalTime;
        this.parkDuration = parkDuration;
        this.parkingSlots = parkingSlot;
    }

    @Override
    public void run() {
        try {
            // Simulate arrival time
            Thread.sleep(arrivalTime * 1000);
            System.out.println("Car " + carId + " from " + gate.getName() + " arrived at time " + arrivalTime);

            // Try to acquire a parking slot
            if (parkingSlots.tryAcquire()) {
                gate.incrementOccupiedSlots();
                System.out.println("Car " + carId + " from " + gate.getName() + " parked. (Parking Status: " + Gate.getOccupiedSlots() + " spots occupied)");

                // Simulate parking duration
                Thread.sleep(parkDuration * 1000);

                // Release the slot after parking
                parkingSlots.release();
                gate.decrementOccupiedSlots();
                System.out.println("Car " + carId + " from " + gate.getName() + " left after " + parkDuration + " units of time. (Parking Status: " + Gate.getOccupiedSlots() + " spots occupied)");

                // Update served car count
                gate.incrementCarsServed();
            } else {
                int waitCounter = 0;
                System.out.println("Car " + carId + " from " + gate.getName() + " waiting for a spot.");
                while(Gate.getOccupiedSlots() > 3){
                    Thread.sleep(1000);
                    waitCounter++;
                }
                // Wait for a parking slot
                parkingSlots.acquire();
                gate.incrementOccupiedSlots();
                System.out.println("Car " + carId + " from " + gate.getName() + " parked after waiting " + waitCounter + " . (Parking Status: " + Gate.getOccupiedSlots() + " spots occupied)");

                // Simulate parking duration
                Thread.sleep(parkDuration * 1000);

                // Release the slot after parking
                parkingSlots.release();
                gate.decrementOccupiedSlots();
                System.out.println("Car " + carId + " from " + gate.getName() + " left after " + parkDuration + " units of time. (Parking Status: " + Gate.getOccupiedSlots() + " spots occupied)");

                gate.incrementCarsServed();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
