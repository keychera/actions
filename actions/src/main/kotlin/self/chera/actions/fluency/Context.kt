package self.chera.actions.fluency

import org.openqa.selenium.By

data class Context<Source>(
    val source: Source,
    val by: By,
)