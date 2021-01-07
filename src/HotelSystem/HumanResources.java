package HotelSystem;

import Resources.PasswordGenerator;
import Resources.SendEmail;
import Resources.UI;
import Storage.Database.StorageEmp;

import javax.mail.MessagingException;
import javax.swing.*;
import java.util.Date;

public class HumanResources {

    private Date calendar;
    private Date date;
    private Date time;
    UI ui = new UI();

    public Date getTime() {
        return this.time;
    }

    public Date getDate() {
        return this.date;
    }

    public void resetHoursWorked() {
        // TODO - implement HumanResources.resetHoursWorked
        throw new UnsupportedOperationException();
    }

    public void sendRequest() {
        // TODO - implement HumanResources.sendRequest
        throw new UnsupportedOperationException();
    }

    public void addEmployee() {
        StorageEmp con = new StorageEmp();
        boolean isAvail = false;
        double hoursWorked = 0;
        int empID;
        do {
            empID = (int) (Math.random() * (99999 - 10000) + 10000);
            if (!con.searchEmpID(empID)) {
                isAvail = true;
            }
        } while (!isAvail);

        String[] details = ui.newEmpUI();

        String name = details[0];
        String surname = details[1];
        double salary = Double.parseDouble(details[2]);
        String job = details[3];
        int managerID = Integer.parseInt(details[4]);
        String email = details[5];
        // ask for data required then display -> Pass Params for data needed

        String login = name.substring(0, 1) + surname + empID;
        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .build();
        String password = passwordGenerator.generate(8);

        JOptionPane.showMessageDialog(null, empID + "\n " + name + "\n " + surname + "\n " + login + "\n " + password + "\n " + salary + "\n " + String.valueOf(hoursWorked) + "\n " + job + "\n " + managerID);

        //con.addNewEmp(empID, name, surname, login, password, salary, hoursWorked, job, managerID, email);

        try {
            SendEmail.sendMail(email, login, password);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
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

}