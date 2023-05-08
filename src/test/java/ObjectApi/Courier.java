package ObjectApi;

public class Courier {
    private String login;
    private String password;
    private String firstName;


    public Courier(String login, String password, String firstName) {

        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }
    public Courier(String login, String password) {

        this.login = login;
        this.password = password;
        this.firstName = null;
    }

    public Courier() {

    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }



    public void setLogin(String login) {
        this.login = login;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

