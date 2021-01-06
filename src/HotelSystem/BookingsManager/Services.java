package HotelSystem.BookingsManager;

import java.util.Date;

public class Services {

    private Date DateOfBooking;
    private Date Time;
    private String serviceID;

    public String getServiceID() {
        return this.serviceID;
    }

    /**
     *
     * @param serviceID
     */
    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

}