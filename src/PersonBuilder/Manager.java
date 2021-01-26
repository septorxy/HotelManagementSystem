package PersonBuilder;


import Storage.Database.StorageEmp;

import java.text.ParseException;
import java.util.List;

public class Manager extends Employee {

    private List<Integer> empManaged;


    public Manager(int empID, String name, String surname, String login, String password, String job, double salary, int managerID, double hoursWorked, String email) {
        super(empID, name, surname, login, password, job, salary, managerID, hoursWorked, email);
        StorageEmp con = new StorageEmp();
        this.empManaged = con.findManaged(empID);
        con.close();
    }


    public void approval() {
        StorageEmp dbEmp = new StorageEmp();
        dbEmp.toApprove(getID());
        dbEmp.close();
    }

    public String[] inherit() {
        StorageEmp dbEmp = new StorageEmp();
        String[] jobs = dbEmp.findManagedJobs(empManaged);
        dbEmp.close();
        return jobs;
    }

    public void setEmpManaged(List<Integer> empManaged) {
        this.empManaged = empManaged;
    }

    public String generateReport(int month, int year) {
        StorageEmp dbEmp = new StorageEmp();
        String toRet = "";
        try {
            toRet = dbEmp.generateHoursWorkedReport(empManaged, month, year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dbEmp.close();
        return toRet;
    }
}