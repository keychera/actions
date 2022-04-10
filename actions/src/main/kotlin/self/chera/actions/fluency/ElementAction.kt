package self.chera.actions.fluency

import arrow.core.*
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import self.chera.actions.fluency.ErrorMessage.notFound

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

    fun get(): ElementType = getTheElement(fromBy).getOrElse { throw RuntimeException(notFound(fromBy)) }
}