package dev.sargunv.maplibrekmp.internal.wrapper.layer

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import dev.sargunv.maplibrekmp.style.expression.Expression
import dev.sargunv.maplibrekmp.style.expression.Point

internal object ExpressionAdapter {
  fun Expression<*>.convert(): org.maplibre.android.style.expressions.Expression =
    org.maplibre.android.style.expressions.Expression.Converter.convert(normalizeJsonLike(value))

  private fun normalizeJsonLike(value: Any?): JsonElement =
    when (value) {
      null -> JsonNull.INSTANCE
      is Boolean -> JsonPrimitive(value)
      is Number -> JsonPrimitive(value)
      is String -> JsonPrimitive(value)
      is List<*> -> JsonArray().apply { value.forEach { add(normalizeJsonLike(it)) } }
      is Map<*, *> ->
        JsonObject().apply { value.forEach { add(it.key as String, normalizeJsonLike(it.value)) } }
      is Point ->
        JsonArray().apply {
          add(value.x)
          add(value.y)
        }
      is Color ->
        JsonPrimitive(
          value.toArgb().let {
            "rgba(${(it shr 16) and 0xFF}, ${(it shr 8) and 0xFF}, ${it and 0xFF}, ${value.alpha})"
          }
        )
      else -> throw IllegalArgumentException("Unsupported type: ${value::class}")
    }
}
