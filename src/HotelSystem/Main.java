package HotelSystem;

import Resources.UI;

public class Main {
    public static void main(String[] args){
        UI ui = new UI();
        CustomerHandler ch = new CustomerHandler();
        HumanResources hr = new HumanResources();
        int choice;
        do {
            choice = ui.init();
            switch (choice) {
                case 1:
                    ch.newCustomer(ui.showNewCustomerUI());
                    break;
                case 2:
                    try {
                        ch.login(ui.showLoginUI("Customer"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    hr.login(ui.showLoginUI("Employee"));
                    break;
                case 4:
                    System.out.println("Terminating Process...");
                    break;
                default:
                    ui.showError("Incorrect Entry");
            }
        }while(choice!=4);
//        StorageCustom dbCustom = new StorageCustom();
//        for(int i = 351; i<356; i++){
//            dbCustom.rooms(i);
//        }
    }
}
