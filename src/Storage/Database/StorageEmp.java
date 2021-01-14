package Storage.Database;

import PersonBuilder.Employee;

import java.sql.*;
import java.util.ArrayList;

public class StorageEmp {
    private Connection con = null;

    public StorageEmp(){
        try{
            Class.forName("org.mariadb.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mariadb://localhost:3306/staffdb?useSSL=false","test","test");
        }catch(Exception e){ System.out.println(e);}
    }

    public void addNewEmp(Employee newEmp){
        String query = new StringBuilder().append("INSERT INTO `staff` (`empID`, `name`, `surname`, `login`, `password`, `salary`, `hoursWorked`, `job`, `managerID`, `email`) VALUES (").append(newEmp.getID()).append(", \"").append(newEmp.getName()).append("\", \"").append(newEmp.getSurname()).append("\", \"").append(newEmp.getLogin()).append("\", \"").append(newEmp.getPassword()).append("\", ").append(newEmp.getSalary()).append(", ").append(newEmp.getHoursWorked()).append(", \"").append(newEmp.getJob()).append("\", ").append(newEmp.getManager()).append(", \"").append(newEmp.getEmail()).append("\")").toString();
        try {
            Statement stmt = con.createStatement();
            stmt.executeQuery(query);
        }catch (Exception E){ System.out.println(E); }
    }

    public boolean existsEmpID(int empID){
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

    public void close(){
        try {
            con.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public boolean Backup(){

        return false;
    }

}