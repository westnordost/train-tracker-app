package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.ui.graphics.Color
import cocoapods.MapLibre.MLNHillshadeStyleLayer
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.core.util.toNSExpression
import dev.sargunv.maplibrekmp.expression.Expression

@PublishedApi
internal actual class HillshadeLayer actual constructor(id: String, actual val source: Source) :
  Layer() {

  override val impl = MLNHillshadeStyleLayer(id, source.impl)

  actual fun setHillshadeIlluminationDirection(direction: Expression<Number>) {
    impl.hillshadeIlluminationDirection = direction.toNSExpression()
  }

  actual fun setHillshadeIlluminationAnchor(anchor: Expression<String>) {
    impl.hillshadeIlluminationAnchor = anchor.toNSExpression()
  }

  actual fun setHillshadeExaggeration(exaggeration: Expression<Number>) {
    impl.hillshadeExaggeration = exaggeration.toNSExpression()
  }

  actual fun setHillshadeShadowColor(shadowColor: Expression<Color>) {
    impl.hillshadeShadowColor = shadowColor.toNSExpression()
  }

  actual fun setHillshadeHighlightColor(highlightColor: Expression<Color>) {
    impl.hillshadeHighlightColor = highlightColor.toNSExpression()
  }

  actual fun setHillshadeAccentColor(accentColor: Expression<Color>) {
    impl.hillshadeAccentColor = accentColor.toNSExpression()
  }
}
