package self.chera.actions.fluency

import arrow.core.*
import self.chera.actions.fluency.internal.Context

class Value<Source : Any, Type : Any>(
    private val value: (Source) -> Either<Throwable, Type>,
    private val context: Context<Source?>
) {
    fun <TypeToAssert : Any> whether(
        getTheValue: Type.() -> TypeToAssert?
    ) = Assert(value.andThen {
        it.flatMap { current ->
            getTheValue(current)?.right()
                ?: RuntimeException("Null value received on `Whether` step").left()
        }
    }, context)

    fun get(): Type {
        return (context.source ?: throw IllegalAccessException("No source supplied!"))
            .let { fromSource ->
                value(fromSource).getOrHandle { throw it }
            }
    }
}