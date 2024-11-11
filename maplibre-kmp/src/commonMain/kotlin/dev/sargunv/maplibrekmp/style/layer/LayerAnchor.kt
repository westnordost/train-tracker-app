package dev.sargunv.maplibrekmp.style.layer

import androidx.compose.runtime.Immutable

@Immutable
public sealed interface LayerAnchor {
  public data object Top : LayerAnchor

  public data object Bottom : LayerAnchor

  public data class Above(val layerId: String) : LayerAnchor

  public data class Below(val layerId: String) : LayerAnchor
}
