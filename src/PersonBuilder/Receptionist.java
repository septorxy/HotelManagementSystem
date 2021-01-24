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

    public boolean CheckInCustomer(String resID){
        StorageCustom dbCustom = new StorageCustom();
        if(dbCustom.existsResID(resID)){
            dbCustom.checkInCustomer(resID);
            dbCustom.close();
            return true;
        }else{
            dbCustom.close();
            return  false;
        }

    }

    public boolean CheckOutCustomer(String resID){
        StorageCustom dbCustom = new StorageCustom();
        if(dbCustom.existsResID(resID)){
            dbCustom.checkOutCustomer(resID);
            dbCustom.close();
            return true;
        }else{
            dbCustom.close();
            return  false;
        }
    }

}