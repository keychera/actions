package self.chera.actions.fluency

import arrow.core.Either
import org.assertj.core.api.SoftAssertions
import org.openqa.selenium.TimeoutException
import self.chera.actions.fluency.internal.Context
import self.chera.actions.fluency.internal.ErrorMessage

class Assert<Source : Any, TypeToAssert : Any>(
    private val toAssert: (Source) -> Either<Throwable, TypeToAssert>,
    private val context: Context<Source?>
) {
    fun isEqualTo(expected: TypeToAssert): (Source) -> SoftAssertions.() -> TypeToAssert? {
        val assertAction: (Source) -> SoftAssertions.() -> TypeToAssert? = { source ->
            toAssert(source).fold(
                { error ->
                    {
                        fail<Any>(composeExceptionError(error))
                        null
                    }
                },
                { actual ->
                    {
                        assertThat(actual)
                            .withFailMessage(composeEqualityError(actual, expected))
                            .isEqualTo(expected)
                        actual
                    }
                }
            )
        }
        // source/driver is null for MultiWait or doThese
        if (context.source != null) {
            SoftAssertions.assertSoftly {
                assertAction(context.source)(it)
            }
        }
        return assertAction
    }

    private fun composeExceptionError(error: Throwable): String {
        val message = when (error) {
            is TimeoutException -> "[⌛] ${ErrorMessage.notFound(context.by)}"
            else -> error.message
        }
        return "\nerror ${message}\n"
    }

    private fun composeEqualityError(actual: TypeToAssert, expected: TypeToAssert): String {
        return "\nerror [≠] [${context.by}]\n  expecting: $expected\n  but was: $actual\n"
    }
}