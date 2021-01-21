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
        Room[] bookRooms = dbCustom.getAvailRooms(details[2], Integer.parseInt(details[3]));
        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(false)
                .useLower(false)
                .useUpper(true)
                .build();
        String resID = passwordGenerator.generate(6);
        try {
            Date[] datesOfStay = { new SimpleDateFormat("yyyy/MM/dd").parse(details[0]), new SimpleDateFormat("yyyy/MM/dd").parse(details[1])};
            Reservation newRes = new Reservation(bookRooms, resID, datesOfStay , getID(), null);
            dbCustom.newReservation(newRes);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param resID
     */
    public void editBooking(String resID) {
        // TODO - implement Customer.editBooking
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param resID
     */
    public boolean cancelBooking(String resID) {
        // TODO - implement Customer.cancelBooking
        throw new UnsupportedOperationException();
    }

    public void makePayment() {
        System.out.println("Payment Portal...\nPayment is assumed successful");
    }

    public void makeComplaint() {
        // TODO - implement Customer.makeComplaint
        throw new UnsupportedOperationException();
    }

    public void showAllBookings(){
        throw new UnsupportedOperationException();
    }


}