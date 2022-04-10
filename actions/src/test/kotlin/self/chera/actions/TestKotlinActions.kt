package self.chera.actions

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
        web.driver.get(TestGlobal.practiceSeleniumUrl)
    }

    @AfterEach
    fun cleanUp() = web.driver.quit()

    @Test
    fun `Verify that assertion happens on existing element`() {
        web.check(By.id("domain"))
            .whether { it?.text }
            .isEqualTo("a practiceselenium.com")
    }

    @Test
    fun `Verify wait action API`() {
        web.waitUntil(By.id("some-id"))
            .isVisible()
    }

    @Test
    fun `Verify multiple check API`() {
        web.check(
            By.id("domain"),
            By.id("doesnt exist"),
            By.id("domainInfo")
        )
            .whether { it?.text }
            .isEqualTo("a practiceselenium.com")
    }

    @Test
    fun `Verify multiple wait API`() {
        web.waitUntil(
            By.id("domain"),
            By.id("doesnt exist"),
            By.id("domainInfo")
        ).isVisible()
    }
}