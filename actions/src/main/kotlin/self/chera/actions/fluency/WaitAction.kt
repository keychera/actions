package self.chera.actions.fluency

import org.openqa.selenium.By
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration


@Suppress("UNCHECKED_CAST")
class WaitAction<DriverType : WebDriver, ElementType : WebElement>(
    private val driver: DriverType,
    private val fromBy: By,
    private val timeout: Long = 5
) {

    private fun waitUntilVisibleThenGet(): ElementType? {
        return try {
            val wait = WebDriverWait(driver, Duration.ofSeconds(timeout))
            wait.until(ExpectedConditions.visibilityOfElementLocated(fromBy)) as ElementType?
        } catch (ex: TimeoutException) {
            null
        }
    }

    /**
     * terminal operation
     */
    fun isVisible(): Boolean {
        return waitUntilVisibleThenGet() != null
    }

    /**
     * intermediate operation
     */
    fun isVisibleAndThen(): ElementAction<ElementType> {
        return ElementAction(fromBy) { this.waitUntilVisibleThenGet() }
    }
}