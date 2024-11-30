
import java.util.List;

public class Gate {

    private final String gateName;
    private List<Car> cars;
    private int carsServedThisGate = 0;

    public Gate(String gateName, List<Car> cars) {
        this.gateName = gateName;
        this.cars = cars;
    }

    public String getName() {
        return this.gateName;
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
        ParkingSystemSimulation.incrementCarsServed();
    }

    public int getCarsServedThisGate() {
        return carsServedThisGate;
    }
}
