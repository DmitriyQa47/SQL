package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;


public class VerificationCodePage {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id='action-verify']");
    private SelenideElement errorPanel = $("[data-test-id='error-notification']");

    public VerificationCodePage() {
        codeField.shouldBe(visible);
    }

    public void validVerify(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
        new DashboardPage();
    }

    public void invalidVerify(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
        verifyButton.shouldBe(visible);
        errorPanel.shouldBe(visible).shouldHave(text("Ошибка")).shouldHave(text("Неверно указан код!"));
    }

    public void isBlockedUser(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
        errorPanel.shouldBe(visible).shouldHave(text("Ошибка")).shouldHave(text("Превышено количество попыток ввода кода"));
    }
}
