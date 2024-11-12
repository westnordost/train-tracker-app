package dev.sargunv.maplibrekmp.internal.wrapper.layer

import cocoapods.MapLibre.MLNStyleLayer
import dev.sargunv.maplibrekmp.internal.wrapper.source.Source

@PublishedApi
internal actual class NativeLayer private actual constructor(override val id: String) : Layer() {
  private lateinit var _impl: MLNStyleLayer

  constructor(layer: MLNStyleLayer) : this(layer.identifier) {
    _impl = layer
  }

  override val impl
    get() = _impl

  override val source: Source
    get() = TODO("Not yet implemented")
}
