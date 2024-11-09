import java.util.List;
import java.util.concurrent.Semaphore;

public class Gate {
    private String gateName;
    private List<Car> cars;
    private Semaphore parkingSlots;
    private int carsServedThisGate = 0;
    private static int CarsServedByAllGates = 0;
    private static int occupiedSlots = 0;

    public Gate(String gateName, List<Car> cars, Semaphore parkingSlot) {
        this.gateName = gateName;
        this.cars = cars;
        this.parkingSlots = parkingSlot;
    }

    public String getName() {
        return this.gateName;
    }

    public Semaphore getParkingSlots() {
        return this.parkingSlots;
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void startCarThreads() {
        for (Car car : cars) {
            car.start();
        }
    }

    public void joinAllThread() {
        for (Car car : cars) {
            try {
                car.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void incrementCarsServed() {
        carsServedThisGate++;
        CarsServedByAllGates++;
    }

    public int getCarsServedThisGate() {
        return carsServedThisGate;
    }

    public static int getCarsServedByAllGates() {
        return CarsServedByAllGates;
    }

    public static synchronized void incrementOccupiedSlots() {
        occupiedSlots++;
    }

    public static synchronized void decrementOccupiedSlots() {
        occupiedSlots--;
    }

    public static int getOccupiedSlots() {
        return occupiedSlots;
    }
}
