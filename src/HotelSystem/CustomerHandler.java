package HotelSystem;

import PersonBuilder.Customer;
import Resources.UI;
import Storage.Database.StorageCustom;

public class CustomerHandler {
    private StorageCustom dbCustom = new StorageCustom();
    private UI ui = new UI();


    //The methods below should be within the web part of the project but since we are not doing that part for the prototype the methods are implemented here


    public void newCustomer(String[] details) {
        String login = details[0].substring(0, 1) + details[1] + details[3];
        Customer newCustom = new Customer(details[0], details[1], login, details[2], Integer.parseInt(details[3]), details[4]);
        if (!dbCustom.existsCustomer(newCustom.getID())) {
            dbCustom.addNewCustom(String.valueOf(newCustom.getID()), newCustom.getName(), newCustom.getSurname(), newCustom.getLogin(), newCustom.getPassword(), newCustom.getEmail());
        } else {
            ui.showError("This customer already exists");
        }
    }


    public void login(String[] loginDetails) throws Exception {
        Customer c = dbCustom.getCustomer(loginDetails[0], loginDetails[1]);
        if (c != null) {
            int choice;
            do {
                choice = ui.showUserOptions();
                switch (choice) {
                    case 1:
                        c.makeBooking();
                        break;
                    case 2:
                        c.showAllBookings();
                        break;
                    case 3:
                        switch (ui.showBookingOptions()){
                            case 1:
                                c.editBooking(ui.getSingleInput("Booking Code"));
                                break;
                            case 2:
                                c.cancelBooking(ui.getSingleInput("Booking Code"));
                                break;
                            case 3:
                                break;
                            default:
                                ui.showError("Invalid Entry");
                        }
                        break;
                    case 4:
                        userSettings();
                        break;
                    case 5:
                        break;
                    default:
                        ui.showError("Invalid Option");

                }
            }while (choice != 4);
        } else {
            ui.showError("The login or password is incorrect. Please try again.");
        }
    }

    public void userSettings() {
        //Gives user option to change login and password
    }


}
