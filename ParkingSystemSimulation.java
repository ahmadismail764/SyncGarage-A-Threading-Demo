
import java.util.concurrent.atomic.AtomicInteger;

public class ParkingSystemSimulation {
    public static final Object PrintLock = new Object();  // عاملين قفل عشان مانشتغلش في عربية غير أما اللي باخد منها الانبوت تخلص
    public static final Object parkingLock = new Object(); // مسئولة عن فانكشن ال wait , notify  عشان مش كل شوية الثريدز تسال في مكان ولا لا
    private static int totalParkingSpots;
    private static AtomicInteger currentCarsInParking = new AtomicInteger(0);
    private static AtomicInteger carsServedByAllGates = new AtomicInteger(0);



    public static void initialize(int spots) {
        totalParkingSpots = spots;
    }

    public static boolean tryAcquireSpot() {
        synchronized (parkingLock) {
            if (currentCarsInParking.get() < totalParkingSpots) {
                currentCarsInParking.incrementAndGet();
                return true;
            }
            return false;
        }
    }

    public static void releaseSpot() {
        synchronized (parkingLock) {
            currentCarsInParking.decrementAndGet();
            parkingLock.notify();
        }
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
