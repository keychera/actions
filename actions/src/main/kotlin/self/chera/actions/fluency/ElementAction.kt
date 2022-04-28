package self.chera.actions.fluency

import arrow.core.*
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import self.chera.actions.fluency.ErrorMessage.notFound

class ElementAction<ElementType : WebElement>(
    private val fromBy: By,
    private val isEager: Boolean,
    private val getTheElement: () -> Either<RuntimeException, ElementType>,
) {

    /**
     * intermediate operation
     */
    fun <TypeToAssert> whether(getTheValue: ElementType.() -> TypeToAssert?): AssertAction<TypeToAssert> {
        return AssertAction(fromBy, isEager,
            getTheElement.andThen {
                it.flatMap { element ->
                    try {
                        getTheValue(element)?.right()
                            ?: NullPointerException("The value retrieved from [$fromBy] is null!").left()
                    } catch (ex: RuntimeException) {
                        ex.left()
                    }
                }
            }
        )
    }

    fun get(): ElementType = getTheElement().getOrElse { throw RuntimeException(notFound(fromBy)) }
}