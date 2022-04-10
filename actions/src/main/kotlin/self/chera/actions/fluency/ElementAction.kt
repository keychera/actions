package self.chera.actions.fluency

import arrow.core.andThen
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

class ElementAction<ElementType : WebElement>(
    val fromBy: By,
    val getTheElement: (By) -> ElementType?
) {
    /**
     * intermediate operation
     */
    fun <CheckType> whether(getTheValue: (ElementType?) -> CheckType?): CheckAction<CheckType> {
        return CheckAction(fromBy, getTheElement.andThen(getTheValue))
    }
}