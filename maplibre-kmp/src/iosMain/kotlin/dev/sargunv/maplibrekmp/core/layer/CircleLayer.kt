package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.ui.graphics.Color
import cocoapods.MapLibre.MLNCircleStyleLayer
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.core.util.toNSExpression
import dev.sargunv.maplibrekmp.core.util.toPredicate
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.Point

@PublishedApi
internal actual class CircleLayer actual constructor(id: String, source: Source) :
  FeatureLayer(source) {

  override val impl = MLNCircleStyleLayer(id, source.impl)

  override var sourceLayer: String
    get() = impl.sourceLayerIdentifier!!
    set(value) {
      impl.sourceLayerIdentifier = value
    }

  override fun setFilter(filter: Expression<Boolean>) {
    impl.predicate = filter.toPredicate()
  }

  actual fun setCircleSortKey(sortKey: Expression<Number>) {
    impl.circleSortKey = sortKey.toNSExpression()
  }

  actual fun setCircleRadius(radius: Expression<Number>) {
    impl.circleRadius = radius.toNSExpression()
  }

  actual fun setCircleColor(color: Expression<Color>) {
    impl.circleColor = color.toNSExpression()
  }

  actual fun setCircleBlur(blur: Expression<Number>) {
    impl.circleBlur = blur.toNSExpression()
  }

  actual fun setCircleOpacity(opacity: Expression<Number>) {
    impl.circleOpacity = opacity.toNSExpression()
  }

  actual fun setCircleTranslate(translate: Expression<Point>) {
    impl.circleTranslation = translate.toNSExpression()
  }

  actual fun setCircleTranslateAnchor(translateAnchor: Expression<String>) {
    impl.circleTranslationAnchor = translateAnchor.toNSExpression()
  }

  actual fun setCirclePitchScale(pitchScale: Expression<String>) {
    impl.circleScaleAlignment = pitchScale.toNSExpression()
  }

  actual fun setCirclePitchAlignment(pitchAlignment: Expression<String>) {
    impl.circlePitchAlignment = pitchAlignment.toNSExpression()
  }

  actual fun setCircleStrokeWidth(strokeWidth: Expression<Number>) {
    impl.circleStrokeWidth = strokeWidth.toNSExpression()
  }

  actual fun setCircleStrokeColor(strokeColor: Expression<Color>) {
    impl.circleStrokeColor = strokeColor.toNSExpression()
  }

  actual fun setCircleStrokeOpacity(strokeOpacity: Expression<Number>) {
    impl.circleStrokeOpacity = strokeOpacity.toNSExpression()
  }
}
