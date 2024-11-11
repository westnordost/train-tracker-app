package dev.sargunv.maplibrekmp.style.expression

import androidx.compose.runtime.Immutable

// token types for expression type safety; these should never be instantiated

public sealed interface TFormatted

public sealed interface TResolvedImage

public sealed interface TCollator

public sealed interface TInterpolationType

public sealed interface TGeometry // TODO create a real type and Expression constructor

@Immutable public data class Point(val x: Number, val y: Number)
