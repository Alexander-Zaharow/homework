package com.company;

public class Phone {
    private int user_id;
    private String phone_number;

    public Phone(int user_id, String phone_number) {
        this.user_id = user_id;
        this.phone_number = phone_number;
    }

    public Phone(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public int getUser_id() {
        return user_id;
    }

    @Override
    public String toString() {
        return user_id + ", " + phone_number;
    }
}
