public class Car extends Thread {
    private String carId;
    private int arrivalTime;
    private int parkDuration;
    private Gate gate;

    public Car(Gate gate, String carId, int arrivalTime, int parkDuration) {
        this.gate = gate;
        this.carId = carId;
        this.arrivalTime = arrivalTime;
        this.parkDuration = parkDuration;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(arrivalTime * 1000);
            synchronized (ParkingSystemSimulation.PrintLock) {
                System.out.println("Car " + carId + " from " + gate.getName() + " arrived at time " + arrivalTime);
            }

            boolean parked = false;
            while (!parked) {
                synchronized (ParkingSystemSimulation.PrintLock) {
                    if (ParkingSystemSimulation.tryAcquireSpot()) {
                        gate.incrementCarsServed();
                        System.out.println("Car " + carId + " from " + gate.getName() + " parked. (Parking Status: "
                                + ParkingSystemSimulation.getCurrentCarsInParking() + " spots occupied)");
                        parked = true;
                    } else {
                        System.out.println("Car " + carId + " from " + gate.getName() + " waiting for a spot.");
                    }
                }

                // الانتظار حتى يتم إشعار السيارة بأن هناك مكان شاغر
                if (!parked) {
                    synchronized (ParkingSystemSimulation.parkingLock) {
                        ParkingSystemSimulation.parkingLock.wait();  // الانتظار حتى يتم تحرير مكان
                    }
                }
            }

            Thread.sleep(parkDuration * 1000);

            synchronized (ParkingSystemSimulation.PrintLock) {
                System.out.println("Car " + carId + " from " + gate.getName() + " left after " + parkDuration
                        + " units of time. (Parking Status: " + (ParkingSystemSimulation.getCurrentCarsInParking() - 1) + " spots occupied)");
                ParkingSystemSimulation.releaseSpot();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
