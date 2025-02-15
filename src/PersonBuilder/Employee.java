package PersonBuilder;

import Resources.UI;
import Storage.Database.StorageEmp;

import java.text.ParseException;
import java.util.Date;

public class Employee extends Person {
    private final String job;
    private final Double salary;
    private final Double hoursWorked;
    private final int managerID;

    public Employee(int empID, String name, String surname, String login, String password, String job, double salary, int managerID, double hoursWorked, String email) {
        super(name, surname, login, password, empID, email);
        this.job = job;
        this.salary = salary;
        this.managerID = managerID;
        this.hoursWorked = hoursWorked;
    }

    public Double getHoursWorked() {
        return this.hoursWorked;
    }

    public Double getSalary() {
        return salary;
    }

    public String getJob() {
        return job;
    }


    public int getManager() {
        return managerID;
    }


    public void ClockIn() {
        UI ui = new UI();
        StorageEmp dbEmp = new StorageEmp();
        dbEmp.clockIn(getID());
        ui.showSuccessMessage();
        dbEmp.close();
    }

    public void ClockOut() {
        StorageEmp dbEmp = new StorageEmp();
        UI ui = new UI();
        dbEmp.clockOut(getID());
        ui.showSuccessMessage();
        dbEmp.close();
    }

    public void requestLeave() {
        UI ui = new UI();
        StorageEmp dbEmp = new StorageEmp();
        Date[] details;
        try {
            details = ui.showLeaveWindow();
            dbEmp.reqLeave(details[0], details[1], getID(), getManager());
            ui.showSuccessMessage();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            ui.showError("Process Cancelled");
        }
        dbEmp.close();
    }

    public boolean cancelLeave() {
        UI ui = new UI();
        StorageEmp dbEmp = new StorageEmp();
        try {
            int toDel = ui.showLeaveCancellation(dbEmp.getAllBookings(getID()));
            dbEmp.cancelLeave(toDel);
            ui.showSuccessMessage();
            dbEmp.close();
            return true;
        } catch (Exception E) {
            ui.showError("Invalid Entry");
            dbEmp.close();
            return false;
        }
    }

    public void showLeave() {
        UI ui = new UI();
        StorageEmp dbEmp = new StorageEmp();
        ui.showAllLeave(dbEmp.getAllBookings(getID()));
        dbEmp.close();
    }
}
