package self.chera.actions.fluency

import org.assertj.core.api.SoftAssertions

class MultiAssertAction<TypeToAssert>(
    private val assertActions: List<AssertAction<TypeToAssert>>
) {
    /**
     * terminal operation
     */
    fun isEqualTo(expected: TypeToAssert) {
        SoftAssertions.assertSoftly { softly ->
            assertActions.forEach { it.isEqualTo(expected, softly) }
        }
    }
}