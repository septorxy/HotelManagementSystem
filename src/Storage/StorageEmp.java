package Storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class StorageEmp {
    private Connection con = null;

    public StorageEmp(){
        try{
            Class.forName("org.mariadb.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mariadb://localhost:3306/staffdb?useSSL=false","test","test");
        }catch(Exception e){ System.out.println(e);}
    }

    public boolean addNewEmp(int empID, String name, String surname, String login, String password, double salary, double hoursWorked, String job, int managerID, String email){
        String query = new StringBuilder().append("INSERT INTO `staff` (`empID`, `name`, `surname`, `login`, `password`, `salary`, `hoursWorked`, `job`, `managerID`, `email`) VALUES (").append(empID).append(", \"").append(name).append("\", \"").append(surname).append("\", \"").append(login).append("\", \"").append(password).append("\", ").append(salary).append(", ").append(hoursWorked).append(", \"").append(job).append("\", ").append(managerID).append(", \"").append(email).append("\")").toString();
        try {
            Statement stmt = con.createStatement();
            stmt.executeQuery(query);
            return true;
        }catch (Exception E){ System.out.println(E); return false; }
    }

    public boolean searchEmpID(int empID){
        String query = "SELECT * FROM `staff` WHERE empID = " + empID;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return rs.isBeforeFirst(); //Returns true if found and false if not found
        }catch (Exception E){ System.out.println(E);}
        return false;
    }

    public ArrayList<Integer> findManaged(int managerID){
        String query = "SELECT empID FROM `staff` WHERE managerID = " + managerID;
        ArrayList<Integer> empManaged = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.isBeforeFirst()){
                while(rs.next()){
                    empManaged.add(rs.getInt(1));
                }
                return empManaged;
            }else{
                return null;
            }

        }catch (Exception E){ System.out.println(E);}
        return null;
    }

}