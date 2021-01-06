package PersonBuilder;
public class Receptionist extends Employee {

    public Receptionist(int empID, String name, String surname, String login, String password, String job, double salary, int managerID) {
        super(empID, name, surname, login, password, job, salary, managerID);
    }

    public void createJob() {
        // TODO - implement Receptionist.createJob
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param resID
     */
    public void editBooking(String resID) {
        // TODO - implement Receptionist.editBooking
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param reservation
     */
    public boolean confirmation(String reservation) {
        // TODO - implement Receptionist.confirmation
        throw new UnsupportedOperationException();
    }

    public void assignJob() {
        // TODO - implement Receptionist.assignJob
        throw new UnsupportedOperationException();
    }

}