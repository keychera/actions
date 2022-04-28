package self.chera.actions.fluency

import arrow.core.Either
import arrow.core.getOrHandle
import org.assertj.core.api.SoftAssertions
import org.openqa.selenium.By
import org.openqa.selenium.TimeoutException

class AssertAction<TypeToAssert>(
    private val fromBy: By,
    private val isEager: Boolean,
    private val getValueToAssert: () -> Either<RuntimeException, TypeToAssert>,
) {

    fun isEqualTo(expected: TypeToAssert): (SoftAssertions) -> Unit {
        // type inference here is tricky so we strongly type the val and the map
        val assertProcess: (SoftAssertions) -> Unit = getValueToAssert().map<(SoftAssertions) -> Unit> { toAssert ->
            {
                it.assertThat(toAssert)
                    .withFailMessage { composeEqualityError(toAssert, expected) }
                    .isEqualTo(expected)
            }
        }.getOrHandle { exc ->
            { it.fail(composeExceptionError(exc)) }
        }
        if (isEager) {
            SoftAssertions.assertSoftly(assertProcess)
        }
        return assertProcess
    }

    private fun composeExceptionError(error: RuntimeException): String {
        val message = when (error) {
            is TimeoutException -> "[⌛] ${error.rawMessage}"
            else -> error.message
        }
        return "\n${ErrorMessage.notFound(fromBy)}\nerror ${message}\n"
    }

    private fun composeEqualityError(toAssert: TypeToAssert, expected: TypeToAssert): String {
        return "\nerror [≠] [$fromBy]\n  expecting: $expected\n  but was: $toAssert\n"
    }
}