package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.expression.Expression

@PublishedApi
internal expect class HeatmapLayer(id: String, source: Source) : FeatureLayer {
  fun setHeatmapRadius(radius: Expression<Number>)

  fun setHeatmapWeight(weight: Expression<Number>)

  fun setHeatmapIntensity(intensity: Expression<Number>)

  fun setHeatmapColor(color: Expression<Color>)

  fun setHeatmapOpacity(opacity: Expression<Number>)
}

