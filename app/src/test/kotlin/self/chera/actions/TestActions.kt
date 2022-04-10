package self.chera.actions

import org.junit.jupiter.api.Test
import org.openqa.selenium.By

class TestActions {
    @Test
    fun `Verify that actions are here`() {
        val web = TestingOnWhat.testOnWeb()
        web.driver.get("https://google.com")

        web.check(By.className("input"))
            .whether { it?.text }
            .isEqualTo("Google Search")

        web.driver.quit()
    }
}