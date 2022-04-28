package self.chera.actions.fluency

import org.openqa.selenium.WebElement

class MultiElementAction<ElementType : WebElement>(
    private val elementActions: List<ElementAction<ElementType>>
) {
    /**
     * intermediate operation
     */
    fun <TypeToAssert> whether(getTheValue: ElementType.() -> TypeToAssert?): MultiAssertAction<TypeToAssert> {
        return MultiAssertAction(elementActions.map { it.whether(getTheValue) })
    }
}