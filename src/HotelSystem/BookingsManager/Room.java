package HotelSystem.BookingsManager;

public class Room {
    private int roomNum;
    private String roomType;
    private boolean cleaned;

    public Room(int roomNum, String roomType, boolean cleaned){
        this.roomNum = roomNum;
        this.roomType = roomType;
        this.cleaned = cleaned;
    }

    public int getRoomNum() {
        return this.roomNum;
    }

    public void setCleaned(boolean cleaned) {
        this.cleaned = cleaned;
    }
}