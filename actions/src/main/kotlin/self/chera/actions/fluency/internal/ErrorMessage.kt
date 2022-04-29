package self.chera.actions.fluency.internal

import org.openqa.selenium.By

internal object ErrorMessage {
    fun notFound(by: By) = "Failed to find element [$by]"
}