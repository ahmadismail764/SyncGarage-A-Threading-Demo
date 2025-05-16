import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        File input = new File("input.txt");
        List<Car> cars = new ArrayList<>();

        if (!input.exists()) {
            System.out.println("file not found");
            return;
        }

        // Initialize shared resources (semaphore)
        ParkingSystemSimulation.initialize(4); // 4 parking slots

        Gate gate1 = new Gate("Gate 1", new ArrayList<>());
        Gate gate2 = new Gate("Gate 2", new ArrayList<>());
        Gate gate3 = new Gate("Gate 3", new ArrayList<>());

        try {
            cars = readInputFile("input.txt", gate1, gate2, gate3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Let each one start and join separately
        gate1.startCarThreads();
        gate2.startCarThreads();
        gate3.startCarThreads();

        gate1.joinAllThread();
        gate2.joinAllThread();
        gate3.joinAllThread();

        System.out.println("Simulation Report:");
        System.out.println("Total Cars Served: " + ParkingSystemSimulation.getCarsServedByAllGates());
        System.out.println("Current Cars in Parking: " + ParkingSystemSimulation.getCurrentCarsInParking());
        System.out.println("- " + gate1.getName() + " served " + gate1.getCarsServedThisGate() + " cars.");
        System.out.println("- " + gate2.getName() + " served " + gate2.getCarsServedThisGate() + " cars.");
        System.out.println("- " + gate3.getName() + " served " + gate3.getCarsServedThisGate() + " cars.");
    }

    private static List<Car> readInputFile(String filename, Gate gate1, Gate gate2, Gate gate3) throws IOException {
        List<Car> carList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");

                String gateName = parts[0].split(" ")[1];
                String carId = parts[1].split(" ")[1];
                int arrivalTime = Integer.parseInt(parts[2].split(" ")[1]);
                int parkDuration = Integer.parseInt(parts[3].split(" ")[1]);

                Gate assignedGate;
                assignedGate = switch (gateName) {
                    case "1" ->
                            gate1;
                    case "2" ->
                            gate2;
                    default ->
                            gate3;
                };

                Car car = new Car(assignedGate, carId, arrivalTime, parkDuration);
                carList.add(car);
                assignedGate.addCar(car);
            }
        }

        return carList;
    }
}
