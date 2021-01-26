package HotelSystem.BookingsManager;

import java.util.Date;

public class Reservation {

    private final Room[] roomsBooked;
    private final String resID;
    private final Date[] DatesOfStay;
    private final int resOwnerID;
    private final Service[] servicesBooked;
    private final String status;

    public Reservation(Room[] roomsBooked, String resID, Date[] datesOfStay, int resOwnerID, Service[] servicesBooked, String status) {
        this.roomsBooked = roomsBooked;
        this.resID = resID;
        this.DatesOfStay = datesOfStay;
        this.resOwnerID = resOwnerID;
        this.servicesBooked = servicesBooked;
        this.status = status;
    }

    public String getResID() {
        return this.resID;
    }

    public Date[] getDatesOfStay() {
        return DatesOfStay;
    }

    public int getResOwnerID() {
        return resOwnerID;
    }

    public Room[] getRoomsBooked() {
        return roomsBooked;
    }

    public String getStatus() {
        return status;
    }
}