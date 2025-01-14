package dev.sargunv.maplibrekmp.compose.engine

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.staticCompositionLocalOf
import co.touchlab.kermit.Logger
import dev.sargunv.maplibrekmp.core.Style
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.ExpressionScope
import kotlinx.coroutines.awaitCancellation

@Composable
internal fun rememberStyleComposition(
  maybeStyle: Style?,
  logger: Logger?,
  content: @Composable ExpressionScope.() -> Unit,
): State<StyleNode?> {
  val ret = remember { mutableStateOf<StyleNode?>(null) }
  val compositionContext = rememberCompositionContext()

  LaunchedEffect(maybeStyle, compositionContext) {
    val rootNode =
      StyleNode(maybeStyle?.let { StyleManager(it, logger) } ?: return@LaunchedEffect).also {
        ret.value = it
      }
    val composition = Composition(MapNodeApplier(rootNode), compositionContext)

    composition.setContent {
      CompositionLocalProvider(LocalStyleManager provides rootNode.styleManager) {
        content(Expression)
      }
    }

    try {
      awaitCancellation()
    } finally {
      ret.value = null
      rootNode.styleManager.style = Style.Null
      composition.dispose()
    }
  }

  return ret
}

internal val LocalStyleManager =
  staticCompositionLocalOf<StyleManager> { throw IllegalStateException() }
