package model;

public class UsersIntShop {
    private int user_id;
    private String login;
    private String password;

    public UsersIntShop() {}

    public UsersIntShop(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User id: " + user_id + ", Login: " + login + ", Password: " + password;
    }
}