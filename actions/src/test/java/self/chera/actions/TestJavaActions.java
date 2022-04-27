package self.chera.actions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import self.chera.TestGlobal;

public class TestJavaActions {
    final BaseAction<?, ?> web = TestingOnWhat.testOnWeb();

    @Test
    void thisMethodIsHaveKotlinCounterpart() {
        web.getDriver().get(TestGlobal.cheraSite);

        web.check(By.className("post-link"))
                .whether(WebElement::getText)
                .isEqualTo("Here is Chera!");
        web.check(By.className("post-link"))
                .whether(WebElement::getText)
                .isEqualTo("Some data");
    }

    @AfterEach
    void quitDriver() {
        web.getDriver().quit();
    }
}
