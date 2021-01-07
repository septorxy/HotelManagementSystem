package HotelSystem;

import Storage.Database.StorageEmp;

public class Main {
    public static void main(String[] args){
       StorageEmp store =  new StorageEmp();

        HumanResources humanResources = new HumanResources();

        humanResources.addEmployee();

    }
}
