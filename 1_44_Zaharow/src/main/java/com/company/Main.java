package com.company;

import jdbc.*;

import java.sql.*;

import static jdbc.ConnectionData.URL;
import static jdbc.ConnectionData.USER;
import static jdbc.ConnectionData.PASSWORD;

public class Main {
    public static void main(String[] args) {
        new Main().task5();
        //new Main().task1(); new Main().task2(); new Main().task3(); new Main().task4();
    }

    void task1() { // Creating table
        try (Connection connection =
                     DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(CreatingTable.CREATE_TABLE_QUERY);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void task2() { // Retrieve data prepared statement
        try (Connection connection =
                     DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement =
                     connection.prepareStatement(RetrieveDataPreparedStatement.SELECT_QUERY)) {
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.printf("%s%23s%23s%n", "id", "user_id", "phone_number");
            while (resultSet.next()) {
                System.out.printf("%d%23s%23s%n", resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("phone_number"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void task3() { // Insert data prepared statement
        try (Connection connection =
                     DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement =
                     connection.prepareStatement(InsertDataPreparedStatement.INSERT_QUERY)) {
            preparedStatement.setInt(1, 5);
            preparedStatement.setString(2, "8800555332");
            preparedStatement.addBatch();
            preparedStatement.setInt(1, 6);
            preparedStatement.setString(2, "8800800800");
            preparedStatement.addBatch();
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void task4() {
        PhoneDAO phoneDAO = new PhoneDAO();
        System.out.println("findAll: " + phoneDAO.findAll());
        System.out.println("findEntityById: " + phoneDAO.findEntityById(1));

        phoneDAO.delete(5);
        System.out.println("afterDeleteById: " + phoneDAO.findAll());

        phoneDAO.delete(new Phone("8800800800"));
        System.out.println("afterDeleteByPhone: " + phoneDAO.findAll());

        phoneDAO.create(new Phone(7, "8999999999"));
        System.out.println("afterCreated: " + phoneDAO.findAll());

        phoneDAO.update(new Phone(7, "8800700600"));
        System.out.println("afterUpdated: " + phoneDAO.findAll());
    }

    void task5() {
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

        AuthUser user = new AuthUser();

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
}




