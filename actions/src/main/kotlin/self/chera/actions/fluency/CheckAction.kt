package self.chera.actions.fluency

import arrow.core.Validated
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.fail
import org.assertj.core.api.SoftAssertions
import org.openqa.selenium.By
import org.openqa.selenium.TimeoutException

class CheckAction<CheckType>(
    private val fromBy: By,
    private val getValueToCheck: (By) -> Validated<RuntimeException, CheckType>
) {
    private val notFoundMessage = "Failed to find element [$fromBy]"

    /**
     * terminal assertion operation
     */
    fun isEqualTo(expected: CheckType) {
        getValueToCheck(fromBy).bimap(
            { error -> run { fail<Any>(composeExceptionError(error)) } },
            { toCheck ->
                run {
                    assertThat(toCheck)
                        .withFailMessage { composeEqualityError(toCheck, expected) }
                        .isEqualTo(expected)
                }
            }
        )
    }

    /**
     * terminal assertion operation
     */
    fun isEqualTo(expected: CheckType, softly: SoftAssertions) {
        getValueToCheck(fromBy).bimap(
            { error -> run { softly.fail<Any>(composeExceptionError(error)) } }
        ) { toCheck ->
            run {
                softly.assertThat(toCheck)
                    .withFailMessage { composeEqualityError(toCheck, expected) }
                    .isEqualTo(expected)
            }
        }
    }

    private fun composeExceptionError(error: RuntimeException): String {
        val message = when (error) {
            is TimeoutException -> "[⌛] ${error.rawMessage}"
            else -> error.message
        }
        return "\n$notFoundMessage\nerror ${message}\n"
    }

    private fun composeEqualityError(toCheck: CheckType, expected: CheckType): String {
        return "\nerror [≠] [$fromBy]\n  expecting: $expected\n  but was: $toCheck\n"
    }
}