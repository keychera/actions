package self.chera.actions.fluency.multi

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import self.chera.actions.fluency.Wait


@Suppress("UNCHECKED_CAST")
class MultiWait<Driver : WebDriver, Target : WebElement>(
    fromBys: List<By>,
    private val contextDriver: Driver
) {
    private val waitActions: List<Wait<Driver, Target>> = fromBys
        .map { Wait(by = it) }

    /**
     * terminal operation
     */
    fun isVisible(): Boolean {
        return waitActions.map { it.isVisible() }.reduce { a, b -> a && b }
    }

    /**
     * intermediate operation
     */
    fun isVisibleAndThen(): MultiValue<Driver, Target> {
        val elementActions = waitActions.map { it.isVisibleAndThen() }
        return MultiValue(elementActions, contextDriver)
    }
}