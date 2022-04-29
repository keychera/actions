package self.chera.actions.fluency.multi

import self.chera.actions.fluency.Value

class MultiValue<Source : Any, Type : Any>(
    private val values: List<Value<Source, Type>>,
    private val contextSource: Source
) {
    /**
     * intermediate operation
     */
    fun <TypeToAssert : Any> whether(getTheValue: Type.() -> TypeToAssert?): MultiAssert<Source, TypeToAssert> {
        return MultiAssert(
            values.map { it.whether(getTheValue) }, contextSource
        )
    }
}