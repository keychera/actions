package self.chera.actions

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import self.chera.actions.fluency.Element
import self.chera.actions.fluency.Wait
import self.chera.actions.fluency.multi.MultiElement
import self.chera.actions.fluency.multi.MultiWait

open class BaseAction<DriverType : WebDriver, ElementType : WebElement>(
    val driver: DriverType
) {
    fun check(by: By): Element<DriverType, ElementType> {
        return waitUntil(by).isVisibleAndThen()
    }

    fun check(vararg bys: By): MultiElement<DriverType, ElementType> {
        return waitUntil(*bys).isVisibleAndThen()
    }

    fun waitUntil(by: By): Wait<DriverType, ElementType> {
        return Wait(by = by, timeout = 5, driver = driver)
    }

    fun waitUntil(vararg bys: By): MultiWait<DriverType, ElementType> {
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