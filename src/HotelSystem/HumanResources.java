package HotelSystem;

import PersonBuilder.Employee;
import PersonBuilder.Manager;
import PersonBuilder.Receptionist;
import Resources.PasswordGenerator;
import Resources.SendEmail;
import Resources.UI;
import Storage.Database.StorageEmp;

import javax.mail.MessagingException;
import java.util.Date;

public class HumanResources {

    UI ui = new UI();
    StorageEmp con = new StorageEmp();

    public void login(String[] loginDetails){
        Employee emp = con.getEmployee(loginDetails[0], loginDetails[1]);
        Manager manager = null;
        Receptionist receptionist = null;
        boolean result;

        if (emp != null) {
            if(emp.getJob().equals("Manager")){
                manager = new Manager(emp.getID(), emp.getName(), emp.getSurname(), emp.getLogin(), emp.getPassword(), emp.getJob(), emp.getSalary(), emp.getManager(), emp.getHoursWorked(), emp.getEmail());
                manager.setEmpManaged(con.findManaged(manager.getID()));
            }else if(emp.getJob().equals("Receptionist")){
                receptionist = new Receptionist(emp.getID(), emp.getName(), emp.getSurname(), emp.getLogin(), emp.getPassword(), emp.getJob(), emp.getSalary(), emp.getManager(), emp.getHoursWorked(), emp.getEmail());
            }
            int choice;
            do {
                choice = ui.showEmpOptions();
                switch (choice) {
                    case 1:
                        emp.ClockIn(new Date());
                        break;
                    case 2:
                        emp.ClockOut(new Date());
                        break;
                    case 3:
                        switch(ui.showLeaveOptionMenu()){
                            case 1:
                                emp.requestLeave();
                            case 2:
                                emp.showLeave();
                            case 3:
                                emp.cancelLeave();
                            case 4:
                                break;
                            default:
                                ui.showError("Invalid Option");
                        }
                        break;
                    case 4:
                        if(receptionist!=null){
                            if(receptionist.CheckInCustomer(ui.getSingleInput("Enter Reservation ID"))){
                                ui.showMessage("Customer successfully checked in");
                            }else{
                                ui.showError("Reservation ID not found");
                            }
                        }
                        break;
                    case 5:
                        if(receptionist!=null){
                            if(receptionist.CheckOutCustomer(ui.getSingleInput("Enter Reservation ID"))){
                                ui.showMessage("Customer successfully checked in");
                            }else{
                                ui.showError("Reservation ID not found");
                            }
                        }
                        break;
                    case 6:
                        if(receptionist!=null){
                            receptionist.editBooking(ui.getSingleInput("Enter Reservation ID"));
                        }
                        break;
                    case 7:
                        if(receptionist!=null){
                            receptionist.createJob(Integer.parseInt(ui.getSingleInput("Enter Room Number that needs work done")));
                        }
                        break;
                    case 8:
                        if(manager!=null){
                            manager.approval();
                        }
                        break;
                    case 9:
                        if(emp.getJob().equals("HR")){
                            addEmployee();
                        }
                        break;
                    case 10:
                        break;
                    default:
                        ui.showError("Invalid Option");

                }
            }while (choice != 4);
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

        String[] details = ui.showNewEmpUI();

        String name = details[0];
        String surname = details[1];
        double salary = Double.parseDouble(details[2]);
        String job = details[3];
        int managerID = Integer.parseInt(details[4]);
        String email = details[5];


        String login = name.substring(0, 1) + surname + empID;
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
    }

}