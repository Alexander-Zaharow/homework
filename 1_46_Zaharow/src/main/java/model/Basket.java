package model;

public class Basket {
    private String login;
    private int clothe_id;

    public Basket() {}

    public Basket(String login, int clothe_id) {
        this.login = login;
        this.clothe_id = clothe_id;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public int getClothe_id() {
        return clothe_id;
    }
    public void setClothe_id(int clothe_id) {
        this.clothe_id = clothe_id;
    }

    @Override
    public String toString() {
        return "Login: " + login + ", Clothe id: " + clothe_id;
    }
}