package self.chera.actions.fluency.multi

import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import self.chera.actions.BaseAction
import self.chera.actions.fluency.Wait


@Suppress("UNCHECKED_CAST")
class MultiWait<Element : WebElement>(
    fromBys: List<By>,
    private val contextAction: BaseAction<*, Element>
) {
    private val waitActions: List<Wait<Element>> = fromBys
        .map { Wait(by = it) }

    /**
     * terminal operation
     */
    fun isVisible(): Boolean {
        return waitActions.map { it.isVisibleFrom()(contextAction) }.reduce { a, b -> a && b }
    }

    /**
     * intermediate operation
     */
    fun isVisibleAndThen(): MultiValue<BaseAction<*, Element>, Element> {
        val elementActions = waitActions.map { it.isVisibleAndThen() }
        return MultiValue(elementActions, contextAction)
    }
}