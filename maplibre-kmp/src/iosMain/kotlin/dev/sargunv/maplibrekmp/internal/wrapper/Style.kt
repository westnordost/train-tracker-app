package dev.sargunv.maplibrekmp.internal.wrapper

import cocoapods.MapLibre.MLNSource
import cocoapods.MapLibre.MLNStyle
import cocoapods.MapLibre.MLNStyleLayer
import dev.sargunv.maplibrekmp.internal.wrapper.layer.Layer
import dev.sargunv.maplibrekmp.internal.wrapper.layer.NativeLayer
import dev.sargunv.maplibrekmp.internal.wrapper.source.NativeSource
import dev.sargunv.maplibrekmp.internal.wrapper.source.Source

internal actual class Style private actual constructor() {
  private lateinit var impl: MLNStyle

  internal constructor(style: MLNStyle) : this() {
    impl = style
  }

  actual fun addSource(source: Source) {
    impl.addSource(source.impl)
  }

  actual fun removeSource(source: Source) {
    impl.removeSource(source.impl)
  }

  actual fun getSource(id: String): Source? {
    return impl.sourceWithIdentifier(id)?.let { NativeSource(it) }
  }

  actual fun getSources(): List<Source> {
    return impl.sources.map { NativeSource(it as MLNSource) }
  }

  actual fun addLayer(layer: Layer) {
    impl.addLayer(layer.impl)
  }

  actual fun addLayerAbove(id: String, layer: Layer) {
    impl.insertLayer(layer.impl, aboveLayer = impl.layerWithIdentifier(id)!!)
  }

  actual fun addLayerBelow(id: String, layer: Layer) {
    impl.insertLayer(layer.impl, belowLayer = impl.layerWithIdentifier(id)!!)
  }

  actual fun addLayerAt(index: Int, layer: Layer) {
    impl.insertLayer(layer.impl, atIndex = index.toULong())
  }

  actual fun removeLayer(layer: Layer) {
    impl.removeLayer(layer.impl)
  }

  actual fun getLayer(id: String): Layer? {
    return impl.layerWithIdentifier(id)?.let { NativeLayer(it) }
  }

  actual fun getLayers(): List<Layer> {
    return impl.layers.map { NativeLayer(it as MLNStyleLayer) }
  }
}
