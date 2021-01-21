package Resources;

import javax.swing.*;

public class UI {
    public int init() {
//        JFrame Frame;
//        JButton newCustomer;
//        JButton LoginCustomer;
//        JButton LoginEmp;
        String choiceS = JOptionPane.showInputDialog("1. For new customer\n2. For Login Customer\n3. For Login Emp");
        return Integer.parseInt(choiceS);
    }


    public String[] showNewEmpUI(){
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
        JOptionPane.showConfirmDialog(null, fields, "Employee Input", JOptionPane.YES_NO_CANCEL_OPTION);
        return new String[]{
                name.getText(), surname.getText(), salary.getText(), job.getText(), managerID.getText(), email.getText()
        };
    }

    public String[] showNewCustomerUI(){
        JTextField name = new JTextField();
        JTextField surname = new JTextField();
        JTextField ID = new JTextField();
        JTextField password = new JTextField();
        JTextField email = new JTextField();
        Object[] fields = {
                "Name", name,
                "Surname", surname,
                "ID (without letters)", ID,
                "Password", password,
                "E-mail", email
        };
        JOptionPane.showConfirmDialog(null, fields, "New Customer Input", JOptionPane.YES_NO_CANCEL_OPTION);
        return new String[]{
                name.getText(), surname.getText(),  password.getText(), ID.getText(), email.getText()
        };
    }

    public void showError(String message){
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }


    //The methods below should be within the web part of the project but since we are not doing that part for the prototype the methods are implemented here
    public String[] showLoginUI(String type){
        JTextField login = new JTextField();
        JTextField password = new JTextField();
        Object[] fields = {
                "Login", login,
                "Password", password,
        };
        if(type.equals("Customer")){
            JOptionPane.showConfirmDialog(null, fields, "Customer Login", JOptionPane.YES_NO_CANCEL_OPTION);
        }else{
            JOptionPane.showConfirmDialog(null, fields, "Employee Login", JOptionPane.YES_NO_CANCEL_OPTION);
        }
        return new String[]{
                login.getText(),  password.getText()
        };
    }




    public int showUserOptions(){
//        JFrame Frame;
//        JButton NewRes;
//        JButton showBooked;
//        JButton settings;
        String choiceS = JOptionPane.showInputDialog("" +
                "1. New Reservation\n" +
                "2. Show current bookings\n" +
                "3. Settings\n" +
                "4. Log Out");
        return Integer.parseInt(choiceS);
    }

    public String[] showNewBookingUI(){
        JTextField dateIn = new JTextField();
        JTextField dateOut = new JTextField();
        JTextField roomType = new JTextField();// Would be a combo box in final product
        JTextField numberOfRooms = new JTextField();
        Object[] fields = {
                "Date-In (dd-mm-yyyy)", dateIn,
                "Date-Out (dd-mm-yyyy)", dateOut,
                "Number of Rooms (Max 3)", numberOfRooms,
                "Room Types (Standard, Suite, Residential)", roomType // In final product different types of standard/suites/presidential exists based on num of ppl in room
        };
        do {
            JOptionPane.showConfirmDialog(null, fields, "New Reservation", JOptionPane.YES_NO_CANCEL_OPTION);
        }while(Integer.parseInt(numberOfRooms.getText())>3);
        return new String[]{
                dateIn.getText(), dateOut.getText(), numberOfRooms.getText(), roomType.getText()
        };
    }

    public String getInput(String message) {
        return JOptionPane.showInputDialog("");
    }

    public int showEmpOptions() {
        String choiceS = JOptionPane.showInputDialog("" +
                "1. Clock-In\n" +
                "2. Clock-Out\n" +
                "3. Leave\n" +
                "4. Check-In Customer (Receptionist)\n" +
                "5. Check-Out Customer (Receptionist)\n" +
                "6. Edit Booking (Receptionist)\n" +
                "7. Create Job (Manager/Receptionist)\n" +
                "8. Approve Leave (Manager)\n" +
                "9. Add Employee (HR)\n" +
                "10. Log Out");
        return Integer.parseInt(choiceS);
    }

    public int showLeaveOptionMenu(){
        String choiceS = JOptionPane.showInputDialog("" +
                "1. Create new Leave\n" +
                "2. View Booked Leave\n" +
                "3. Cancel Leave\n" +
                "4. Back");
        return Integer.parseInt(choiceS);
    }
}
