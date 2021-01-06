package PersonBuilder;

public abstract class Person {
    private String name, surname, login, password;

    public Person(String name, String surname, String login, String password){
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
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

    public abstract void login(String login, String password);

    public String getName() {
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

}
