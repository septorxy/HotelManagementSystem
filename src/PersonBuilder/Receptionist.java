package PersonBuilder;

import Storage.Database.StorageCustom;

public class Receptionist extends Employee {

    public Receptionist(int empID, String name, String surname, String login, String password, String job, double salary, int managerID, double hoursWorked, String email) {
        super(empID, name, surname, login, password, job, salary, managerID, hoursWorked, email);
    }

    public void createJob(int roomNum) {
        // TODO - implement Receptionist.createJob
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param resID
     */
    public void editBooking(String resID) {
        // TODO - implement Receptionist.editBooking
        throw new UnsupportedOperationException();
    }


    public void assignJob() {
        // TODO - implement Receptionist.assignJob
        throw new UnsupportedOperationException();
    }

    public void CheckInCustomer(String resID){
        StorageCustom dbCustom = new StorageCustom();
        if(dbCustom.findReservation(resID)){
            dbCustom.checkInCustomer(resID);
        }
    }

    public void CheckOutCustomer(String resID){
        StorageCustom dbCustom = new StorageCustom();
        if(dbCustom.findReservation(resID)){
            dbCustom.deleteRes(resID);
        }
    }

}