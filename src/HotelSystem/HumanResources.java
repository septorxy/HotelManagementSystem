package HotelSystem;

import Resources.PasswordGenerator;
import Resources.SendEmail;
import Storage.StorageEmp;

import javax.mail.MessagingException;
import javax.swing.*;
import java.util.Date;

public class HumanResources {

    private Date calendar;
    private Date date;
    private Date time;

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
        do{
            empID = (int) (Math.random() * (99999 - 10000) + 10000);
            if(!con.searchEmpID(empID)){
                isAvail = true;
            }
        }while(!isAvail);
        JTextField name = new JTextField();
        JTextField surname = new JTextField();
        JTextField salary = new JTextField();
        JTextField job = new JTextField();
        JTextField managerID = new JTextField();
        JTextField email = new JTextField();
        Object[] fields = {
                "Name", name,
                "Surname", surname,
                "Yearly Salary", salary,
                "Job", job,
                "Manager / Supervisor", managerID,
                "E-mail", email
        };
        JOptionPane.showConfirmDialog(null, fields, "Employee Input", JOptionPane.OK_CANCEL_OPTION);

        String login = name.getText().substring(0,1) + surname.getText() + empID;
        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .build();
        String password = passwordGenerator.generate(8);

        JOptionPane.showMessageDialog(null, empID + "\n " + name.getText() +"\n "+ surname.getText() +"\n "+ login +"\n "+  password +"\n "+ salary.getText()+ "\n "+ String.valueOf(hoursWorked) +"\n "+ job.getText() +"\n "+ managerID.getText());

        con.addNewEmp(empID,name.getText(),surname.getText(),login, password, Double.parseDouble(salary.getText()), hoursWorked, job.getText(), Integer.parseInt(managerID.getText()), email.getText());

        try {
            SendEmail.sendMail(email.getText(), login, password);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param time
     */
    public void ClockIn(Date time) {
        // TODO - implement HumanResources.ClockIn
        throw new UnsupportedOperationException();
    }

    /**
     *
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