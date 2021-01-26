package HotelSystem;

import PersonBuilder.Employee;
import PersonBuilder.Manager;
import PersonBuilder.Receptionist;
import Resources.PasswordGenerator;
import Resources.SendEmail;
import Resources.UI;
import Storage.Database.StorageEmp;

import javax.mail.MessagingException;


public class HumanResources {

    UI ui = new UI();
    StorageEmp con = new StorageEmp();

    public void login(String[] loginDetails) throws Exception {
        Employee emp = con.getEmployee(loginDetails[0], loginDetails[1]);
        Manager manager = null;
        Receptionist receptionist = null;
        String[] jobs = new String[0];

        if (emp != null) {
            if (emp.getJob().equals("Manager")) {
                manager = new Manager(emp.getID(), emp.getName(), emp.getSurname(), emp.getLogin(), emp.getPassword(), emp.getJob(), emp.getSalary(), emp.getManager(), emp.getHoursWorked(), emp.getEmail());
                manager.setEmpManaged(con.findManaged(manager.getID()));
                jobs = manager.inherit();
                for (String job : jobs) {
                    if (job.equals("Receptionist")) {
                        receptionist = new Receptionist(emp.getID(), emp.getName(), emp.getSurname(), emp.getLogin(), emp.getPassword(), emp.getJob(), emp.getSalary(), emp.getManager(), emp.getHoursWorked(), emp.getEmail());
                        break;
                    }
                }
            } else if (emp.getJob().equals("Receptionist")) {
                receptionist = new Receptionist(emp.getID(), emp.getName(), emp.getSurname(), emp.getLogin(), emp.getPassword(), emp.getJob(), emp.getSalary(), emp.getManager(), emp.getHoursWorked(), emp.getEmail());
            }
            int choice;
            do {
                choice = ui.showEmpOptions();
                try {
                    switch (choice) {
                        case 1:
                            emp.ClockIn();
                            break;
                        case 2:
                            emp.ClockOut();
                            break;
                        case 3:
                            switch (ui.showLeaveOptionMenu()) {
                                case 1:
                                    emp.requestLeave();
                                    break;
                                case 2:
                                    emp.showLeave();
                                    break;
                                case 3:
                                    emp.cancelLeave();
                                    break;
                                case 4:
                                    break;
                                default:
                                    ui.showError("Invalid Option");
                            }
                            break;
                        case 4:
                            if (receptionist != null) {
                                if (receptionist.CheckInCustomer(ui.getSingleInput("Enter Reservation ID"))) {
                                    ui.showMessage("Customer successfully checked in");
                                } else {
                                    ui.showError("Reservation ID not found");
                                }
                            } else {
                                ui.showError("You do not have permission for this!");
                            }
                            break;
                        case 5:
                            if (receptionist != null) {
                                if (receptionist.CheckOutCustomer(ui.getSingleInput("Enter Reservation ID"))) {
                                    ui.showMessage("Customer successfully checked in");
                                } else {
                                    ui.showError("Reservation ID not found");
                                }
                            } else {
                                ui.showError("You do not have permission for this!");
                            }
                            break;
                        case 6:
                            if (receptionist != null) {
                                receptionist.editBooking(ui.getSingleInput("Enter Reservation ID"));
                            } else {
                                ui.showError("You do not have permission for this!");
                            }
                            break;
                        case 7:
                            if (receptionist != null) {
                                receptionist.createJob();
                            } else {
                                ui.showError("You do not have permission for this!");
                            }
                            break;
                        case 8:
                            if (manager != null) {
                                manager.approval();
                            } else {
                                ui.showError("You do not have permission for this!");
                            }
                            break;
                        case 9:
                            if (emp.getJob().equals("HR")) {
                                addEmployee();
                            } else if (manager != null) {
                                for (String job : jobs) {
                                    if (job.equals("HR")) {
                                        addEmployee();
                                    }
                                }
                            } else {
                                ui.showError("You do not have permission for this!");
                            }
                            break;
                        case 10:
                            if (manager != null) {
                                try {
                                    int[] toSend = ui.getMonthAndYear();
                                    ui.showMessage(manager.generateReport(toSend[0], toSend[1]));
                                } catch (Exception e) {
                                    ui.showError("Process Cancelled");
                                }
                            } else {
                                ui.showError("You do not have permission for this!");
                            }
                            break;
                        case 11:
                            break;
                        default:
                            ui.showError("Invalid Option");

                    }
                } catch (Exception E) {
                    ui.showError("Process Cancelled");
                }
            } while (choice != 11);
        } else {
            ui.showError("The login or password is incorrect. Please try again.");
        }
    }

    public void addEmployee() {
        boolean isAvail = false;
        double hoursWorked = 0;
        int empID;
        Employee newEmp;
        do {
            empID = (int) (Math.random() * (99999 - 10000) + 10000);
            if (!con.existsEmpID(empID)) {
                isAvail = true;
            }
        } while (!isAvail);
        try {
            String[] details = ui.showNewEmpUI();

            String name = details[0];
            String surname = details[1];
            double salary = Double.parseDouble(details[2]);
            String job = details[3];
            int managerID = Integer.parseInt(details[4]);
            String email = details[5];


            String login = name.charAt(0) + surname + empID;
            PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                    .useDigits(true)
                    .useLower(true)
                    .useUpper(true)
                    .build();
            String password = passwordGenerator.generate(8);


            newEmp = new Employee(empID, name, surname, login, password, job, salary, managerID, hoursWorked, email);

            con.addNewEmp(newEmp);

            try {
                SendEmail.sendMail(email, login, password);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        } catch (Exception E) {
            ui.showError("Process Cancelled");
        }
    }

}