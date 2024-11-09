import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        File input = new File("input.txt");
        List<Car> cars = new ArrayList<>();

        if (!input.exists()) {
            System.out.println("file not found");
            return;
        }

        Semaphore parkingLot = new Semaphore(4);  // Shared semaphore for parking slots
        Gate gate1 = new Gate("Gate 1", new ArrayList<>(), parkingLot);
        Gate gate2 = new Gate("Gate 2", new ArrayList<>(), parkingLot);
        Gate gate3 = new Gate("Gate 3", new ArrayList<>(), parkingLot);

        try {
            cars = readInputFile("input", gate1, gate2, gate3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        gate1.startCarThreads();
        gate2.startCarThreads();
        gate3.startCarThreads();

        gate1.joinAllThread();
        gate2.joinAllThread();
        gate3.joinAllThread();

        System.out.println("Simulation Report:");
        System.out.println("Total Cars Served: " + Gate.getCarsServedByAllGates());
        System.out.println("Current Cars in Parking: " + Gate.getOccupiedSlots());
        System.out.println("- " + gate1.getName() + " served " + gate1.getCarsServedThisGate() + " cars.");
        System.out.println("- " + gate2.getName() + " served " + gate2.getCarsServedThisGate() + " cars.");
        System.out.println("- " + gate3.getName() + " served " + gate3.getCarsServedThisGate() + " cars.");
    }

    // Helper method to read the input file
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
                if (gateName.equals("1")) assignedGate = gate1;
                else if (gateName.equals("2")) assignedGate = gate2;
                else assignedGate = gate3;

                Car car = new Car(assignedGate, carId, arrivalTime, parkDuration, assignedGate.getParkingSlots());
                carList.add(car);
                assignedGate.addCar(car);
            }
        }

        return carList;
    }
}
