package PersonBuilder;

import HotelSystem.BookingsManager.Reservation;
import HotelSystem.BookingsManager.Room;
import Resources.PasswordGenerator;
import Resources.UI;
import Storage.Database.StorageCustom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Customer extends Person {

    public Customer(String name, String surname, String login, String password, int ID, String email) {
        super(name, surname, login, password, ID, email);
    }


    public void makeBooking() {
        UI ui = new UI();
        StorageCustom dbCustom = new StorageCustom();
        String[] details = ui.showNewBookingUI();
        Room[] bookRooms;
        try {
            bookRooms = dbCustom.getAvailRooms(details[3], Integer.parseInt(details[2]),new SimpleDateFormat("yyyy/MM/dd").parse(details[0]), new SimpleDateFormat("yyyy/MM/dd").parse(details[1]));
            if(bookRooms == null){
                throw new Exception();
            }
            PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                    .useDigits(false)
                    .useLower(false)
                    .useUpper(true)
                    .build();
            String resID = passwordGenerator.generate(6);
            while(dbCustom.existsResID(resID)){
                resID = passwordGenerator.generate(6);
            }
            try {
                Date[] datesOfStay = { new SimpleDateFormat("yyyy/MM/dd").parse(details[0]), new SimpleDateFormat("yyyy/MM/dd").parse(details[1])};
                Reservation newRes = new Reservation(bookRooms, resID, datesOfStay , getID(), null);
                if(makePayment()) {
                    dbCustom.newReservation(newRes);
                    //Send Email Here with confirmation
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dbCustom.close();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception ignored) {
        }

    }

    public void editBooking(String resID) {
        // TODO - implement Customer.editBooking
        throw new UnsupportedOperationException();
    }

    public boolean cancelBooking(String resID) {
        StorageCustom dbCustom = new StorageCustom();
        boolean result = dbCustom.deleteRes(resID);
        dbCustom.close();
        return result;
    }

    public boolean makePayment() {
        System.out.println("Payment Portal...\nPayment is assumed successful");
        return true;
    }

    public void makeComplaint() {
        // TODO - implement Customer.makeComplaint
        //Send Email with message written by user.
        throw new UnsupportedOperationException();
    }

    public void showAllBookings(){
        UI ui = new UI();
        StorageCustom dbCustom = new StorageCustom();
        Reservation[] res = dbCustom.getAllBookings(getID());
        ui.formatBookings(res);
        dbCustom.close();
    }


}