package self.chera.actions;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import self.chera.TestGlobal;

public class TestJavaActions {
    @Test
    void verifyTheAction() {
        var web = TestingOnWhat.testOnWeb();
        web.getDriver().get(TestGlobal.practiceSeleniumUrl);
        web.check(By.id("domain"))
                .whether(WebElement::getText)
                .isEqualTo("practiceselenium.com");
        web.getDriver().quit();
    }
}
