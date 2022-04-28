package self.chera.actions

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import self.chera.actions.fluency.Value
import self.chera.actions.fluency.Wait
import self.chera.actions.fluency.multi.MultiValue
import self.chera.actions.fluency.multi.MultiWait

open class BaseAction<Driver : WebDriver, Element : WebElement>(
    val driver: Driver
) {
    fun check(by: By): Value<Driver, Element> {
        return waitUntil(by).isVisibleAndThen()
    }

    fun check(vararg bys: By): MultiValue<Driver, Element> {
        return waitUntil(*bys).isVisibleAndThen()
    }

    fun waitUntil(by: By): Wait<Driver, Element> {
        return Wait(by = by, timeout = 5, driver = driver)
    }

    fun waitUntil(vararg bys: By): MultiWait<Driver, Element> {
        return MultiWait(bys.toList(), driver)
    }

    fun get(by: By) = waitUntil(by).isVisibleAndThen().get()

    fun click(by: By) {
        waitUntil(by).isVisibleAndThen().get().click()
    }

    fun sendKeys(by: By, value: String) {
        waitUntil(by).isVisibleAndThen().get().sendKeys(value)
    }


}