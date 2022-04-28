package self.chera.actions

import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import self.chera.TestGlobal

class TestKotlinActions {
    private lateinit var web: WebAction

    @BeforeEach
    fun createAction() {
        web = TestingOnWhat.testOnWeb()
        web.driver[TestGlobal.cheraSite]
    }

    @AfterEach
    fun cleanUp() = web.driver.quit()

    @Test
    fun thisMethodHasJavaCounterpart() {
        SoftAssertions.assertSoftly {
            it.assertThatCode {
                web.check(By.className("post-link"))
                    .whether { text }
                    .isEqualTo("Here is Chera!")
            }.doesNotThrowAnyException()

            it.assertThatThrownBy {
                web.check(By.className("post-link"))
                    .whether { text }
                    .isEqualTo("Some data")
            }.isInstanceOf(Throwable::class.java)
        }
    }

    @Test
    fun `Verify that assertion happens on existing element`() {
        web.check(By.className("post-link"))
            .whether { text }
            .isEqualTo("a Here is Chera!")
    }

    @Test
    fun `Verify that assertion happens on non-existing element`() {
        web.check(By.id("doesnt exist"))
            .whether { text }
            .isEqualTo("a Here is Chera!")
    }

    @Test
    fun `Verify wait action API`() {
        web.waitUntil(By.id("some-id"))
            .isVisible()
    }

    @Test
    fun `Verify multiple check API`() {
        web.check(
            By.className("post-link"),
            By.className("post-meta"),
            By.id("doesnt exist"),
            By.className("post-meta"),
        )
            .whether { text }
            .isEqualTo("Here is Chera!")
    }

    @Test
    fun `Verify multiple wait API`() {
        web.waitUntil(
            By.className("post-link"),
            By.id("doesnt exist"),
            By.className("post-meta")
        ).isVisible()
    }

    @Test
    fun `Verify WebElement interfaces is accessible`() {
        web.get(By.className("post-meta")).text.let { System.err.println(it) }
        web.click(By.className("post-link"))
        web.click(By.id("doesnt exist"))
        web.sendKeys(By.id("hmm"), "some text to type")
    }
}