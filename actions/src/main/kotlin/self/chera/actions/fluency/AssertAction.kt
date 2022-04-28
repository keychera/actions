package self.chera.actions.fluency

import arrow.core.Validated
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.fail
import org.assertj.core.api.SoftAssertions
import org.openqa.selenium.By
import org.openqa.selenium.TimeoutException

class AssertAction<TypeToAssert>(
    private val fromBy: By,
    private val getValueToAssert: (By) -> Validated<RuntimeException, TypeToAssert>
) {

    /**
     * terminal assertion operation
     */
    fun isEqualTo(expected: TypeToAssert) {
        getValueToAssert(fromBy).bimap(
            { error -> run { fail<Any>(composeExceptionError(error)) } },
            { toAssert ->
                run {
                    assertThat(toAssert)
                        .withFailMessage { composeEqualityError(toAssert, expected) }
                        .isEqualTo(expected)
                }
            }
        )
    }

    /**
     * terminal assertion operation
     */
    fun isEqualTo(expected: TypeToAssert, softly: SoftAssertions) {
        getValueToAssert(fromBy).bimap(
            { error -> run { softly.fail<Any>(composeExceptionError(error)) } }
        ) { toAssert ->
            run {
                softly.assertThat(toAssert)
                    .withFailMessage { composeEqualityError(toAssert, expected) }
                    .isEqualTo(expected)
            }
        }
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