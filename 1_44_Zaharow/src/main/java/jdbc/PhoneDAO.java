package jdbc;

import com.company.Phone;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jdbc.ConnectionData.*;

public class PhoneDAO extends AbstractDAO {
    private static final String SQL_SELECT_ALL_USERS = "SELECT * FROM phones";
    private static final String SQL_SELECT_USER_ID = "SELECT * FROM phones WHERE user_id=?";
    private static final String SQL_DELETE_BY_USER_ID = "DELETE FROM phones WHERE user_id=?";
    private static final String SQL_DELETE_BY_PHONE_NUMBER = "DELETE FROM phones WHERE phone_number=?";
    private static final String SQL_INSERT_USER = "INSERT INTO phones (user_id, phone_number) VALUES (?, ?)";
    private static final String SQL_UPDATE_PHONE_NUMBER = "UPDATE phones SET phone_number = ? WHERE user_id = ?";

    @Override
    public List findAll() {
        List phones = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_SELECT_ALL_USERS);
            while (rs.next()) {
                int user_id = rs.getInt(2);
                String phone_number = rs.getString(3);
                phones.add(new Phone(user_id, phone_number));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return phones;
    }

    @Override
    public Phone findEntityById(Integer user_id) {
        Phone phone = null;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_USER_ID)) {
            statement.setInt(1, user_id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String phone_number = rs.getString(3);
                phone = new Phone(user_id, phone_number);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return phone;
    }

    @Override
    public boolean delete(Integer user_id) {
        Phone phone = null;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement =
                     connection.prepareStatement(SQL_DELETE_BY_USER_ID)) {
            statement.setInt(1, user_id);
            ResultSet rs = statement.executeQuery();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Phone entity) {
        String phone_number = entity.getPhone_number();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement =
                     connection.prepareStatement(SQL_DELETE_BY_PHONE_NUMBER)) {
            statement.setString(1, phone_number);
            ResultSet rs = statement.executeQuery();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean create(Phone entity) {
        try (Connection connection =
                     DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement =
                     connection.prepareStatement(SQL_INSERT_USER)) {
            preparedStatement.setInt(1, entity.getUser_id());
            preparedStatement.setString(2, entity.getPhone_number());
            preparedStatement.addBatch();
            preparedStatement.executeBatch();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Phone update(Phone entity) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement =
                     connection.prepareStatement(SQL_UPDATE_PHONE_NUMBER)) {
            statement.setString(1, entity.getPhone_number());
            statement.setInt(2, entity.getUser_id());
            ResultSet rs = statement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return entity;
    }
}
