package ru.netology.test;

import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.User;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationCodePage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class LoginTest {

    @Test
    void successEnterTest() throws SQLException {
        LoginPage loginPage = new LoginPage();
        User user = DataHelper.getValidUserFirst();
        VerificationCodePage pageVerification = loginPage.validLogin(user);
        String verificationCode = DataHelper.getLastVerificationCode(user);
        pageVerification.validVerify(verificationCode);
    }

    @Test
    void passwordErrorTest() throws SQLException {
        LoginPage loginPage = new LoginPage();
        User wrongUser = DataHelper.getInvalidPasswordUser();
        loginPage.invalidUser(wrongUser);
    }

    @Test
    void invalidVerificationCodeTest() throws SQLException {
        LoginPage loginPage = new LoginPage();
        User user = DataHelper.getValidUserFirst();
        VerificationCodePage pageVerification = loginPage.validLogin(user);
        String verificationCode = DataHelper.getInvalidVerificationCode();
        pageVerification.invalidVerify(verificationCode);
    }

    void doMaxEnter(User user) throws SQLException {
        LoginPage loginPage;
        String verificationCode;
        VerificationCodePage pageVerification;

        for (int i = 0; i < 3; i++) {
            loginPage = new LoginPage();
            pageVerification = loginPage.validLogin(user);
            verificationCode = DataHelper.getLastVerificationCode(user);
            pageVerification.validVerify(verificationCode);
        }
    }

    @Test
    void enterBlockTest() throws SQLException {
        User user = DataHelper.getValidUserSecond();
        doMaxEnter(user);

        LoginPage loginPage = new LoginPage();
        VerificationCodePage pageVerification = loginPage.validLogin(user);
        String verificationCode = DataHelper.getLastVerificationCode(user);
        pageVerification.isBlockedUser(verificationCode);
    }


}
