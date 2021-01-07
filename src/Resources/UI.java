package Resources;
import javax.swing.*;

public class UI {
    public UI(){
        //Would be welcome UI
        //JOptionPane.showMessageDialog(null,"Welcome! Please choose what to do");
    }

    public UI(Object[] fields, String message){
        JOptionPane.showMessageDialog(null, fields, message,  JOptionPane.INFORMATION_MESSAGE);
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
}
