package PersonBuilder;

import HotelSystem.BookingsManager.Reservation;
import HotelSystem.BookingsManager.Room;
import HotelSystem.BookingsManager.Service;
import Resources.PasswordGenerator;
import Resources.SendEmail;
import Resources.UI;
import Storage.Database.StorageCustom;

import javax.mail.MessagingException;
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
        try {
            String[] details = ui.showNewBookingUI();
            Room[] bookRooms;
            try {
                bookRooms = dbCustom.getAvailRooms(details[3], Integer.parseInt(details[2]), new SimpleDateFormat("yyyy/MM/dd").parse(details[0]), new SimpleDateFormat("yyyy/MM/dd").parse(details[1]));
                if (bookRooms == null) {
                    throw new Exception();
                }
                PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                        .useDigits(false)
                        .useLower(false)
                        .useUpper(true)
                        .build();
                String resID = passwordGenerator.generate(6);
                while (dbCustom.existsResID(resID)) {
                    resID = passwordGenerator.generate(6);
                }
                try {
                    Date[] datesOfStay = {new SimpleDateFormat("yyyy/MM/dd").parse(details[0]), new SimpleDateFormat("yyyy/MM/dd").parse(details[1])};
                    Reservation newRes;
                    if (makePayment(true)) {
                        newRes = new Reservation(bookRooms, resID, datesOfStay, getID(), null, "Booking Confirmed");
                    } else {
                        newRes = new Reservation(bookRooms, resID, datesOfStay, getID(), null, "Booking Unconfirmed");
                    }
                    dbCustom.newReservation(newRes);
                    String email = "Dear " + getName() + ",\nPlease find below your Booking:\n" + ui.buildRes(newRes).toString() + "I hope you enjoy your stay with us!\nSincerely,\nThe management";
                    SendEmail.sendMailCustom(getEmail(), email);
                    ui.showSuccessMessage();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (Exception ignored) {
            }
        } catch (Exception E) {
            ui.showError("Process Cancelled");
        }
        dbCustom.close();
    }

    public void editBooking(String resID) {
        StorageCustom dbCustom = new StorageCustom();
        UI ui = new UI();
        Reservation toChange = dbCustom.getReservation(resID);
        if (toChange != null) {
            boolean dateInChanged = false;
            boolean dateOutChanged = false;
            boolean numRoomsChanged= false;
            try {
                String[] changes = ui.showEditOptions(toChange);
                Date dateInEdit = new SimpleDateFormat("yyyy/MM/dd").parse(changes[0]);
                Date dateOutEdit = new  SimpleDateFormat("yyyy/MM/dd").parse(changes[1]);

                if (!dateInEdit.equals(toChange.getDatesOfStay()[0])) {
                    dateInChanged = true;
                }
                if (!dateOutEdit.equals(toChange.getDatesOfStay()[0])) {
                    dateOutChanged = true;
                }
                if (Integer.parseInt(changes[2]) != toChange.getRoomsBooked().length) {
                    numRoomsChanged = true;
                }
                dbCustom.editReservation(dateInChanged, dateOutChanged, numRoomsChanged, dateInEdit, dateOutEdit, Integer.parseInt(changes[2]), resID);
                String email = "Dear " + getName() + ",\nPlease find below your Booking:\n" + ui.buildRes(dbCustom.getReservation(resID)).toString() + "I hope you enjoy your stay with us!\nSincerely,\nThe management";
                SendEmail.sendMailCustom(getEmail(), email);
                ui.showSuccessMessage();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (Exception e) {
                ui.showError("Process Cancelled");
            }
        }
        dbCustom.close();
    }

    public boolean cancelBooking(String resID) {
        StorageCustom dbCustom = new StorageCustom();
        boolean result = dbCustom.cancelRes(resID);
        String email = "Dear " + getName() + ",\nYour booking " + resID + " has been cancelled.\nSincerely,\nThe management";
        try {
            SendEmail.sendMailCustom(getEmail(), email);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        dbCustom.close();
        return result;
    }

    public boolean makePayment(boolean paid) {
        System.out.println("Payment Portal...");
        if (paid) {
            System.out.println("Payment is assumed successful");
        } else {
            System.out.println("Payment is assumed to be delayed to a later date");
        }
        return paid;
    }

    public void showAllBookings() {
        UI ui = new UI();
        StorageCustom dbCustom = new StorageCustom();
        Reservation[] res = dbCustom.getAllBookings(getID());
        ui.formatBookings(res);
        dbCustom.close();
    }


    public void serviceBooking() {
        UI ui = new UI();
        StorageCustom dbCustom = new StorageCustom();
        try {
            String resID = ui.getSingleInput("What is your Reservation ID?");
            Service newService = ui.showServiceWindow(dbCustom.getReservation(resID));
            dbCustom.setService(newService, resID);
            ui.showSuccessMessage();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            ui.showError("Process Cancelled");
        }
        dbCustom.close();
    }
}