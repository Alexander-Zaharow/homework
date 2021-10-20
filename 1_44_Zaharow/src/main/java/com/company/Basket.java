package com.company;

public class Basket {
    private String login;
    private int clothe_id;

    public Basket(String login, int clothe_id) {
        this.login = login;
        this.clothe_id = clothe_id;
    }

    @Override
    public String toString() {
        return login + ", " + clothe_id;
    }
}
