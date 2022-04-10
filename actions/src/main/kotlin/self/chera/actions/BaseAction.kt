package self.chera.actions

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import self.chera.actions.fluency.ElementAction
import self.chera.actions.fluency.WaitAction

open class BaseAction<DriverType : WebDriver, ElementType : WebElement>(
    val driver: DriverType
) {
    fun check(by: By): ElementAction<ElementType> {
        return waitUtil(by).isVisibleAndThen()
    }

    fun waitUtil(by: By): WaitAction<DriverType, ElementType> {
        return WaitAction(driver, by)
    }
}