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
        String query = "INSERT INTO `staff` (`empID`, `name`, `surname`, `login`, `password`, `salary`, `hoursWorked`, `job`, `managerID`, `email`) VALUES (" + newEmp.getID() + ", '" + newEmp.getName() + "', '" + newEmp.getSurname() + "', '" + newEmp.getLogin() + "', '" + newEmp.getPassword() + "', " + newEmp.getSalary() + ", " + newEmp.getHoursWorked() + ", '" + newEmp.getJob() + "', " + newEmp.getManager() + ", '" + newEmp.getEmail() + "')";
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

    public Employee getEmployee(String login, String password) {
        String query = "SELECT * FROM `staff` WHERE login = '" + login + "' AND password = '" + password + "'";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.isBeforeFirst()) {
                rs.next();
                return new Employee(rs.getInt("empID"), rs.getString("name"), rs.getString("surname"), rs.getString("login"), rs.getString("password"), rs.getString("job"),rs.getDouble("salary"), rs.getInt("managerID"), rs.getDouble("hoursWorked"), rs.getString("email"));
            } else {
                return null;
            }
        } catch (Exception E) {
            System.out.println(E);
        }
        return null;
    }

    public void close(){
        try {
            con.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

}