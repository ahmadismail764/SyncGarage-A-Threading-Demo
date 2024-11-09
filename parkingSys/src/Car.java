import java.util.concurrent.Semaphore;
import java.util.List;


public class Car extends Thread{
    private String carId;
    private int arrivalTime;
    private int parkDuration;
    private Semaphore parkingSlots;
    private Gate gate;

    public Car(Gate gate, String carId, int arrivalTime, int parkDuration, Semaphore parkingSlot){
        this.gate = gate;
        this.carId = carId;
        this.arrivalTime = arrivalTime;
        this.parkDuration = parkDuration;
        this.parkingSlots = parkingSlot;
    }
// this override the run function which is in the Thread library
    @Override
    public void run(){
        // the main running logic of the problem and logging of event (Ex: arrival, waiting , park)
        //
    }

}
