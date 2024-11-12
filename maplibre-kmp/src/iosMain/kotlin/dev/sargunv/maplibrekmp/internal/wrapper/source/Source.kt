package dev.sargunv.maplibrekmp.internal.wrapper.source

import cocoapods.MapLibre.MLNSource

@PublishedApi
internal actual sealed class Source {
  actual abstract val id: String
  abstract val impl: MLNSource
}
