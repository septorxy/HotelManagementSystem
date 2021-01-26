package HotelSystem.BookingsManager;

import java.util.Date;

public class Service {

    private final Date dateOfBooking;
    private final Date time;
    private final String type;

    public Service(Date dateOfBooking, Date time, String type) {
        this.dateOfBooking = dateOfBooking;
        this.time = time;
        this.type = type;
    }

    public Date getDateOfBooking() {
        return dateOfBooking;
    }

    public Date getTime() {
        return time;
    }

    public String getType() {
        return type;
    }
}