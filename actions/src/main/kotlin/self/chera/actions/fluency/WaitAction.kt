package self.chera.actions.fluency

import arrow.core.Validated
import arrow.core.invalid
import arrow.core.valid
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

    private fun untilVisibleThenGet(): Validated<RuntimeException, ElementType> {
        return try {
            val wait = WebDriverWait(driver, Duration.ofSeconds(timeout))
            val element = wait.until(ExpectedConditions.visibilityOfElementLocated(fromBy))
            (element as ElementType).valid()
        } catch (ex: TimeoutException) {
            ex.invalid()
        }
    }

    /**
     * terminal operation
     */
    fun isVisible(): Boolean {
        return untilVisibleThenGet().isValid
    }

    /**
     * intermediate operation
     */
    fun isVisibleAndThen(): ElementAction<ElementType> {
        return ElementAction(fromBy) { this.untilVisibleThenGet() }
    }
}