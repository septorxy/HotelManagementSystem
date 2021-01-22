package Storage.Database;

import HotelSystem.BookingsManager.Reservation;
import HotelSystem.BookingsManager.Room;
import PersonBuilder.Customer;
import Resources.UI;

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
        String query = new StringBuilder().append("INSERT INTO `customers` (`customerID`, `name`, `surname`, `login`, `password`, `email`) VALUES(").append("'").append(ID).append("', '").append(name).append("', '").append(surname).append("', '").append(login).append("', '").append(password).append("', '").append(email).append("')").toString();
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
        String query = "SELECT * FROM `reservations` WHERE resID = '" + resID + "'";
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
        String query = "INSERT INTO `reservations` (customerID, resID, `Date In`, `Date Out`, `room1`, `room2`, `room3`) VALUES ('" + res.getResOwnerID() + "', '" + res.getResID() + "', '" + dateFormat.format(dates[0]) + "', '" + dateFormat.format(dates[1]) + "', '" + (rooms[0]!=null?rooms[0].getRoomNum():0) + "', '" + (rooms[1]!=null?rooms[1].getRoomNum():0) + "', '" + (rooms[2]!=null?rooms[2].getRoomNum():0) + "')";

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
        boolean cleared = true;
        String query2;
        int currRoomNum;
        Date dateIn;
        Date dateOut;
        int initialVal, lastRow;

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            do {
                roomsToReturn[0] = null;
                roomsToReturn[1] = null;
                roomsToReturn[2] = null;
                rs.last();
                lastRow = rs.getRow();
                rs.beforeFirst();
                rs.next();
                initialVal = rs.getInt("RoomNum");
                for (int i = initialVal; i < (int) (Math.random() * lastRow - initialVal - 2) + initialVal; i++) {
                    rs.next();
                }
                for (int i = 0; i < numOfRooms; i++) {
                    roomsToReturn[i] = new Room(rs.getInt("RoomNum"), type, rs.getBoolean("cleaned"));
                    currRoomNum = roomsToReturn[i].getRoomNum();
                    query2 = "SELECT * FROM `reservations` WHERE room1 = '" + currRoomNum + "' OR room2 = '" + currRoomNum + "' OR room3 = '" + currRoomNum + "'";
                    ResultSet rs2 = stmt.executeQuery(query2);
                    if (rs2.isBeforeFirst()) {
                        dateIn = rs2.getDate("Date In");
                        dateOut = rs2.getDate("Date Out");
                        cleared = (checkIn.toInstant().isAfter(dateOut.toInstant()) && checkOut.toInstant().isAfter(dateOut.toInstant())) || (checkIn.toInstant().isBefore(dateIn.toInstant()) && checkOut.toInstant().isBefore(dateIn.toInstant()));
                    }
                    rs.next();
                }

            } while (!cleared);
            return roomsToReturn;
        } catch (Exception E) {
            System.out.println(E);
        }
        return null;
    }

    public boolean deleteRes(String resID) {
        String query = "DELETE FROM `reservations` WHERE `resID` = '" + resID + "'";

        try {
            Statement stmt = con.createStatement();
            stmt.executeQuery(query);
            return true;
        } catch (Exception E) {
            System.out.println(E);
        }
        return false;
    }

    public boolean checkInCustomer(String resID) {
        String query = "UPDATE `reservations` SET `checkIn` = '1' WHERE `reservations`.`resID` = '" + resID + "'";
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
        String query = "SELECT * FROM `reservations` WHERE customerID = '" + customerID + "'";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            rs.last();
            Reservation[] reservations = new Reservation[rs.getRow()];
            rs.beforeFirst();
            rs.next();
            String query2 = "SELECT `RoomType` From rooms WHERE RoomNum = '" + rs.getInt("room1") +"'";
            ResultSet rs2 = stmt.executeQuery(query2);
            rs2.next();
            rs.beforeFirst();
            String roomType = rs2.getString("RoomType");
            int i = 0;
            while (rs.next()) {
                reservations[i] = new Reservation(new Room[]{new Room(rs.getInt("room1"), roomType, true), rs.getInt("room2")!=0?new Room(rs.getInt("room2"), roomType, true):null, rs.getInt("room3")!=0?new Room(rs.getInt("room3"), roomType, true):null}, rs.getString("resID"), new Date[]{ rs.getDate("Date In"), rs.getDate("Date Out")}, rs.getInt("customerID"), null);
                i++;
            }
            return reservations;
        } catch (Exception E) {
            System.out.println(E);
        }
        return null;
    }

    public void close(){
        try {
            con.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
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
