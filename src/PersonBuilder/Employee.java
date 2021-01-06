package PersonBuilder;
import java.util.*;

public class Employee extends Person{
    private String job;
    private Double salary;
    private Double hoursWorked;
    private int managerID;
    private String currentJob;
    private Date myCal; //To review
    private int empID;

    public Employee(int empID, String name, String surname, String login, String password, String job, double salary, int managerID) {
        super(name, surname, login, password);
        this.empID = empID;
        this.job = job;
        this.salary = salary;
        this.managerID = managerID;
    }

    public Double getHoursWorked() {
        return this.hoursWorked;
    }

    public void setHoursWorked(Double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public int getEmpID() {
        return this.empID;
    }

    @Override
    public void login(String login, String password) {
        //Get Employee Login Screen
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
}
