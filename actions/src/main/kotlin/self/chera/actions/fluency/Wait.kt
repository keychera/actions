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
import self.chera.actions.fluency.internal.Context
import java.time.Duration

class Wait<Driver : WebDriver, Target : WebElement>(
    private val by: By,
    driver: Driver? = null,
    private val timeout: Long = 5,
) {
    private val context: Context<Driver?> = Context(driver, by)

    val waitAction: (Driver) -> Either<Throwable, Target> = { driver ->
        try {
            val wait = WebDriverWait(driver, Duration.ofSeconds(timeout))
            val element = wait.until(ExpectedConditions.visibilityOfElementLocated(by))
            @Suppress("UNCHECKED_CAST")
            (element as Target).right()
        } catch (ex: TimeoutException) {
            ex.left()
        }
    }

    fun isVisibleAndThen() = Value(waitAction, context)

    fun isVisible(): Boolean {
        return (context.source ?: throw IllegalAccessException("No context driver supplied!"))
            .let { fromDriver ->
                waitAction(fromDriver).isRight()
            }
    }
}