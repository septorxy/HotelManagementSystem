package PersonBuilder;

public class Customer extends Person {

    public Customer(String name, String surname, String login, String password, int ID) {
        super(name, surname, login, password, ID);
    }

    public void setDates() {
        // TODO - implement Customer.setDates
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param service
     */
    public void setService(String service) {
        // TODO - implement Customer.setService
        throw new UnsupportedOperationException();
    }

    public String makeBooking() {
        // TODO - implement Customer.makeBooking
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param resID
     */
    public void editBooking(String resID) {
        // TODO - implement Customer.editBooking
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param resID
     */
    public boolean cancelBooking(String resID) {
        // TODO - implement Customer.cancelBooking
        throw new UnsupportedOperationException();
    }

    public void makePayment() {
        // TODO - implement Customer.makePayment
        throw new UnsupportedOperationException();
    }

    public void makeComplaint() {
        // TODO - implement Customer.makeComplaint
        throw new UnsupportedOperationException();
    }

    @Override
    public void login(String login, String password) {

    }
}