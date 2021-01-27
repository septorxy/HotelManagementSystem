package Storage.Database;

import HotelSystem.BookingsManager.Reservation;
import HotelSystem.BookingsManager.Room;
import HotelSystem.BookingsManager.Service;
import PersonBuilder.Customer;
import Resources.UI;
import Resources.Validate;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class StorageCustom {
    private Connection con = null;
    UI ui = new UI();

    public StorageCustom() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/customerdb", "test", "test");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addNewCustom(String IDs, String name, String surname, String login, String password, String email) {
        int ID = Integer.parseInt(IDs);
        String query = "INSERT INTO `customers` (`customerID`, `name`, `surname`, `login`, `password`, `email`) VALUES(" + "'" + ID + "', '" + name + "', '" + surname + "', '" + login + "', '" + password + "', '" + email + "')";
        try {
            Statement stmt = con.createStatement();
            stmt.executeQuery(query);
        } catch (Exception E) {
            System.out.println(E);
        }
    }

    public boolean existsCustomer(int ID) {
        String query = "SELECT * FROM `customers` WHERE customerID = " + ID;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return rs.isBeforeFirst(); //Returns true if found and false if not found
        } catch (Exception E) {
            System.out.println(E);
        }
        return false;
    }

    public boolean existsResID(String resID) {
        String query = "SELECT * FROM `reserves` WHERE resID = '" + resID + "'";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return rs.isBeforeFirst(); //Returns true if found and false if not found
        } catch (Exception E) {
            System.out.println(E);
        }
        return false;
    }

    public Customer getCustomer(String login, String password) {
        String query = "SELECT * FROM `customers` WHERE login = '" + login + "' AND password = '" + password + "'";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.isBeforeFirst()) {
                rs.next();
                return new Customer(rs.getString("name"), rs.getString("surname"), login, password, rs.getInt("customerID"), rs.getString("email"));

            } else {
                return null;
            }
        } catch (Exception E) {
            System.out.println(E);
        }
        return null;
    }

    public void newReservation(Reservation res) {
        java.util.Date[] dates = res.getDatesOfStay();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Room[] rooms = res.getRoomsBooked();
        String query = "INSERT INTO `reserves` (customerID, resID, `Date In`, `Date Out`, `room1`, `room2`, `room3`, `status`) VALUES ('" + res.getResOwnerID() + "', '" + res.getResID() + "', '" + dateFormat.format(dates[0]) + "', '" + dateFormat.format(dates[1]) + "', '" + (rooms[0] != null ? rooms[0].getRoomNum() : 0) + "', '" + (rooms[1] != null ? rooms[1].getRoomNum() : 0) + "', '" + (rooms[2] != null ? rooms[2].getRoomNum() : 0) + "', '" + res.getStatus() + "')";

        try {
            Statement stmt = con.createStatement();
            stmt.executeQuery(query);
        } catch (Exception E) {
            System.out.println(E);
        }
    }

    public Room[] getAvailRooms(String type, int numOfRooms, Date checkIn, Date checkOut) {
        Room[] roomsToReturn = new Room[3];
        String query = "SELECT * FROM `rooms` WHERE RoomType = '" + type + "'";
        boolean cleared;
        String query2;
        int currRoomNum;
        Date dateIn;
        Date dateOut;
        int initialVal, lastRow;

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.last();
            lastRow = rs.getInt("RoomNum");
            rs.beforeFirst();
            rs.next();
            initialVal = rs.getInt("RoomNum");
            boolean full = false;
            for (int i = initialVal; i < (int) (Math.random() * lastRow - initialVal - 2) + initialVal; i++) {
                rs.next();
            }
            for (int i = 0; i < numOfRooms; i++) {
                roomsToReturn[i] = new Room(rs.getInt("RoomNum"), type, rs.getBoolean("cleaned"));
                currRoomNum = roomsToReturn[i].getRoomNum();
                query2 = "SELECT * FROM `reserves` WHERE room1 = '" + currRoomNum + "' OR room2 = '" + currRoomNum + "' OR room3 = '" + currRoomNum + "'";
                ResultSet rs2 = stmt.executeQuery(query2);
                if (rs2.isBeforeFirst()) {
                    while (rs2.next()) {
                        dateIn = rs2.getDate("Date In");
                        dateOut = rs2.getDate("Date Out");
                        boolean isBefore = checkIn.compareTo(dateIn) < 0 && checkOut.compareTo(dateIn) < 0;
                        boolean isAfter = (checkIn.compareTo(dateOut) > 0 && checkOut.compareTo(dateOut) > 0);
                        cleared = ((isBefore || isAfter) && !Validate.existsIn(roomsToReturn, currRoomNum, i));
                        if (!cleared) {
                            roomsToReturn[i] = null;
                            i--;
                            break;
                        }
                    }
                } else if (Validate.existsIn(roomsToReturn, currRoomNum, i)) {
                    roomsToReturn[i] = null;
                    i--;
                }
                if (!rs.next()) {
                    rs.beforeFirst();
                    rs.next();
                    if (!full) {
                        full = true;
                    } else {
                        ui.showError("There are no rooms of this type available. Please select different dates or a different room type");
                        return null;
                    }
                }
            }
            return roomsToReturn;
        } catch (Exception E) {
            System.out.println(E);
        }
        return null;
    }

    public Reservation getReservation(String resID) {
        String query = "SELECT * FROM `reserves` WHERE resID = '" + resID + "'";

        try {
            Statement stmt = con.createStatement();
            if (existsResID(resID)) {
                ResultSet rs = stmt.executeQuery(query);
                rs.next();
                String query2 = "SELECT `RoomType` From rooms WHERE RoomNum = '" + rs.getInt("room1") + "'";
                ResultSet rs2 = stmt.executeQuery(query2);
                rs2.next();
                String roomType = rs2.getString("RoomType");
                return new Reservation(new Room[]{new Room(rs.getInt("room1"), roomType, true), rs.getInt("room2") != 0 ? new Room(rs.getInt("room2"), roomType, true) : null, rs.getInt("room3") != 0 ? new Room(rs.getInt("room3"), roomType, true) : null}, rs.getString("resID"), new Date[]{rs.getDate("Date In"), rs.getDate("Date Out")}, rs.getInt("customerID"), null, rs.getString("status"));
            } else {
                return null;
            }
        } catch (Exception E) {
            System.out.println(E);
        }
        return null;
    }

    public boolean cancelRes(String resID) {
        String query = "UPDATE `reserves` SET `status` = 'Cancelled', `room1` = NULL, `room2` = NULL, `room3` = NULL WHERE `reserves`.`resID` = '" + resID + "'";

        try {
            Statement stmt = con.createStatement();
            if (existsResID(resID)) {
                stmt.executeQuery(query);
                //Refund through 3rd party
                return true;
            } else {
                return false;
            }
        } catch (Exception E) {
            System.out.println(E);
        }
        return false;
    }

    public boolean checkInCustomer(String resID) {
        String query = "UPDATE `reserves` SET `status` = 'Checked In' WHERE `reserves`.`resID` = '" + resID + "'";
        try {
            Statement stmt = con.createStatement();
            stmt.executeQuery(query);
            return true;
        } catch (Exception E) {
            System.out.println(E);
        }
        return false;
    }

    public boolean checkOutCustomer(String resID) {
        String query = "UPDATE `reserves` SET `status` = 'Checked Out' WHERE `reserves`.`resID` = '" + resID + "'";
        try {
            Statement stmt = con.createStatement();
            stmt.executeQuery(query);
            return true;
        } catch (Exception E) {
            System.out.println(E);
        }
        return false;
    }

    public Reservation[] getAllBookings(int customerID) {
        String query = "SELECT * FROM `reserves` WHERE customerID = '" + customerID + "'";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.last();
            Reservation[] reserves = new Reservation[rs.getRow()];
            rs.beforeFirst();
            rs.next();
            String query2 = "SELECT `RoomType` From rooms WHERE RoomNum = '" + rs.getInt("room1") + "'";
            ResultSet rs2 = stmt.executeQuery(query2);
            boolean why = rs2.isBeforeFirst();
            rs2.next();
            rs.beforeFirst();
            String roomType = rs2.getString("RoomType");
            int i = 0;
            while (rs.next()) {
                reserves[i] = new Reservation(new Room[]{new Room(rs.getInt("room1"), roomType, true), rs.getInt("room2") != 0 ? new Room(rs.getInt("room2"), roomType, true) : null, rs.getInt("room3") != 0 ? new Room(rs.getInt("room3"), roomType, true) : null}, rs.getString("resID"), new Date[]{rs.getDate("Date In"), rs.getDate("Date Out")}, rs.getInt("customerID"), null, rs.getString("status"));
                i++;
            }
            return reserves;
        } catch (Exception E) {
            System.out.println(E);
        }
        return null;
    }

    public void close() {
        try {
            con.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void editReservation(boolean dateInChanged, boolean dateOutChanged, boolean numRoomsChanged, Date dateInEdit, Date dateOutEdit, int numRooms, String resID) {
        Reservation toChange = getReservation(resID);
        Statement stmt;
        try {
            stmt = con.createStatement();
            String query3 = "SELECT `RoomType` From rooms WHERE RoomNum = '" + toChange.getRoomsBooked()[0].getRoomNum() + "'";
            ResultSet rs = stmt.executeQuery(query3);
            rs.next();
            String roomType = rs.getString("RoomType");
            if (dateInChanged || dateOutChanged) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String query = "UPDATE `reserves` SET" + (dateInChanged ? "`Date In` = '" + dateFormat.format(dateInEdit) + "'" : "") + (dateInChanged && dateOutChanged ? "," : "") + (dateOutChanged ? "`Date Out` = '" + dateFormat.format(dateOutEdit) + "'" : "") + " WHERE `reserves`.`resID` = '" + resID + "'";
                String update_rooms_query = "UPDATE `reserves` SET `room1` = NULL, `room2` = NULL, `room3` = NULL WHERE `reserves`.`resID` = '" + resID + "'";
                stmt.executeQuery(update_rooms_query);
                Room[] newRooms = getAvailRooms(roomType, numRooms, dateInEdit, dateOutEdit);
                String query2 = "UPDATE `reserves` SET `room1` = '" + newRooms[0].getRoomNum() + "'" + (numRooms >= 2 ? ", `room2` = '" + newRooms[1].getRoomNum() + "'" : "") + (numRooms == 3 ? ", `room3` = '" + newRooms[2].getRoomNum() + "'" : "") + " WHERE `reserves`.`resID` = '" + resID + "'";
                stmt.executeQuery(query2);
                stmt.executeQuery(query);
            }

            if (numRoomsChanged) {
                if (numRooms < toChange.getRoomsBooked().length) {
                    String query2 = "UPDATE `reserves` SET `room3` = NULL" + (numRooms == 1 ? ", `room2` = NULL" : "") + " WHERE `reserves`.`resID` = '" + resID + "'";
                    stmt.executeQuery(query2);
                } else {
                    Room[] newRooms = getAvailRooms(roomType, numRooms - toChange.getRoomsBooked().length, dateInEdit, dateOutEdit);
                    String query2 = "UPDATE `reserves` SET" + (numRooms - toChange.getRoomsBooked().length == 1 ? " `room2` = '" + newRooms[0].getRoomNum() + "'" : "") + (numRooms - toChange.getRoomsBooked().length == 2 ? ", `room3` = '" + newRooms[1].getRoomNum() + "'" : "") + " WHERE `reserves`.`resID` = '" + resID + "'";
                    stmt.executeQuery(query2);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setService(Service newService, String resID) {
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
        String query = "INSERT INTO `services` (`resID`,`type`, `Time`, `Date`)  VALUES ('" + resID + "', '" + newService.getType() + "', '" + dateFormat2.format(newService.getTime()) + "', '" + dateFormat1.format(newService.getDateOfBooking()) + "')";
        try {
            Statement stmt = con.createStatement();
            stmt.executeQuery(query);
        } catch (Exception E) {
            System.out.println(E);
        }
    }


//    public void rooms(int roomNum){
//        String query = "INSERT INTO `rooms` VALUES ('" + roomNum + "', 'Presidential', 1)";
//        try {
//            Statement stmt = con.createStatement();
//            stmt.executeQuery(query);
//        } catch (Exception E) {
//            System.out.println(E);
//        }
//    }
}
