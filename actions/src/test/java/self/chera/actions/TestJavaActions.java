package self.chera.actions;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import self.chera.TestGlobal;

import static self.chera.actions.Expressions.check;

public class TestJavaActions {
    BaseAction<?, ?> web;

    @BeforeEach
    void goToSite() {
        web = TestingOnWhat.testOnWeb();
        web.getDriver().get(TestGlobal.cheraSite);
    }

    @AfterEach
    void quitDriver() {
        web.getDriver().quit();
    }

    @Test
    void thisMethodHasKotlinCounterpart() {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThatCode(() ->
                    web.check(By.className("post-link"))
                            .whether(WebElement::getText)
                            .isEqualTo("Here is Chera!")
            ).doesNotThrowAnyException();

            softly.assertThatCode(() ->
                    web.check(By.className("post-link"))
                            .whether(WebElement::getText)
                            .isEqualTo("Some data")
            ).isInstanceOf(Throwable.class);

        });
        web.doThese(
                check(By.className("post-link")).whether(WebElement::getText).isEqualTo("DO THESE"),
                check(By.id("doesn't exist")).whether(WebElement::getText).isEqualTo("DO THESE"),
                check(By.className("post-meta")).whether(WebElement::getText).isEqualTo("DO THESE")
        );

    }
}
