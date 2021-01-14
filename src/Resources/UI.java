package Resources;

import javax.swing.*;

public class UI {
    public void init() {
//        JFrame Frame;
//        JButton newCustomer;
//        JButton LoginCustomer;
//        JButton LoginEmp;
        String choiceS = JOptionPane.showInputDialog("1. For new customer\n2. For Login Customer\n3. For Login Emp");
        int choice = Integer.parseInt(choiceS);

        switch (choice){
            case 1:
                newCustomerUI();
                break;
            case 2:
                loginUI("Customer");
                break;
            case 3:
                loginUI("Employee");
                break;
        }
    }


    public String[] newEmpUI(){
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
        JOptionPane.showMessageDialog(null, fields, "Employee Input",  JOptionPane.INFORMATION_MESSAGE);
        return new String[]{
                name.getText(), surname.getText(), salary.getText(), job.getText(), managerID.getText(), email.getText()
        };
    }

    public void newCustomerUI(){
        JTextField name = new JTextField();
        JTextField surname = new JTextField();
        JTextField ID = new JTextField();
        JTextField email = new JTextField();
        Object[] fields = {
                "Name", name,
                "Surname", surname,
                "ID (without letters)", ID,
                "E-mail", email
        };
        JOptionPane.showMessageDialog(null, fields, "New Customer Input",  JOptionPane.INFORMATION_MESSAGE);

    }

    public boolean loginUI(String type){
        return false;
    }

}
