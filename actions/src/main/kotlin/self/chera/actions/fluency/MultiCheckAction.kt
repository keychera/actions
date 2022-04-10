package self.chera.actions.fluency

import org.assertj.core.api.SoftAssertions

class MultiCheckAction<CheckType>(
    private val checkActions: List<CheckAction<CheckType>>
) {
    /**
     * terminal operation
     */
    fun isEqualTo(expected: CheckType) {
        val softly = SoftAssertions()
        checkActions.forEach { it.isEqualTo(expected, softly) }
        softly.assertAll()
    }
}