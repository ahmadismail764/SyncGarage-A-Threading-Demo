import java.util.concurrent.Semaphore;
import java.util.List;

public class Gate{
    private String gateName; // EX: gate1
    private List<Car> cars; // list of the cars waiting on the gate
    private Semaphore parkingSlots; // to have 4 slots add (Semaphore parkingLot = new Semaphore(4)) to main
    private int carsServedThisGate = 0; // counts cars served by this gate instance
    private static int CarsServedByAllGates = 0; // counts cars served by all gates
    private static int occupiedSlots = 0; // tracks the number of occupied slot which is needed in the output when a car leaves or arrives

    // this constructor initialize the gate object
    public Gate(String gateName, List<Car> cars, Semaphore parkingSlot){
        this.gateName = gateName;
        this.cars = cars;
        this.parkingSlots = parkingSlot;
    }
    // we may need some getters
    // cars uses the gate names
    public String getName(){
        return this.gateName;
    }

    public void startCarThreads(){}

    public void joinAllThread(){}
}





// this explains the line 7 (private Semaphore parkingSlots;)

//How This Handles Cars from Multiple Gates:
//Shared Semaphore: The Semaphore parkingLot is shared by all the gates.
// This means that any car trying to park, regardless of which gate it comes from, must first try to acquire a permit from the semaphore.
//
//If a car arrives and there is a free spot (a permit available), it can acquire the permit and park.
//If no spots are available (i.e., all permits are acquired), the car must wait for a permit to be released by a car that is leaving (a parked car must release its permit when it departs).
//Interaction Between Gates:
//        Multiple Gates, Same Semaphore: Since all gates use the same semaphore, the number of cars parked at any time is governed by the total number of available slots (4 in this case), regardless of which gate the cars belong to. The semaphore doesn't distinguish between which gate a car comes from.
//For example, if 2 cars are parked at Gate 1 and 2 cars are parked at Gate 2, the semaphore will still only have 0 permits available, and any new car trying to park will have to wait.