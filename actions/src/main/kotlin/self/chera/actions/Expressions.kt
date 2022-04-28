package self.chera.actions

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import self.chera.actions.fluency.Value
import self.chera.actions.fluency.Wait

object Expressions {
    @JvmStatic
    fun <Driver : WebDriver, Element : WebElement> check(by: By): Value<Driver, Element> =
        Wait<Driver, Element>(by = by, timeout = 5).isVisibleAndThen()
}