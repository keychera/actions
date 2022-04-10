package self.chera.actions.fluency

import org.assertj.core.api.AbstractAssert
import org.assertj.core.api.Assertions.assertThat
import org.openqa.selenium.By

class CheckAction<CheckType>(
    private val fromBy: By,
    private val getValueToCheck: (By) -> CheckType?
) {
    /**
     * terminal operation
     */
    @JvmOverloads
    fun isEqualTo(
        expected: CheckType,
        getAssertAgent: (CheckType?) -> AbstractAssert<*, *> = { assertThat(it) }
    ) {
        val toCheck = getValueToCheck.invoke(fromBy)
        getAssertAgent(toCheck).isEqualTo(expected)
    }

}