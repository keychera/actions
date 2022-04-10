package self.chera.actions.fluency

import arrow.core.Validated
import arrow.core.andThen
import arrow.core.invalid
import arrow.core.valid
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

class ElementAction<ElementType : WebElement>(
    private val fromBy: By,
    private val getTheElement: (By) -> Validated<RuntimeException, ElementType>
) {
    /**
     * intermediate operation
     */
    fun <CheckType> whether(getTheValue: (ElementType?) -> CheckType?): CheckAction<CheckType> {
        return CheckAction(fromBy) {
            getTheElement(it).andThen { element ->
                try {
                    getTheValue(element)?.valid()
                        ?: NullPointerException("The value retrieved from [$fromBy] is null!").invalid()
                } catch (ex: RuntimeException) {
                    ex.invalid()
                }
            }
        }
    }
}