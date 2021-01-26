package Resources;

import HotelSystem.BookingsManager.Reservation;
import HotelSystem.BookingsManager.Service;

import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UI {
    public int init() {
        String choiceS = JOptionPane.showInputDialog("1. For new customer\n2. For Login Customer\n3. For Login Emp\n4. Exit Program");
        if (choiceS == null) {
            return 4;
        } else if (choiceS.equals("")) {
            return 0;
        }
        try {
            return Integer.parseInt(choiceS);
        } catch (NumberFormatException fe) {
            return 0;
        }
    }


    public String[] showNewEmpUI() throws Exception {
        JTextField name = new JTextField();
        JTextField surname = new JTextField();
        JTextField salary = new JTextField();
        JTextField job = new JTextField();
        JTextField managerID = new JTextField();
        JTextField email = new JTextField();
        Object[] fields = {
                "Name", name,
                "Surname", surname,
                "Yearly Salary", salary,
                "Job", job,
                "Manager / Supervisor", managerID,
                "E-mail", email
        };
        boolean verified = true;
        String problem = "";
        do {
            if (!verified) {
                verified = true;
                showError("You have entered incorrect data: " + problem);
                problem = "";
            }
            int result = JOptionPane.showConfirmDialog(null, fields, "Employee Input", JOptionPane.YES_NO_CANCEL_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                if (name.getText().equals("") || surname.getText().equals("") || salary.getText().equals("") || job.getText().equals("") || managerID.getText().equals("") || email.getText().equals("")) {
                    verified = false;
                    problem += "\nOne or more fields have been left empty.";
                } else if (!Validate.isAlpha(name.getText()) || !Validate.isAlpha(surname.getText()) || !Validate.isJob(job.getText()) || !Validate.isEmail(email.getText()) || !Validate.isNumber(salary.getText(), "Integer") || !Validate.isNumber(managerID.getText(), "Integer") || Integer.parseInt(managerID.getText()) < 10000 || Integer.parseInt(managerID.getText()) > 99999) {
                    verified = false;
                    problem += "\nData inputted is not correct.";
                }
            } else {
                throw new Exception();
            }
        } while (!verified);
        return new String[]{
                name.getText(), surname.getText(), salary.getText(), job.getText(), managerID.getText(), email.getText()
        };
    }

    public String[] showNewCustomerUI() throws Exception {
        JTextField name = new JTextField();
        JTextField surname = new JTextField();
        JTextField ID = new JTextField();
        JTextField password = new JTextField();
        JTextField email = new JTextField();
        Object[] fields = {
                "Name", name,
                "Surname", surname,
                "ID (without letters)", ID,
                "Password", password,
                "E-mail", email
        };
        boolean verified = true;
        String problem = "";
        do {
            if (!verified) {
                verified = true;
                showError("You have entered incorrect data: " + problem);
                problem = "";
            }
            int result = JOptionPane.showConfirmDialog(null, fields, "New Customer Input", JOptionPane.YES_NO_CANCEL_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                if (name.getText().equals("") || surname.getText().equals("") || ID.getText().equals("") || password.getText().equals("") || email.getText().equals("")) {
                    verified = false;
                    problem += "\nOne or more fields have been left empty.";
                } else if (!Validate.isAlpha(name.getText()) || !Validate.isAlpha(surname.getText()) || !Validate.isAlphaNum(password.getText()) || !Validate.isNumber(ID.getText(), "Integer") || !Validate.isEmail(email.getText())) {
                    verified = false;
                    problem += "\nData inputted is not correct.";
                }
            } else {
                throw new Exception();
            }
        } while (!verified);
        return new String[]{
                name.getText(), surname.getText(), password.getText(), ID.getText(), email.getText()
        };
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }


    public String[] showLoginUI(String type) throws Exception {
        JTextField login = new JTextField();
        JTextField password = new JTextField();
        Object[] fields = {
                "Login", login,
                "Password", password,
        };
        boolean verified = true;
        String problem = "";
        int result;
        do {
            if (!verified) {
                verified = true;
                showError("You have entered incorrect data: " + problem);
                problem = "";
            }
            if (type.equals("Customer")) {
                result = JOptionPane.showConfirmDialog(null, fields, "Customer Login", JOptionPane.YES_NO_CANCEL_OPTION);
            } else {
                result = JOptionPane.showConfirmDialog(null, fields, "Employee Login", JOptionPane.YES_NO_CANCEL_OPTION);
            }
            if (result == JOptionPane.YES_OPTION) {
                if (login.getText().equals("") || password.getText().equals("")) {
                    verified = false;
                    problem += "\nOne or more fields have been left empty.";
                } else if (!Validate.isAlphaNum(login.getText()) || !Validate.isAlphaNum(password.getText())) {
                    verified = false;
                    problem += "\nData inputted is not correct.";
                }
            } else {
                throw new Exception();
            }

        } while (!verified);
        return new String[]{
                login.getText(), password.getText()
        };
    }


    public int showUserOptions() {
        String choiceS = JOptionPane.showInputDialog("" +
                "1. New Reservation\n" +
                "2. Show current bookings\n" +
                "3. Reservation Manager\n" +
                "4. Services\n" +
                "5. Settings\n" +
                "6. Log Out");
        if (choiceS == null) {
            return 6;
        } else if (choiceS.equals("")) {
            return 0;
        }
        return Integer.parseInt(choiceS);
    }

    public String[] showNewBookingUI() throws Exception {
        JTextField dateIn = new JTextField();
        JTextField dateOut = new JTextField();
        JTextField roomType = new JTextField();// Would be a combo box in final product
        JTextField numberOfRooms = new JTextField();
        Object[] fields = {
                "Date-In (yyyy/mm/dd)", dateIn,
                "Date-Out (yyyy/mm/dd)", dateOut,
                "Number of Rooms (Max 3)", numberOfRooms,
                "Room Types (Standard, Suite, Presidential)", roomType // In final product different types of standard/suites/presidential exists based on num of ppl in room
        };
        boolean verified = true;
        String problem = "";
        do {
            if (!verified) {
                verified = true;
                showError("You have entered incorrect data: " + problem);
                problem = "";
            }
            int result = JOptionPane.showConfirmDialog(null, fields, "New Reservation", JOptionPane.YES_NO_CANCEL_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                if (dateIn.getText().equals("") || dateOut.getText().equals("") || numberOfRooms.getText().equals("") || roomType.getText().equals("")) {
                    verified = false;
                    problem += "\nOne or more fields have been left empty.";
                } else if (!Validate.isDate(dateIn.getText()) || !Validate.isDate(dateOut.getText())) {
                    verified = false;
                    problem += "\nDate is not inputted correctly. Must be inputted as yyyy/mm/dd";
                } else if (dateFormat.parse(dateIn.getText()).compareTo(dateFormat.parse(dateOut.getText())) > 0) {
                    verified = false;
                    problem += "\nTime doesn't flow backwards! Date-In must be before Date-Out.";
                } else if (!Validate.isNumber(numberOfRooms.getText(), "Integer") || Integer.parseInt(numberOfRooms.getText()) <= 0 || Integer.parseInt(numberOfRooms.getText()) > 3) {
                    verified = false;
                    problem += "\nNumber of rooms must be an integer between 1 and 3";
                } else if (!Validate.isRoomType(roomType.getText())) {
                    verified = false;
                    problem += "\nInvalid Room Type";
                }
            } else {
                throw new Exception();
            }
        } while (!verified);
        return new String[]{
                dateIn.getText(), dateOut.getText(), numberOfRooms.getText(), roomType.getText()
        };
    }

    public String getSingleInput(String message) throws Exception {
        String inp;
        boolean verified;
        do {
            verified = true;
            inp = JOptionPane.showInputDialog(message);
            if (inp == null) {
                throw new Exception();
            } else if (inp.equals("") || inp.length() != 6 || !Validate.isAlpha(inp)) {
                showError("Reservation ID must be 6 characters long");
                verified = false;
            }
        } while (!verified);
        return inp;
    }

    public int showEmpOptions() {
        String choiceS = JOptionPane.showInputDialog("" +
                "1. Clock-In\n" +
                "2. Clock-Out\n" +
                "3. Leave\n" +
                "4. Check-In Customer (Receptionist)\n" +
                "5. Check-Out Customer (Receptionist)\n" +
                "6. Edit Booking (Receptionist)\n" +
                "7. Create Job (Manager/Receptionist)\n" +
                "8. Approve Leave (Manager)\n" +
                "9. Add Employee (HR)\n" +
                "10. Generate Hours Worked Report\n" +
                "11. Log Out");
        if (choiceS == null) {
            return 11;
        } else if (choiceS.equals("")) {
            return 0;
        }
        try {
            return Integer.parseInt(choiceS);
        } catch (NumberFormatException fe) {
            return 0;
        }
    }

    public int showLeaveOptionMenu() {
        String choiceS = JOptionPane.showInputDialog("" +
                "1. Create new Leave\n" +
                "2. View Booked Leave\n" +
                "3. Cancel Leave\n" +
                "4. Back");
        if (choiceS == null) {
            return 4;
        } else if (choiceS.equals("")) {
            return 0;
        }
        try {
            return Integer.parseInt(choiceS);
        } catch (NumberFormatException fe) {
            return 0;
        }
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    public void formatBookings(Reservation[] reservations) {
        StringBuilder sb = new StringBuilder();
        if (reservations != null) {
            for (Reservation res : reservations) {
                sb.append("Reservation ID: ").append(res.getResID()).append("\n");
                sb.append("Check-In Date: ").append(res.getDatesOfStay()[0]).append("\n");
                sb.append("Check-Out Date: ").append(res.getDatesOfStay()[1]).append("\n");
                sb.append("Rooms Booked: ").append(res.getRoomsBooked()[0].getRoomNum());
                if (res.getRoomsBooked()[2] != null) {
                    sb.append(", ").append(res.getRoomsBooked()[1].getRoomNum());
                    sb.append(", ").append(res.getRoomsBooked()[2].getRoomNum());
                } else if (res.getRoomsBooked()[1] != null) {
                    sb.append(", ").append(res.getRoomsBooked()[1].getRoomNum());
                }
                sb.append("\n");
                sb.append("--------------------------\n");
            }
            JOptionPane.showMessageDialog(null, sb, "All bookings for customer ID: " + reservations[0].getResOwnerID(), JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No Bookings here!");
        }
    }

    public int showBookingOptions() {
        String choiceS = JOptionPane.showInputDialog("" +
                "1. Edit Booking\n" +
                "2. Cancel Booking\n" +
                "3. Back");
        if (choiceS == null) {
            return 3;
        } else if (choiceS.equals("")) {
            return 0;
        }
        try {
            return Integer.parseInt(choiceS);
        } catch (NumberFormatException fe) {
            return 0;
        }
    }

    public String[] showEditOptions(Reservation toChange) throws Exception {
        JTextField dateIn = new JTextField();
        JTextField dateOut = new JTextField();
        JTextField numberOfRooms = new JTextField();
        Object[] fields = {
                "Current Date In: " + toChange.getDatesOfStay()[0], dateIn,
                "Current Date Out: " + toChange.getDatesOfStay()[1], dateOut,
                "Current Number of Rooms: " + toChange.getRoomsBooked().length, numberOfRooms,
        };
        boolean verified = true;
        String problem = "";
        do {
            if (!verified) {
                verified = true;
                showError("You have entered incorrect data: " + problem);
                problem = "";
            }
            int result = JOptionPane.showConfirmDialog(null, fields, "Edit Reservation", JOptionPane.YES_NO_CANCEL_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                if (dateIn.getText().equals("") || dateOut.getText().equals("") || numberOfRooms.getText().equals("")) {
                    verified = false;
                    problem += "\nOne or more fields have been left empty.";
                } else if (!Validate.isDate(dateIn.getText()) || !Validate.isDate(dateOut.getText())) {
                    verified = false;
                    problem += "\nDate is not inputted correctly. Must be inputted as yyyy/mm/dd";
                } else if (dateFormat.parse(dateIn.getText()).compareTo(dateFormat.parse(dateOut.getText())) > 0) {
                    verified = false;
                    problem += "\nTime doesn't flow backwards! Date-In must be before Date-Out.";
                } else if (!Validate.isNumber(numberOfRooms.getText(), "Integer") || Integer.parseInt(numberOfRooms.getText()) <= 0 || Integer.parseInt(numberOfRooms.getText()) > 3) {
                    verified = false;
                    problem += "\nNumber of rooms must be an integer between 1 and 3";
                }
            } else {
                throw new Exception();
            }
        } while (!verified);
        return new String[]{
                dateIn.getText(), dateOut.getText(), numberOfRooms.getText()
        };
    }

    public Date[] showLeaveWindow() throws Exception {
        JTextField dateIn = new JTextField();
        JTextField dateOut = new JTextField();
        Object[] fields = {
                "Start Date", dateIn,
                "End Date", dateOut
        };

        boolean verified = true;
        String problem = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        do {
            if (!verified) {
                verified = true;
                showError("You have entered incorrect data: " + problem);
                problem = "";
            }
            int result = JOptionPane.showConfirmDialog(null, fields, "Leave Dates", JOptionPane.YES_NO_CANCEL_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                if (dateIn.getText().equals("") || dateOut.getText().equals("")) {
                    verified = false;
                    problem = "\nOne or more fields have been left empty.";
                } else if (!Validate.isDate(dateIn.getText()) || !Validate.isDate(dateOut.getText())) {
                    verified = false;
                    problem = "\nDate is not inputted correctly. Must be inputted as yyyy/mm/dd";
                } else if (dateFormat.parse(dateIn.getText()).compareTo(dateFormat.parse(dateOut.getText())) > 0) {
                    verified = false;
                    problem = "\nTime doesn't flow backwards! Date-In must be before Date-Out.";
                }
            } else {
                throw new Exception();
            }
        } while (!verified);
        return new Date[]{
                new SimpleDateFormat("yyyy/MM/dd").parse(dateIn.getText()), new SimpleDateFormat("yyyy/MM/dd").parse(dateOut.getText())
        };
    }

    public boolean showLeaveApproval(int empID, String name, String surname, java.sql.Date start, java.sql.Date end) throws Exception {
        String message = "ID: " + empID + "\nFull Name: " + name + " " + surname + "\nDate Start: " + start + "\nEnd: " + end + "\nApprove?";
        int result = JOptionPane.showConfirmDialog(null, message, "Approved", JOptionPane.YES_NO_CANCEL_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            return true;
        } else if (result == JOptionPane.NO_OPTION) {
            return false;
        } else {
            throw new Exception();
        }
    }

    public void showAllLeave(String allLeave) {
        if (allLeave != null) {
            JOptionPane.showMessageDialog(null, allLeave, "All Leave Bookings", JOptionPane.INFORMATION_MESSAGE);
        } else {
            showError("No Leave Bookings Here :)");
        }
    }

    public int showLeaveCancellation(String allLeave) throws Exception {
        String toRet = JOptionPane.showInputDialog(null, allLeave + "\n Kindly choose which leave to cancel", "All Leave Bookings", JOptionPane.INFORMATION_MESSAGE);
        if (toRet == null || toRet.equals("")) {
            throw new Exception();
        } else {
            return Integer.parseInt(toRet);
        }
    }


    public Service showServiceWindow(Reservation res) throws Exception {
        JTextField type = new JTextField();
        JTextField date = new JTextField();
        JTextField time = new JTextField();
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
        String startTime = "08:00";
        String endTime = "20:00";
        Object[] fields = {
                "Service Required (Pool, Spa, Restaurant, Room Service)", type,
                "Date (yyyy/mm/dd)", date,
                "Time 24-hour clock (HH:mm)", time
        };
        boolean verified = true;
        String problem = "";
        do {
            if (!verified) {
                verified = true;
                showError("You have entered incorrect data: " + problem);
                problem = "";
            }
            int result = JOptionPane.showConfirmDialog(null, fields, "Add Services", JOptionPane.YES_NO_CANCEL_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                if (date.getText().equals("") || type.getText().equals("") || time.getText().equals("")) {
                    verified = false;
                    problem = "\nOne or more fields have been left empty.";
                } else if (!Validate.isDate(date.getText())) {
                    verified = false;
                    problem = "\nDate is not inputted correctly. Must be inputted as yyyy/mm/dd";
                } else if (!Validate.isTime(time.getText())) {
                    verified = false;
                    problem = "\nTime is not inputted correctly. Must be inputted as HH:mm";
                } else if (!Validate.isServiceType(type.getText())) {
                    verified = false;
                    problem = "\nIncorrect Service Type";
                } else if (dateFormat2.parse(time.getText()).compareTo(dateFormat2.parse(startTime)) < 0 || dateFormat2.parse(time.getText()).compareTo(dateFormat2.parse(endTime)) > 0) {
                    verified = false;
                    problem = "\nServices only available between 08:00 and 20:00";
                } else if (dateFormat1.parse(date.getText()).compareTo(res.getDatesOfStay()[0]) < 0 || dateFormat1.parse(date.getText()).compareTo(res.getDatesOfStay()[1]) > 0) {
                    verified = false;
                    problem = "\nDate must be between your dates of stay";
                }
            } else {
                throw new Exception();
            }
        } while (!verified);

        return new Service(dateFormat1.parse(date.getText()), dateFormat2.parse(time.getText()), type.getText());
    }

    public int[] getMonthAndYear() {
        JTextField month = new JTextField();
        JTextField year = new JTextField();
        Object[] fields = {
                "Month:", month,
                "Year:", year
        };

        boolean verified = true;
        String problem = "";
        do {
            if (!verified) {
                verified = true;
                showError("You have entered incorrect data: " + problem);
                problem = "";
            }
            int result = JOptionPane.showConfirmDialog(null, fields, "Month and year to generate report", JOptionPane.YES_NO_CANCEL_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                if (month.getText().equals("") || year.getText().equals("")) {
                    verified = false;
                    problem = "\nOne or more fields have been left empty.";
                } else if (!Validate.isNumber(month.getText(), "Integer") || !Validate.isNumber(year.getText(), "Integer") || Integer.parseInt(month.getText()) > 12 || Integer.parseInt(month.getText()) < 0) {
                    verified = false;
                    problem = "\nPlease enter a valid month and year number.";
                }
            }
        } while (!verified);
        return new int[]{
                Integer.parseInt(month.getText()), Integer.parseInt(year.getText())
        };
    }
}
