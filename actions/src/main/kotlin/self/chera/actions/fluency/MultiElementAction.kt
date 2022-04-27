package self.chera.actions.fluency

import org.openqa.selenium.WebElement

class MultiElementAction<ElementType : WebElement>(
    private val elementActions: List<ElementAction<ElementType>>
) {
    /**
     * intermediate operation
     */
    fun <CheckType> whether(getTheValue: ElementType.() -> CheckType?): MultiCheckAction<CheckType> {
        return MultiCheckAction(elementActions.map { it.whether(getTheValue) })
    }
}