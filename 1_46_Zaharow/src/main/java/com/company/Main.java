package com.company;

import hibernateORM.InternetShopORM;
import jdbc.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new Main().menu();
    }

    void createAndFillInBasicTables() { // Для первого запуска приложения
        InternetShopSQL.createTables();

        Clothe[] clothes = new Clothe[]{
                new Clothe("Толстовка", "S", "синий", 2650),
                new Clothe("Толстовка", "XS", "красный", 3200),
                new Clothe("Футболка", "S", "красный", 550),
                new Clothe("Футболка", "M", "чёрный", 300),
                new Clothe("Брюки", "XXS", "синий", 700),
                new Clothe("Брюки", "L", "красный", 1000),
        };
        for (Clothe clothe: clothes) {
            InternetShopSQL.insertClotheOnSale(clothe);
        }
    }

    void menu() { // Главное меню
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Добро пожаловать! ---\n" +
                "Выберите действие:\n" +
                "Регистрация - <reg>\n" +
                "Вход в систему - <login>\n" +
                "Выход из приложения - <Stop>");

        switch(scanner.nextLine()) {
            case "reg": AuthUser.registration(); break;
            case "login": AuthUser.logIn(); break;
        }
    }

    void runProgram() { // jdbc
        Menu menu = new Menu("");
        MenuStream shop = new MenuStream();

        String command = "";

        cycle:
        while (!command.equals("Stop")) {
            switch(command) {
                case "basket": shop.workWithBasket(); break cycle;
                case "catalog": shop.openCatalog(); break;
                case "sweatshirts": shop.workWithItem("Толстовка"); break;
                case "tshirts": shop.workWithItem("Футболка"); break;
                case "trousers": shop.workWithItem("Брюки"); break;
            }
            command = shop.readCommand();
        }
    }

    void adminMode() { // hibernateORM (Для запуска режима введите в режиме входа(login) login=admin, password=admin)
        Scanner scanner = new Scanner(System.in);
        InternetShopORM shopORM = new InternetShopORM();

        System.out.println("Выберите действие:\n" +
                "Показать всех пользователей - <users>\n" +
                "Показать продаваемую одежду - <clothes>\n" +
                "Показать корзины пользователей - <baskets> \n" +
                "Добавить предмет одежды на продажу - <add> \n" +
                "Обновить цену на предмет одежды - <update> \n" +
                "Убрать предмет одежды с продажи - <delete> \n" +
                "Выход из приложения - <Stop>");

        String command = "";

        while (!command.equals("Stop")) {
            switch(command) {
                case "users": shopORM.showUsersIntShop(); break;
                case "clothes": shopORM.showClothesOnSale(); break;
                case "baskets": shopORM.showBasket(); break;
                case "add":
                    System.out.println("Введите тип одежды: ");
                    String type = scanner.nextLine();
                    System.out.println("Введите размер одежды: ");
                    String size = scanner.nextLine();
                    System.out.println("Введите цвет одежды: ");
                    String color = scanner.nextLine();
                    System.out.println("Введите стоимость одежды: ");
                    int cost = scanner.nextInt();
                    shopORM.addClothesOnSale(type, size, color, cost);
                    break;
                case "update":
                    System.out.println("Введите идентификатор(id) одежды: ");
                    int id = scanner.nextInt();
                    System.out.println("Введите новое значение стоимости одежды: ");
                    int costClothe = scanner.nextInt();
                    shopORM.updateСostClothes(id, costClothe);
                    break;
                case "delete":
                    System.out.println("Введите идентификатор(id) одежды: ");
                    int idClothe = scanner.nextInt();
                    shopORM.removeClothes(idClothe);
                    break;
            }
            command = scanner.nextLine();
        }
    }
}