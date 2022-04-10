package self.chera.actions.fluency

import org.assertj.core.api.SoftAssertions

class MultiCheckAction<CheckType>(
    private val checkActions: List<CheckAction<CheckType>>
) {
    /**
     * terminal operation
     */
    fun isEqualTo(expected: CheckType) {
        SoftAssertions.assertSoftly { softly ->
            checkActions.forEach { it.isEqualTo(expected, softly) }
        }
    }
}