package self.chera.actions

import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import self.chera.actions.fluency.Value
import self.chera.actions.fluency.Wait

object Expressions {
    @JvmStatic
    fun <E : WebElement> check(by: By): Value<BaseAction<*, E>, E> =
        Wait<E>(by = by, timeout = 5).isVisibleAndThen()
}