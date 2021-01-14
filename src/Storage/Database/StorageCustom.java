package Storage.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class StorageCustom {
    private Connection con = null;

    public StorageCustom(){
        try{
            Class.forName("org.mariadb.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mariadb://localhost:3306/customerdb","test","test");
        }catch(Exception e){ System.out.println(e);}
    }

    public void close(){
        try {
            con.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    //NOT DONE TODO
    public void addNewCustom(int ID, String name, String surname, String login, String password, String email){
        String query = "";
        try {
            Statement stmt = con.createStatement();
            stmt.executeQuery(query);
        }catch (Exception E){ System.out.println(E); }
    }

    public boolean Backup(){

         return false;
    }
}
