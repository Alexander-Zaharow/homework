package com.company;

import jdbc.InternetShopSQL;

import java.util.Scanner;

public class AuthUser {
    public AuthUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите логин: ");
        String login = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        User user = new User(login, password);
        System.out.println("Ваш логин, пароль:\n" + user);
        InternetShopSQL.insertUser(user);
    }
}
