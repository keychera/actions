package self.chera.actions.fluency

import arrow.core.Either
import arrow.core.left
import arrow.core.right
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
    private val timeout: Long = 5,
    private val isEager: Boolean = false
) {

    private fun untilVisibleThenGet(): () -> Either<RuntimeException, ElementType> {
        return {
            try {
                val wait = WebDriverWait(driver, Duration.ofSeconds(timeout))
                val element = wait.until(ExpectedConditions.visibilityOfElementLocated(fromBy))
                (element as ElementType).right()
            } catch (ex: TimeoutException) {
                ex.left()
            }
        }
    }

    /**
     * terminal operation
     */
    fun isVisible(): Boolean {
        return untilVisibleThenGet()().isRight()
    }

    /**
     * intermediate operation
     */
    fun isVisibleAndThen(): ElementAction<ElementType> {
        return ElementAction(fromBy, isEager, untilVisibleThenGet())
    }
}