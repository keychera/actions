package self.chera.actions.fluency

import org.assertj.core.api.Assertions.assertThat
import org.openqa.selenium.By

class CheckAction<CheckType>(
    private val fromBy: By,
    private val getValueToCheck: (By) -> CheckType?
) {
    /**
     * terminal operation
     */
    fun isEqualTo(toCheck: CheckType) {
        assertThat(getValueToCheck.invoke(fromBy)).isEqualTo(toCheck)
    }
}