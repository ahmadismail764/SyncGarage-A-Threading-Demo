
public class Car extends Thread {

    private final String carId;
    private final Gate gate;
    private final int arrivalTime, parkDuration;

    public Car(Gate gate, String carId, int arrivalTime, int parkDuration) {
        this.gate = gate;
        this.carId = carId;
        this.arrivalTime = arrivalTime;
        this.parkDuration = parkDuration;
    }

    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public void run() {
        try {
            Thread.sleep(arrivalTime * 1000);
            System.out.println("Car " + carId + " from " + gate.getName() + " arrived at time " + arrivalTime);
            boolean parked = false;
            int waitCounter = 0;
            while (!parked) {
                synchronized (ParkingSystemSimulation.PrintLock) {
                    if (ParkingSystemSimulation.tryAcquireSpot()) {
                        gate.incrementCarsServed();
                        System.out.println("Car " + carId + " from " + gate.getName() + " parked. (Parking Status: "
                                + ParkingSystemSimulation.getCurrentCarsInParking() + " spots occupied)");
                        parked = true;
                    } else {
                        waitCounter++;
                    }
                }
                if (!parked) {
                    Thread.sleep(1000);
                }
            }
            System.out.println("Car " + carId + " from " + gate.getName() + " parked after waiting for " + waitCounter + " units of time.");
            Thread.sleep(parkDuration * 1000);
            ParkingSystemSimulation.releaseSpot();
            System.out.println("Car " + carId + " from " + gate.getName() + " left after " + parkDuration
                    + " units of time. (Parking Status: " + (ParkingSystemSimulation.getCurrentCarsInParking()) + " spots occupied)");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
