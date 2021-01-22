package PersonBuilder;


import Storage.Database.StorageEmp;

import java.util.List;

public class Manager extends Employee {

    private List<Integer> empManaged;
    private List<String> perms;

    public Manager(int empID, String name, String surname, String login, String password, String job, double salary, int managerID, double hoursWorked, String email) {
        super(empID, name, surname, login, password, job, salary, managerID, hoursWorked, email);
        StorageEmp con = new StorageEmp();
        this.empManaged = con.findManaged(empID);
        con.close();
    }


    public boolean approval() {
        // TODO - implement Manager.approval
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param empManaged
     */
    public void inherit(int[] empManaged) {
        // TODO - implement Manager.inherit
        throw new UnsupportedOperationException();
    }

    public List<Integer> getEmpManaged() {
        return empManaged;
    }

    public void setEmpManaged(List<Integer> empManaged) {
        this.empManaged = empManaged;
    }
}