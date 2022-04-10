package self.chera.actions.fluency

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement


@Suppress("UNCHECKED_CAST")
class MultiWaitAction<DriverType : WebDriver, ElementType : WebElement>(
    driver: DriverType, fromBys: List<By>
) {
    private val waitActions: List<WaitAction<DriverType, ElementType>> = fromBys
        .map { WaitAction(driver, it) }

    /**
     * terminal operation
     */
    fun isVisible(): Boolean {
        return waitActions.map { it.isVisible() }.reduce { a, b -> a && b }
    }

    /**
     * intermediate operation
     */
    fun isVisibleAndThen(): MultiElementAction<ElementType> {
        val elementActions = waitActions.map { wait ->
            ElementAction(wait.fromBy) { wait.untilVisibleThenGet() }
        }
        return MultiElementAction(elementActions)
    }
}