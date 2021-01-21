package Storage.Database;

import HotelSystem.BookingsManager.Reservation;
import HotelSystem.BookingsManager.Room;
import PersonBuilder.Customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class StorageCustom {
    private Connection con = null;

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
        Room[] rooms = res.getRoomsBooked();
        String query = "INSERT INTO `reservations` (customerID, resID, `Date In`, `Date Out`, 'room1', `room2`, `room3`) VALUES ('" + res.getResOwnerID() + "', '" + res.getResID() + "', '" + dates[0] + "', '" + dates[1] + "', '"+rooms[0]+"', '"+ rooms[1] +"', '"+ rooms[2] +"')";

        try {
            Statement stmt = con.createStatement();
            stmt.executeQuery(query);
        } catch (Exception E) {
            System.out.println(E);
        }
    }


    //TODO: FIX AVAIL ROOMS - NEED TO CHECK IF ROOMS AVAILABLE BETWEEN CERTAIN DATES
    public Room[] getAvailRooms(String type, int numOfRooms) {
        Room[] roomsToReturn = new Room[3];
        String query = "SELECT * FROM `rooms` WHERE RoomType = '" + type + "'";
        int i = 0;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
//            while (rs.next() && i < numOfRooms + 1) {
//                roomsToReturn[i] = new Room(rs.getInt("RoomNum"), type, rs.getBoolean("cleaned"));
//                i++;
//            }

                return roomsToReturn;
            } catch(Exception E){
                System.out.println(E);
            }
            return null;
        }

    public void deleteRes(String resID) {
        throw new UnsupportedOperationException();
    }

    public boolean findReservation(String resID) {
        throw new UnsupportedOperationException();
    }

    public void checkInCustomer(String resID) {
        throw new UnsupportedOperationException();
    }
}
