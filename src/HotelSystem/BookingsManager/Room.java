package HotelSystem.BookingsManager;

public class Room {

    private int roomNum;
    private String roomType;
    private boolean cleaned;
    private boolean booked;

    public int getRoomNum() {
        return this.roomNum;
    }

    public void setCleaned(boolean cleaned) {
        this.cleaned = cleaned;
    }
}