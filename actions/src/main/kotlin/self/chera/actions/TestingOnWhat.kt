package self.chera.actions

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

typealias WebAction = BaseAction<WebDriver, WebElement>

object TestingOnWhat {
    init {
        WebDriverManager.chromedriver().setup()
    }

    @JvmStatic
    fun testOnWeb(): WebAction {
        val options = ChromeOptions()
        options.addArguments("--incognito")
        val driver = ChromeDriver(options)
        return BaseAction(driver)
    }
}

