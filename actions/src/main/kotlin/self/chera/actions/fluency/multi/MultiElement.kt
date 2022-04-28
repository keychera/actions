package self.chera.actions.fluency.multi

import org.openqa.selenium.WebElement
import self.chera.actions.fluency.Element

class MultiElement<Source : Any, Type : WebElement>(
    private val elementActions: List<Element<Source, Type>>,
    private val contextSource: Source
) {
    /**
     * intermediate operation
     */
    fun <TypeToAssert : Any> whether(getTheValue: Type.() -> TypeToAssert?): MultiAssert<Source, TypeToAssert> {
        return MultiAssert(
            elementActions.map { it.whether(getTheValue) }, contextSource
        )
    }
}