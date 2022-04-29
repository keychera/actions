package self.chera.actions

import org.assertj.core.api.SoftAssertions
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
    fun check(by: By): Value<BaseAction<*, Element>, Element> {
        return waitUntil(by).isVisibleAndThen()
    }

    fun check(vararg bys: By): MultiValue<BaseAction<*, Element>, Element> {
        return waitUntil(*bys).isVisibleAndThen()
    }

    fun waitUntil(by: By): Wait<Element> {
        return Wait(by = by, timeout = 5, baseAction = this)
    }

    fun waitUntil(vararg bys: By): MultiWait<Element> {
        return MultiWait(bys.toList(), this)
    }

    fun get(by: By) = waitUntil(by).isVisibleAndThen().get()

    fun click(by: By) {
        waitUntil(by).isVisibleAndThen().get().click()
    }

    fun sendKeys(by: By, value: String) {
        waitUntil(by).isVisibleAndThen().get().sendKeys(value)
    }

    @SafeVarargs
    fun <E : WebElement> doThese(
        vararg assertAction: (BaseAction<*, E>) -> SoftAssertions.() -> Unit
    ) {
        SoftAssertions.assertSoftly { softly ->
            assertAction.forEach { doAssert ->
                @Suppress("UNCHECKED_CAST")
                doAssert(this as BaseAction<*, E>)(softly)
            }
        }
    }
}