package PersonBuilder;

import java.util.Date;

public class Employee extends Person{
    private String job;
    private Double salary;
    private Double hoursWorked;
    private int managerID;
    private String currentJob;
    private Date myCal; //To review

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

    public void setHoursWorked(Double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getJob() {
        return job;
    }


    public int getManager() {
        return managerID;
    }

    public void setManager(int managerID) {
        this.managerID = managerID;
    }

    /**
     * @param time
     */
    public void ClockIn(Date time) {
        // TODO - implement HumanResources.ClockIn
        throw new UnsupportedOperationException();
    }

    /**
     * @param time
     */
    public void ClockOut(Date time) {
        // TODO - implement HumanResources.ClockOut
        throw new UnsupportedOperationException();
    }

    public String requestLeave() {
        // TODO - implement HumanResources.requestLeave
        throw new UnsupportedOperationException();
    }

    public boolean cancelLeave() {
        // TODO - implement HumanResources.cancelLeave
        throw new UnsupportedOperationException();
    }

    public void showLeave() {
        throw new UnsupportedOperationException();
    }
}
