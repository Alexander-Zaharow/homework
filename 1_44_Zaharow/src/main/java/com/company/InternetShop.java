package com.company;

import jdbc.InternetShopSQL;

import java.util.Scanner;

public interface InternetShop {
    void print();
}

class Menu implements InternetShop{
    private String catalog;
    public void print() {System.out.println(catalog);}

    void setCatalog(String catalog) {
        this.catalog = catalog;
    };

    Menu(String content) {
        catalog = content;
    }
}

class MenuStream { // меню
    private Scanner scanner = new Scanner(System.in);
    private Menu menuMain = new Menu("");

    MenuStream() {
        menuMain.setCatalog("Выберите действие: " +
                "\nОткрыть список катологов - <catalog>" +
                "\nПерейти в корзину - <basket>" +
                "\nВыход из программы - <Stop>");
        menuMain.print();
    }

    String readCommand() {
        return scanner.nextLine();
    }

    void openCatalog() {
        menuMain.setCatalog("Толстовки - <sweatshirts>" +
                "\nФутболки - <tshirts>" +
                "\nБрюки - <trousers>" +
                "\nПерейти в корзину - <basket>" +
                "\nВыход из программы - <Stop>");
        menuMain.print();
    }

    void workWithItem(String item) {
        System.out.print("Введите свой логин: ");
        String login = scanner.nextLine();

        InternetShopSQL.findClothes(item);
        System.out.print("Введите индекс интересующего товара: ");
        int pick = scanner.nextInt();
        System.out.println("Добавить данный товар в корзину? (да/нет) - <1/2>");
        int yesNo = scanner.nextInt();
        if (yesNo == 1) {
            if (InternetShopSQL.findUser(login)) {
                InternetShopSQL.insertClotheInBasket(login, pick);
            }
            menuMain.print();
        }
        else { menuMain.print(); }
    }

    void workWithBasket() {
        System.out.print("Введите свой логин: ");
        String login = scanner.nextLine();
        if (InternetShopSQL.findUser(login)) {
            menuMain.setCatalog("Корзина: ");
            menuMain.print();
            InternetShopSQL.findBasket(login);
            System.out.println("Преобрести товары? (да/нет) - <1/2>: ");
            int yesNo = scanner.nextInt();
            if (yesNo == 1) {
                InternetShopSQL.deleteBasket(login);
                System.out.println("Спасибо за покупку!");
            }
            else {
                System.out.println("Очистить корзину? (да/нет) - <1/2>: ");
                int yesNo2 = scanner.nextInt();
                if (yesNo2 == 1) {
                    InternetShopSQL.deleteBasket(login);
                    System.out.println("Корзина очищена!");
                }
            }
        }
    }
}

