package self.chera.actions.fluency.multi

import org.assertj.core.api.SoftAssertions
import self.chera.actions.fluency.Assert

class MultiAssert<Source : Any, TypeToAssert : Any>(
    private val assertActions: List<Assert<Source, TypeToAssert>>,
    private val contextSource: Source
) {
    /**
     * terminal operation
     */
    fun isEqualTo(expected: TypeToAssert) {
        SoftAssertions.assertSoftly { softly ->
            assertActions.forEach { it.isEqualTo(expected)(contextSource)(softly) }
        }
    }

    fun isNotEqualTo(expected: TypeToAssert) {
        SoftAssertions.assertSoftly { softly ->
            assertActions.forEach { it.isNotEqualTo(expected)(contextSource)(softly) }
        }
    }

    fun isNotNull() {
        SoftAssertions.assertSoftly { softly ->
            assertActions.forEach { it.isNotNull()(contextSource)(softly) }
        }
    }

    fun isNull() {
        SoftAssertions.assertSoftly { softly ->
            assertActions.forEach { it.isNull()(contextSource)(softly) }
        }
    }
}