//This class stores RideParameters metadata
public class RideParameters {
    private int type; // RideParameters type
    private int firstParameter; 
    private int secondParameter;
    private int thirdParameter;

    public RideParameters() {
    }

    @Override
    public String toString() {
        return String.format("%d, %d, %d, %d", type, firstParameter, secondParameter, thirdParameter);
    }

    /*
     * This method fetches the type of command for execution - Insert, Print, Print_Range,
     * Get_Next_Ride, Cancel_Ride, Update_Trip
     */
    public int getType() {
        return type;
    }

    // This method assigns the command type to type variable
    public void setType(int type) {
        this.type = type;
    }

    //This method fetches the first parameter
    public int getFirstParameter() {
        return firstParameter;
    }

    //This method assigns the first parameter
    public void setFirstParameter(int firstParameter) {
        this.firstParameter = firstParameter;
    }

    //This method fetches the second parameter
    public int getSecondParameter() {
        return secondParameter;
    }

    // This method assigns the second parameter
    public void setSecondParameter(int secondParameter) {
        this.secondParameter = secondParameter;
    }

   // This method fetches the third parameter 
    public int getThirdParameter() {
        return thirdParameter;
    }

    // This method assigns the third parameter
    public void setThirdParameter(int thirdParameter) {
        this.thirdParameter = thirdParameter;
    }
}