package self.chera.actions.fluency

import org.openqa.selenium.By

internal object ErrorMessage {
    fun notFound(by: By) = "Failed to find element [$by]"
}