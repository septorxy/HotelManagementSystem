package Storage;

import java.sql.Connection;
import java.sql.DriverManager;

public class StorageCustom {
    private Connection con = null;

    public StorageCustom(){
        try{
            Class.forName("org.mariadb.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mariadb://localhost:3306/customerdb","test","test");
        }catch(Exception e){ System.out.println(e);}
    }
}
