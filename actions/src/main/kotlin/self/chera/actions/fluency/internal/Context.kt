package self.chera.actions.fluency.internal

import org.openqa.selenium.By

data class Context<Source>(
    val source: Source,
    val by: By,
)