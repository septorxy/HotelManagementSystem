package HotelSystem.BookingsManager;

import java.util.Date;

public class Reservation {

    private Room[] roomsBooked;
    private String resID;
    private Date[] DatesOfStay;
    private int resOwnerID;
    private Service[] servicesBooked;

    public Reservation(Room[] roomsBooked, String resID, Date[] datesOfStay, int resOwnerID, Service[] servicesBooked){
        this.roomsBooked = roomsBooked;
        this.resID = resID;
        this.DatesOfStay = datesOfStay;
        this.resOwnerID = resOwnerID;
        this.servicesBooked = servicesBooked;
    }

    public String getResID() {
        return this.resID;
    }

    public Date[] getDatesOfStay() {
        return DatesOfStay;
    }

    public void setDatesOfStay(Date[] datesOfStay) {
        DatesOfStay = datesOfStay;
    }


    public void setServicesBooked() {
        this.servicesBooked = servicesBooked;
    }

    public Service[] getServicesBooked() {
        return servicesBooked;
    }

    public int getResOwnerID() {
        return resOwnerID;
    }

    public Room[] getRoomsBooked() {
        return roomsBooked;
    }
}