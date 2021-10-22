package com.company;

import jdbc.InternetShopSQL;

import java.util.Scanner;

public class AuthUser {

    public AuthUser() {}

    static void registration() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите логин: ");
        String login = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        User user = new User(login, password);
        System.out.println("Ваш логин, пароль:\n" + user);
        InternetShopSQL.insertUser(user);
        System.out.println("Регистрация произошла успешно!\n");
        new Main().runProgram();
    }

    static void logIn() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите логин:");
        String login = scanner.nextLine();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine();

        if (checkingForAdmin(login, password)) {
            System.out.println("Вход произошёл успешно! Вы являетесь администратором!\n");
            new Main().adminMode();
        } else {
            if (InternetShopSQL.findUser(login, password)) {
                System.out.println("Вход произошёл успешно!\n");
                new Main().runProgram();
            } else {
                System.out.println("Ошибка! Проверьте правильность данных или пройдите регистрацию!\n");
                new Main().menu();
            }
        }
    }

    private static boolean checkingForAdmin(String login, String password) {
        return login.equals("admin") && password.equals("admin");
    }
}