
import java.util.concurrent.atomic.AtomicInteger;

public class ParkingSystemSimulation {

    public static final Object PrintLock = new Object();
    private static int totalParkingSpots;
    private static AtomicInteger currentCarsInParking = new AtomicInteger(0);
    // بيراقب عدد العربيات المركونه
    private static AtomicInteger carsServedByAllGates = new AtomicInteger(0);
    // بيراقب عدد العربيات اللي اتخدمت من كل البوابات

    public static void initialize(int spots) {
        totalParkingSpots = spots;
    }

    public static synchronized boolean tryAcquireSpot() {
        if (currentCarsInParking.get() < totalParkingSpots) {// لو العربيات المركونه أقل من 4 بخليه يركن
            currentCarsInParking.incrementAndGet();
            return true;
        }
        return false;// لو أكبر من أو يساوي 4 مبخليهوش يركن
    }

    public static synchronized void releaseSpot() {
        currentCarsInParking.decrementAndGet();
    }

    public static int getCurrentCarsInParking() {
        return currentCarsInParking.get();
    }

    public static void incrementCarsServed() {
        carsServedByAllGates.incrementAndGet();
    }

    public static int getCarsServedByAllGates() {
        return carsServedByAllGates.get();
    }
}
