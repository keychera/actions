package self.chera.actions.fluency

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.fail
import org.assertj.core.api.SoftAssertions
import org.openqa.selenium.By

class CheckAction<CheckType>(
    private val fromBy: By,
    private val getValueToCheck: (By) -> CheckType?
) {
    private val notFoundMessage = "Failed to find element [$fromBy]"

    /**
     * terminal assertion operation
     */
    fun isEqualTo(expected: CheckType) {
        val toCheck = getValueToCheck.invoke(fromBy)
        if (toCheck != null) {
            assertThat(toCheck).isEqualTo(expected)
        } else {
            fail(notFoundMessage)
        }
    }

    /**
     * terminal assertion operation
     */
    fun isEqualTo(expected: CheckType, softly: SoftAssertions) {
        val toCheck = getValueToCheck.invoke(fromBy)
        if (toCheck != null) {
            softly.assertThat(toCheck).isEqualTo(expected)
        } else {
            softly.fail(notFoundMessage)
        }
    }
}