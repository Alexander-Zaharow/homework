package jdbc;

import com.company.UserBasket;
import com.company.Clothe;
import com.company.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jdbc.ConnectionData.*;
import static jdbc.ConnectionData.PASSWORD;

public class InternetShopSQL {
    // CREATES
    public static final String CREATE_TABLE_CLOTHES_ON_SALE =
            "CREATE TABLE clothesOnSale (id serial, " +
                    "type VARCHAR(10)," +
                    "size VARCHAR(10)," +
                    "color VARCHAR(10)," +
                    "cost INT," +
                    "primary key(id));";
    public static final String CREATE_TABLE_USERS_INT_SHOP =
            "CREATE TABLE usersIntShop (user_id serial, " +
                    "login VARCHAR(10)," +
                    "password VARCHAR(10)," +
                    "CONSTRAINT login_unique UNIQUE (login)," +
                    "primary key(user_id));";
    public static final String CREATE_TABLE_BASKET =
            "CREATE TABLE basket (login VARCHAR(10), " +
                    "clothe_id INT, " +
                    "foreign key(login) references usersIntShop(login), " +
                    "foreign key(clothe_id) references clothesOnSale(id));";
    // INSERTS
    private static final String SQL_INSERT_CLOTHE_ON_SALE = "INSERT INTO clothesOnSale " +
            "(type, size, color, cost) VALUES (?, ?, ?, ?)";
    private static final String SQL_INSERT_BASKET = "INSERT INTO basket " +
            "(login, clothe_id) VALUES (?, ?)";
    private static final String SQL_INSERT_USER = "INSERT INTO usersIntShop " +
            "(login, password) VALUES (?, ?)";

    // SELECTS
    private static final String SQL_SELECT_ALL_CLOTHES_ON_SALE = "SELECT * FROM clothesOnSale";
    private static final String SQL_SELECT_ALL_BASKET = "SELECT * FROM basket";
    private static final String SQL_SELECT_ALL_USERS = "SELECT * FROM usersIntShop";

    private static final String SQL_SELECT_CLOTHES = "SELECT * FROM clothesOnSale WHERE type=?";
    private static final String SQL_SELECT_USER_BY_LOGIN = "SELECT * FROM usersIntShop WHERE login=?";
    private static final String SQL_SELECT_USER_BY_LOGIN_AND_PASS = "SELECT * FROM usersIntShop WHERE login=? AND password=?";
    private static final String SQL_SELECT_USER_BASKET = "SELECT * FROM basket WHERE login=?";
    private static final String SQL_SELECT_CLOTHES_IN_BASKET = "SELECT * FROM clothesOnSale WHERE id=?";

    // DELETE
    private static final String SQL_DELETE_BASKET_BY_LOGIN = "DELETE FROM basket WHERE login=?";

    public static boolean createTables() {
        try (Connection connection =
                     DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_TABLE_CLOTHES_ON_SALE);
            statement.executeUpdate(CREATE_TABLE_USERS_INT_SHOP);
            statement.executeUpdate(CREATE_TABLE_BASKET);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static List findClothes(String type) {
        List clothes = new ArrayList<>();
        int i = 0;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_CLOTHES)) {
            statement.setString(1, type);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String size = rs.getString(3);
                String color = rs.getString(4);
                int cost = rs.getInt(5);
                clothes.add(i, id + " - " + new Clothe(type, size, color, cost));
                System.out.println(clothes.get(i));
                i++;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return clothes;
    }

    public static List findAll(String table) { // clothesOnSale, usersIntShop or basket
        String SQL = "";
        switch (table) {
            case "clothesOnSale":
                SQL = SQL_SELECT_ALL_CLOTHES_ON_SALE;
                break;
            case "basket":
                SQL = SQL_SELECT_ALL_BASKET;
                break;
            case "usersIntShop":
                SQL = SQL_SELECT_ALL_USERS;
                break;
        }
        List lines = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL);
            while (rs.next()) {
                switch (SQL) {
                    case SQL_SELECT_ALL_CLOTHES_ON_SALE: {
                        String type = rs.getString(2);
                        String size = rs.getString(3);
                        String color = rs.getString(4);
                        int cost = rs.getInt(5);
                        lines.add(new Clothe(type, size, color, cost));
                        break;
                    }
                    case SQL_SELECT_ALL_BASKET: {
                        String login = rs.getString(1);
                        int clothe_id = rs.getInt(2);
                        lines.add(new UserBasket(login, clothe_id));
                        break;
                    }
                    case SQL_SELECT_ALL_USERS: {
                        String login = rs.getString(2);
                        String password = rs.getString(3);
                        lines.add(new User(login, password));
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lines;
    }

    public static boolean findUser (String login) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet rs = statement.executeQuery();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean findUser (String login, String password) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN_AND_PASS)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private static int costClothesInBasket(int id) {
        int clotheCost = 0;
        List clothes = new ArrayList<>();
        int i = 0;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_CLOTHES_IN_BASKET)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String type = rs.getString(2);
                String size = rs.getString(3);
                String color = rs.getString(4);
                int cost = rs.getInt(5);
                clothes.add(i, id + " - " + new Clothe(type, size, color, cost));
                System.out.println(clothes.get(i));
                i++;
                clotheCost += cost;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return clotheCost;
    }

    public static List findBasket (String login) {
        int totalCost = 0;
        List basket = new ArrayList<>();
        int i = 0;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_USER_BASKET)) {
            statement.setString(1, login);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int clothe_id = rs.getInt(2);
                totalCost += costClothesInBasket(clothe_id);
                basket.add(i, new UserBasket(login, clothe_id));
                i++;
            }
            System.out.println("Итого: " + totalCost + "р.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return basket;
    }

    public static boolean deleteBasket(String login) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement =
                     connection.prepareStatement(SQL_DELETE_BASKET_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet rs = statement.executeQuery();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean insertUser(User user) {
        try (Connection connection =
                     DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement =
                     connection.prepareStatement(SQL_INSERT_USER)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.addBatch();
            preparedStatement.executeBatch();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean insertClotheOnSale(Clothe clothe) {
        try (Connection connection =
                     DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement =
                     connection.prepareStatement(SQL_INSERT_CLOTHE_ON_SALE)) {
            preparedStatement.setString(1, clothe.getType());
            preparedStatement.setString(2, clothe.getSize());
            preparedStatement.setString(3, clothe.getColor());
            preparedStatement.setInt(4, clothe.getCost());
            preparedStatement.addBatch();
            preparedStatement.executeBatch();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean insertClotheInBasket(String login, int clothe_id) {
        try (Connection connection =
                     DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement =
                     connection.prepareStatement(SQL_INSERT_BASKET)) {
            preparedStatement.setString(1, login);
            preparedStatement.setInt(2, clothe_id);
            preparedStatement.addBatch();
            preparedStatement.executeBatch();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}