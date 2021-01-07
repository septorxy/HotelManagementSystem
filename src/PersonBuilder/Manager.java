package PersonBuilder;


import Storage.Database.StorageEmp;

import java.util.ArrayList;
import java.util.List;

public class Manager extends Employee {

    List<Integer> empManaged = new ArrayList<Integer>();

    public Manager(int empID, String name, String surname, String login, String password, String job, double salary, int managerID) {
        super(empID, name, surname, login, password, job, salary, managerID);
        StorageEmp con = new StorageEmp();
        this.empManaged = con.findManaged(empID);
    }

    /**
     *
     * @param request
     */
    public boolean approval(String request) {
        // TODO - implement Manager.approval
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param empManaged
     */
    public void inherit(String[] empManaged) {
        // TODO - implement Manager.inherit
        throw new UnsupportedOperationException();
    }

}