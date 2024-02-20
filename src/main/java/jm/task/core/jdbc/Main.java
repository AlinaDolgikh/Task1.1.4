package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Иван", "Иванов", (byte) 25);
        userService.saveUser("Катя", "Рожкова", (byte) 30);
        userService.saveUser("Петр", "Петров", (byte) 28);
        userService.saveUser("Юля", "Мельникова", (byte) 20);

        List<User> allUsers = userService.getAllUsers();
        for (User user : allUsers) {
            System.out.println(user.toString());
        }
        userService.removeUserById(3);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}