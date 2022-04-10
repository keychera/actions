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
        web.driver.get(TestGlobal.cheraSite)
    }

    @AfterEach
    fun cleanUp() = web.driver.quit()

    @Test
    fun `Verify that assertion happens on existing element`() {
        web.check(By.className("post-link"))
            .whether { it?.text }
            .isEqualTo("a Here is Chera!")
    }

    @Test
    fun `Verify that assertion happens on non-existing element`() {
        web.check(By.id("doesnt exist"))
            .whether { it?.text }
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
            By.id("doesnt exist"),
            By.className("post-meta")
        )
            .whether { it?.text }
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
}