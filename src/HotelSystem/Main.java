package HotelSystem;

import Storage.StorageEmp;

public class Main {
    public static void main(String[] args){
       StorageEmp store =  new StorageEmp();
       //store.addNewEmp(11100,"Mikel","Mangion","MMangion11100","pass",10000,50,"Receptionist",11000);
       //boolean check = store.searchEmpID(11100);
      // System.out.println(check);

        HumanResources humanResources = new HumanResources();

        humanResources.addEmployee();

    }
}
