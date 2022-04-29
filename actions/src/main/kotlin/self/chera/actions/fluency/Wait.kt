package self.chera.actions.fluency

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import org.openqa.selenium.By
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import self.chera.actions.BaseAction
import self.chera.actions.fluency.internal.Context
import java.time.Duration

class Wait<Element : WebElement>(
    private val by: By,
    baseAction: BaseAction<*, Element>? = null,
    private val timeout: Long = 5,
) {
    private val context: Context<BaseAction<*, Element>?> = Context(baseAction, by)

    val waitAction: (BaseAction<*, Element>) -> Either<Throwable, Element> = { base ->
        try {
            val wait = WebDriverWait(base.driver, Duration.ofSeconds(timeout))
            val element = wait.until(ExpectedConditions.visibilityOfElementLocated(by))
            @Suppress("UNCHECKED_CAST")
            (element as Element).right()
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

    fun isVisibleFrom(): (BaseAction<*, Element>) -> Boolean = {
        waitAction(it).isRight()
    }
}