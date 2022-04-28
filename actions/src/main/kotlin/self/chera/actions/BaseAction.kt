package self.chera.actions

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import self.chera.actions.fluency.ElementAction
import self.chera.actions.fluency.MultiElementAction
import self.chera.actions.fluency.MultiWaitAction
import self.chera.actions.fluency.WaitAction

open class BaseAction<DriverType : WebDriver, ElementType : WebElement>(
    val driver: DriverType
) {
    fun check(by: By): ElementAction<ElementType> {
        return waitUntil(by).isVisibleAndThen()
    }

    fun check(vararg bys: By): MultiElementAction<ElementType> {
        return waitUntil(*bys).isVisibleAndThen()
    }

    fun waitUntil(by: By): WaitAction<DriverType, ElementType> {
        return WaitAction(driver = driver, fromBy = by, timeout = 5, isEager = true)
    }

    fun waitUntil(vararg bys: By): MultiWaitAction<DriverType, ElementType> {
        return MultiWaitAction(driver, bys.toList())
    }

    fun get(by: By) = waitUntil(by).isVisibleAndThen().get()

    fun click(by: By) {
        waitUntil(by).isVisibleAndThen().get().click()
    }

    fun sendKeys(by: By, value: String) {
        waitUntil(by).isVisibleAndThen().get().sendKeys(value)
    }


}