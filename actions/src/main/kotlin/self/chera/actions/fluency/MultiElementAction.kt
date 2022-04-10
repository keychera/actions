package self.chera.actions.fluency

import arrow.core.andThen
import org.openqa.selenium.WebElement

class MultiElementAction<ElementType : WebElement>(
    private val elementActions: List<ElementAction<ElementType>>
) {
    /**
     * intermediate operation
     */
    fun <CheckType> whether(getTheValue: (ElementType?) -> CheckType?): MultiCheckAction<CheckType> {
        return MultiCheckAction(elementActions.map {
            CheckAction(it.fromBy, it.getTheElement.andThen(getTheValue))
        })
    }
}