package dev.sargunv.maplibrekmp.compose.layer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key as composeKey
import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.layer.FillLayer
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.Expression.Companion.const
import dev.sargunv.maplibrekmp.expression.Expression.Companion.nil
import dev.sargunv.maplibrekmp.expression.Expression.Companion.point
import dev.sargunv.maplibrekmp.expression.Point
import dev.sargunv.maplibrekmp.expression.TResolvedImage
import io.github.dellisd.spatialk.geojson.Feature

@Composable
@Suppress("NOTHING_TO_INLINE")
public inline fun FillLayer(
  id: String,
  source: Source,
  sourceLayer: String = "",
  minZoom: Float = 0.0f,
  maxZoom: Float = 24.0f,
  filter: Expression<Boolean> = nil(),
  visible: Boolean = true,
  sortKey: Expression<Number> = nil(),
  antialias: Expression<Boolean> = const(true),
  opacity: Expression<Number> = const(1.0),
  color: Expression<Color> = const(Color.Black),
  outlineColor: Expression<Color> = color,
  translate: Expression<Point> = point(0.0, 0.0),
  translateAnchor: Expression<String> = const("map"),
  pattern: Expression<TResolvedImage> = nil(),
  noinline onClick: ((features: List<Feature>) -> Unit)? = null,
  noinline onLongClick: ((features: List<Feature>) -> Unit)? = null,
) {
  composeKey(id) {
    LayerNode(
      factory = { FillLayer(id = id, source = source) },
      update = {
        set(sourceLayer) { layer.sourceLayer = it }
        set(minZoom) { layer.minZoom = it }
        set(maxZoom) { layer.maxZoom = it }
        set(filter) { layer.setFilter(it) }
        set(visible) { layer.visible = it }
        set(sortKey) { layer.setFillSortKey(it) }
        set(antialias) { layer.setFillAntialias(it) }
        set(opacity) { layer.setFillOpacity(it) }
        set(color) { layer.setFillColor(it) }
        set(outlineColor) { layer.setFillOutlineColor(it) }
        set(translate) { layer.setFillTranslate(it) }
        set(translateAnchor) { layer.setFillTranslateAnchor(it) }
        set(pattern) { layer.setFillPattern(it) }
      },
      onClick = onClick,
      onLongClick = onLongClick,
    )
  }
}
