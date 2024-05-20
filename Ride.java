// This class stores Ride metadata 
public class Ride  {
    private int rideNumber;
    private int rideCost;
    private int tripDuration;

    // parameterized constructor of Ride class
    public Ride(int rideNumber, int rideCost, int tripDuration) {
        this.rideNumber = rideNumber;
        this.rideCost = rideCost;
        this.tripDuration= tripDuration;
    }

    //copy constructor of Ride class
    public Ride(Ride Ride) {
        this.rideNumber = Ride.rideNumber;
        this.rideCost = Ride.rideCost;
        this.tripDuration= Ride.tripDuration;
    }

    // This method fetches the Ride number
    public int getRideNumber() {
        return rideNumber;
    }

    // This method fetches the trip duration
    public int getTripDuration() {
        return tripDuration;
    }

    //This method sets the trip duration
    public void setTripDuration(int tripDuration) {
        this.tripDuration= tripDuration;
    }

    //This method fetches the ride cost
    public int getRideCost() {
        return rideCost;
    }

    //This method sets the ride cost
    public void setRideCost(int rideCost) {
        this.rideCost= rideCost;
    }

}