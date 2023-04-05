package ru.netology.data;

import com.github.javafaker.Faker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

public class DataHelper {


    private static QueryRunner runner = new QueryRunner();
    private static Faker faker = new Faker();
    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://192.168.99.101:3306/app", "app", "pass");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static User getValidUserFirst() throws SQLException {
        String usersSQL = "select * from users where login = 'vasya';";
        User user = runner.query(connection, usersSQL, new BeanHandler<>(User.class));
        user.setPassword("qwerty123");
        return user;
    }

    public static User getValidUserSecond() throws SQLException {
        String usersSQL = "select * from users where login = 'petya';";
        User user = runner.query(connection, usersSQL, new BeanHandler<>(User.class));
        user.setPassword("123qwerty");
        return user;
    }

    public static User getInvalidPasswordUser() throws SQLException {
        User user = getValidUserFirst();
        user.setPassword(faker.lorem().characters(9));
        return user;
    }

    public static String getLastVerificationCode(User user) throws SQLException {
        String codeSQL = "select code from auth_codes " +
                "where user_id = ? and created = (" +
                "select max(created) from auth_codes " +
                "where user_id = ?)";
        String code = runner.query(connection, codeSQL, new ScalarHandler<>(), user.getId(), user.getId());
        return code;
    }

    public static String getInvalidVerificationCode() {
        return faker.number().digits(6);
    }

}
