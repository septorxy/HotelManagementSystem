package PersonBuilder;

public abstract class Person {
    private String name, surname, login, password, email;
    private int ID;

    public Person(String name, String surname, String login, String password, int ID, String email){
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.ID = ID;
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return this.login;
    }

    public  String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getID() { return ID; }

    public void setID(int ID) { this.ID = ID; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
