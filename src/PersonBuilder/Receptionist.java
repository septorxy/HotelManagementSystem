package PersonBuilder;

import HotelSystem.BookingsManager.Reservation;
import Resources.UI;
import Storage.Database.StorageCustom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Receptionist extends Employee {

    public Receptionist(int empID, String name, String surname, String login, String password, String job, double salary, int managerID, double hoursWorked, String email) {
        super(empID, name, surname, login, password, job, salary, managerID, hoursWorked, email);
    }

    public void editBooking(String resID) {
        StorageCustom dbCustom = new StorageCustom();
        UI ui = new UI();
        Reservation toChange = dbCustom.getReservation(resID);
        if (toChange != null) {
            boolean dateInChanged = false;
            boolean dateOutChanged = false;
            boolean numRoomsChanged = false;
            try {
                String[] changes = ui.showEditOptions(toChange);
                Date dateInEdit = new SimpleDateFormat("yyyy/MM/dd").parse(changes[0]);
                Date dateOutEdit = new SimpleDateFormat("yyyy/MM/dd").parse(changes[1]);

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
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (Exception e) {
                ui.showError("Process Cancelled");
            }
        }
        dbCustom.close();
    }

    public void createJob() {
        //Delegate to 3rd party like Jira
        throw new UnsupportedOperationException();
    }


    public boolean CheckInCustomer(String resID) {
        StorageCustom dbCustom = new StorageCustom();
        if (dbCustom.existsResID(resID)) {
            boolean result = dbCustom.checkInCustomer(resID);
            dbCustom.close();
            return result;
        } else {
            dbCustom.close();
            return false;
        }

    }

    public boolean CheckOutCustomer(String resID){
        StorageCustom dbCustom = new StorageCustom();
        if(dbCustom.existsResID(resID)){
            boolean result = dbCustom.checkOutCustomer(resID);
            dbCustom.close();
            return result;
        }else{
            dbCustom.close();
            return  false;
        }
    }

}