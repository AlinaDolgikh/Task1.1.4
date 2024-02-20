package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connect = Util.connect();
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Statement statement = connect.createStatement()) {
            statement.execute("""
                        CREATE TABLE IF NOT EXISTS Users (
                       Id SERIAL PRIMARY KEY,
                       Name VARCHAR(255),
                       LastName VARCHAR(255),
                       Age SMALLINT
                     );
                    """);
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connect.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS Users");
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO Users (Name, LastName, Age ) VALUES (?,?,?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connect.prepareStatement("DELETE FROM Users WHERE id=?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("User с id- " + id + " удален из базы данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = connect.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT  * FROM Users");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("Id"));
                user.setName(resultSet.getString("Name"));
                user.setLastName(resultSet.getString("LastName"));
                user.setAge(resultSet.getByte("Age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = connect.createStatement()) {
            statement.execute("TRUNCATE Users");
            System.out.println("Данные таблицы удалены");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
