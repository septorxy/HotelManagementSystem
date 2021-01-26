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
                    try {
                        ch.newCustomer(ui.showNewCustomerUI());
                    } catch (Exception e) {
                        ui.showError("Process Cancelled");
                    }
                    break;
                case 2:
                    try {
                        ch.login(ui.showLoginUI("Customer"));
                    } catch (Exception e) {
                        ui.showError("Process Cancelled");
                    }
                    break;
                case 3:
                    try {
                        hr.login(ui.showLoginUI("Employee"));
                    } catch (Exception e) {
                        ui.showError("Process Cancelled");
                    }
                    break;
                case 4:
                    System.out.println("Terminating Process...");
                    break;
                default:
                    ui.showError("Incorrect Entry");
            }
        }while(choice!=4);
    }
}
