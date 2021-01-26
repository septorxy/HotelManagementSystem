package Storage.Database;

import PersonBuilder.Employee;
import Resources.UI;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StorageEmp {
    private Connection con = null;

    public StorageEmp() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/staffdb?useSSL=false", "test", "test");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addNewEmp(Employee newEmp) {
        String query = "INSERT INTO `staff` (`empID`, `name`, `surname`, `login`, `password`, `salary`, `job`, `managerID`, `email`) VALUES (" + newEmp.getID() + ", '" + newEmp.getName() + "', '" + newEmp.getSurname() + "', '" + newEmp.getLogin() + "', '" + newEmp.getPassword() + "', " + newEmp.getSalary() + ", '" + newEmp.getJob() + "', " + newEmp.getManager() + ", '" + newEmp.getEmail() + "')";
        try {
            Statement stmt = con.createStatement();
            stmt.executeQuery(query);
        } catch (Exception E) {
            System.out.println(E);
        }
    }

    public boolean existsEmpID(int empID) {
        String query = "SELECT * FROM `staff` WHERE empID = " + empID;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return rs.isBeforeFirst(); //Returns true if found and false if not found
        } catch (Exception E) {
            System.out.println(E);
        }
        return false;
    }

    public ArrayList<Integer> findManaged(int managerID) {
        String query = "SELECT empID FROM `staff` WHERE managerID = " + managerID;
        ArrayList<Integer> empManaged = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    empManaged.add(rs.getInt(1));
                }
                return empManaged;
            } else {
                return null;
            }

        } catch (Exception E) {
            System.out.println(E);
        }
        return null;
    }

    public String[] findManagedJobs(List<Integer> IDs) {
        try {
            String[] jobs = new String[IDs.size()];
            Statement stmt = con.createStatement();
            for (int i = 0; i < IDs.size(); i++) {
                String query = "SELECT job FROM `staff` WHERE empID = " + IDs.get(i);
                ResultSet rs = stmt.executeQuery(query);
                rs.next();
                jobs[i] = rs.getString("job");
            }
            return jobs;
        } catch (Exception E) {
            System.out.println(E);
        }
        return null;
    }

    public Employee getEmployee(String login, String password) {
        String query = "SELECT * FROM `staff` WHERE login = '" + login + "' AND password = '" + password + "'";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.isBeforeFirst()) {
                rs.next();
                return new Employee(rs.getInt("empID"), rs.getString("name"), rs.getString("surname"), rs.getString("login"), rs.getString("password"), rs.getString("job"), rs.getDouble("salary"), rs.getInt("managerID"), 0, rs.getString("email"));
            } else {
                return null;
            }
        } catch (Exception E) {
            System.out.println(E);
        }
        return null;
    }

    public void close() {
        try {
            con.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public boolean clockIn(int empID) {
        try {
            UI ui = new UI();
            if (checkClockedStatus(empID)) {
                String query = "INSERT INTO `timestamp` (`empID`, `ClockIn`, `ClockOut`, `hoursWorked`) VALUES ('" + empID + "', current_timestamp(), NULL, NULL)";
                Statement stmt = con.createStatement();
                stmt.executeQuery(query);
                return true;
            } else {
                ui.showError("Clock-Out pending. Please Clock-Out first");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean clockOut(int empID) {
        try {
            UI ui = new UI();
            if (!checkClockedStatus(empID)) {
                Statement stmt = con.createStatement();
                double hoursWorked;
                String query1 = "SELECT * FROM `timestamp` WHERE empID = '" + empID + "' ORDER BY `ClockIn` DESC";
                ResultSet rs = stmt.executeQuery(query1);
                rs.next();
                Timestamp ts1 = rs.getTimestamp("ClockIn");
                java.util.Date date = new Date();
                long time = date.getTime();
                Timestamp ts2 = new Timestamp(time);
                long milliseconds = ts2.getTime() - ts1.getTime();
                hoursWorked = milliseconds / 3600000.0;
                String query2 = "UPDATE `timestamp` SET `ClockOut` = current_timestamp(), `hoursWorked` = " + hoursWorked + " WHERE empID = '" + empID + "' AND ClockOut IS NULL";
                stmt.executeQuery(query2);
                return true;
            } else {
                ui.showError("Clock-In pending. Please Clock-In first");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkClockedStatus(int empID) throws Exception {
        //true = can check in / cant check out
        //false = can check out / cant check in
        String query = "SELECT * FROM `timestamp` WHERE empID = '" + empID + "' ORDER BY `ClockIn` DESC";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.isBeforeFirst()) {
                rs.next();
                return rs.getTimestamp("ClockOut") != null;
            } else {
                return true;
            }
        } catch (Exception E) {
            System.out.println(E);
        }
        throw new Exception();
    }

    public boolean reqLeave(Date dateIn, Date dateOut, int empID, int managerID) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String query = "INSERT INTO `leave` (`empID`, `Start`, `End`, `managerID`, `approved`) VALUES ('" + empID + "', '" + dateFormat.format(dateIn) + "', '" + dateFormat.format(dateOut) + "', '" + managerID + "', '" + (managerID == empID ? "Approved" : "Pending") + "')";
        try {
            Statement stmt = con.createStatement();
            stmt.executeQuery(query);
            return true;
        } catch (Exception E) {
            System.out.println(E);
        }
        return false;
    }

    public void toApprove(int empID) {
        String query = "SELECT * FROM `leave` WHERE managerID = '" + empID + "' AND approved = 'Pending'";
        String query2;
        String query3;
        ResultSet rs2;
        UI ui = new UI();
        boolean approved;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                try {
                    query2 = "SELECT `name`, `surname` FROM `staff` WHERE `empID` = '" + rs.getInt("empID") + "'";
                    rs2 = stmt.executeQuery(query2);
                    rs2.next();
                    approved = ui.showLeaveApproval(rs.getInt("empID"), rs2.getString("name"), rs2.getString("surname"), rs.getDate("Start"), rs.getDate("End"));
                } catch (Exception E) {
                    break;
                }
                query3 = "UPDATE `leave` SET `approved` = '" + (approved ? "Approved" : "Unapproved") + "' WHERE leaveNum = '" + rs.getInt("leaveNum") + "'";
                stmt.executeQuery(query3);
            }
        } catch (Exception E) {
            System.out.println(E);
        }
    }

    public String getAllBookings(int empID) {
        Date currDate = new Date();
        StringBuilder result = new StringBuilder();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String query = "SELECT * FROM `leave` WHERE `empID` = '" + empID + "' AND `Start`>'" + dateFormat.format(currDate) + "'";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    result.append("Leave Number: ").append(rs.getInt("leaveNum")).append("\n").append(rs.getDate("Start")).append(" to ").append(rs.getDate("End")).append("\nStatus: ").append(rs.getString("approved")).append("\n-------------------\n");
                }
                return result.toString();
            } else {
                return null;
            }
        } catch (Exception E) {
            System.out.println(E);
        }
        return null;
    }

    public void cancelLeave(int toDel) {
        String query = "UPDATE `leave` SET approved = 'Cancelled' WHERE leaveNum = '" + toDel + "'";
        try {
            Statement stmt = con.createStatement();
            stmt.executeQuery(query);
        } catch (Exception E) {
            System.out.println(E);
        }
    }

    public String generateHoursWorkedReport(List<Integer> empManaged, int month, int year) throws ParseException {
        String query;
        String query2;
        double hoursWorked;
        String dateS = year + "/" + month + "/01";
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        long time = dateFormat.parse(dateS).getTime();
        Timestamp ts = new Timestamp(time);
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            if (empManaged.size() != 0) {
                for (Integer empID : empManaged) {
                    hoursWorked = 0;
                    query = "SELECT name, surname FROM staff WHERE empID = '" + empID + "'";
                    query2 = "SELECT hoursWorked FROM timestamp WHERE empID = '" + empID + "' AND ClockIn > '" + ts + "'";
                    ResultSet rs = stmt.executeQuery(query);
                    rs.next();
                    ResultSet rs2 = stmt.executeQuery(query2);
                    sb.append("For empID:  ").append(empID).append("\nFull Name: ");
                    sb.append(rs.getString("name")).append(" ").append(rs.getString("surname"));
                    while (rs2.next()) {
                        hoursWorked += rs2.getDouble("hoursWorked");
                    }
                    sb.append("\nHours Worked:").append(hoursWorked).append("----------------------");
                }
                return sb.toString();
            } else {
                return "No employees are managed by this manager";
            }
        } catch (Exception E) {
            System.out.println(E);
        }
        return null;
    }
}