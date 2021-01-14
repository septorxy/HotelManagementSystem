package HotelSystem.BookingsManager;

import java.util.Date;

public class Reservation {

    private Room[] roomsBooked;
    private String resID;
    private Date[] DatesOfStay;
    private int resOwnerID;
    private Service[] servicesBooked;

    public String getResID() {
        return this.resID;
    }

    public Date[] getDatesOfStay() {
        return DatesOfStay;
    }

    public void setDatesOfStay(Date[] datesOfStay) {
        DatesOfStay = datesOfStay;
    }
}