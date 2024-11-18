package dev.sargunv.maplibrekmp.core.layer

import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.expression.Expression

@PublishedApi
internal actual sealed class UserFeatureLayer actual constructor(actual val source: Source) :
  UserLayer() {
  actual abstract var sourceLayer: String

  actual abstract fun setFilter(filter: Expression<Boolean>)
}
