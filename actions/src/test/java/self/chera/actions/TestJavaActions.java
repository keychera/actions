package self.chera.actions;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import self.chera.TestGlobal;

public class TestJavaActions {
    @Test
    void verifyTheAction() {
        var web = TestingOnWhat.testOnWeb();
        web.getDriver().get(TestGlobal.cheraSite);
        web.check(By.id("post-link"))
                .whether(WebElement::getText)
                .isEqualTo("Here is Chera!");
        web.getDriver().quit();
    }
}
