package dev.sargunv.maplibrekmp.core.source

import dev.sargunv.maplibrekmp.core.util.correctedAndroidUri
import dev.sargunv.maplibrekmp.core.util.toMLNExpression
import dev.sargunv.maplibrekmp.expression.Expression.Companion.const
import io.github.dellisd.spatialk.geojson.GeoJson
import org.maplibre.android.style.sources.GeoJsonOptions as MLNGeoJsonOptions
import org.maplibre.android.style.sources.GeoJsonSource as MLNGeoJsonSource

@PublishedApi
internal actual class GeoJsonSource : Source {
  override val impl: MLNGeoJsonSource

  actual constructor(id: String, dataUrl: String, options: GeoJsonOptions) {
    impl = MLNGeoJsonSource(id, dataUrl.correctedAndroidUri(), buildOptionMap(options))
  }

  actual constructor(id: String, data: GeoJson, options: GeoJsonOptions) {
    impl = MLNGeoJsonSource(id, data.json(), buildOptionMap(options))
  }

  private fun buildOptionMap(options: GeoJsonOptions) =
    MLNGeoJsonOptions().apply {
      withMaxZoom(options.maxZoom)
      withBuffer(options.buffer)
      withTolerance(options.tolerance)
      withLineMetrics(options.lineMetrics)
      withCluster(options.cluster)
      withClusterMaxZoom(options.clusterMaxZoom)
      withClusterRadius(options.clusterRadius)
      options.clusterProperties.forEach { (key, value) ->
        withClusterProperty(
          key,
          const(value.operator).toMLNExpression()!!,
          value.mapper.toMLNExpression()!!,
        )
      }
    }

  actual fun setDataUrl(url: String) {
    impl.setUri(url.correctedAndroidUri())
  }

  actual fun setData(geoJson: GeoJson) {
    impl.setGeoJson(geoJson.json())
  }
}
