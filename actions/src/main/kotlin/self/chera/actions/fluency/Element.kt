package self.chera.actions.fluency

import arrow.core.*

class Element<Source : Any, Type : Any>(
    private val element: (Source) -> Either<Throwable, Type>,
    private val context: Context<Source?>
) {
    fun <TypeToAssert : Any> whether(
        getTheValue: Type.() -> TypeToAssert?
    ) = Assert(element.andThen {
        it.flatMap { current ->
            getTheValue(current)?.right()
                ?: RuntimeException("Null value received on `Whether` step").left()
        }
    }, context)

    fun get(): Type {
        return (context.source ?: throw IllegalAccessException("No source supplied!"))
            .let { fromSource ->
                element(fromSource).getOrHandle { throw it }
            }
    }
}