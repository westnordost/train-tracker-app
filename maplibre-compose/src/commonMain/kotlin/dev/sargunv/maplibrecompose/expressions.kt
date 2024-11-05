package dev.sargunv.maplibrecompose

import androidx.compose.ui.graphics.Color
import kotlin.jvm.JvmName

@Suppress("MemberVisibilityCanBePrivate")
public object ExpressionDsl {

  // basic types: https://maplibre.org/maplibre-style-spec/types/
  // minus point and padding, which don't seem to be used in expressions

  public fun const(string: String): Expression<String> = Expression.ofString(string)

  public fun const(number: Number): Expression<Number> = Expression.ofNumber(number)

  public fun const(bool: Boolean): Expression<Boolean> = Expression.ofBoolean(bool)

  public fun <T> `null`(): Expression<T?> = Expression.ofNull()

  public fun const(color: Color): Expression<Color> = Expression.ofColor(color)

  // expressions: https://maplibre.org/maplibre-style-spec/expressions/

  // variable binding

  /**
   * Binds expressions to named variables, which can then be referenced in the result expression
   * using [var].
   */
  public fun <Input, Output> let(
    name: String,
    value: Expression<Input>,
    expression: Expression<Output>,
  ): Expression<Output> = call("let", const(name), value, expression)

  /** References variable bound using [let]. */
  public fun <T> `var`(name: String): Expression<T> = call("var", const(name))

  // types

  /** Produces a literal array value. */
  public fun <T> literal(values: List<Expression<T>>): Expression<List<T>> =
    call("literal", Expression.ofList(values))

  /** Produces a literal object value. */
  public fun <T> literal(values: Map<String, Expression<T>>): Expression<Map<String, T>> =
    call("literal", Expression.ofMap(values))

  /**
   * Asserts that the input is an array (optionally with a specific item type and length). If, when
   * the input expression is evaluated, it is not of the asserted type, then this assertion will
   * cause the whole expression to be aborted.
   */
  public fun array(
    value: Expression<*>,
    type: Expression<String>? = null,
    length: Expression<Number>? = null,
  ): Expression<List<*>> {
    val args = buildList {
      type?.let { add(const("array")) }
      length?.let { add(const("array")) }
    }
    return call("array", value, *args.toTypedArray())
  }

  /** Returns a string describing the type of the given value. */
  public fun `typeof`(expression: Expression<*>): Expression<String> = call("typeof", expression)

  /**
   * Asserts that the input value is a string. If multiple values are provided, each one is
   * evaluated in order until a string is obtained. If none of the inputs are strings, the
   * expression is an error.
   */
  public fun string(value: Expression<*>, vararg fallbacks: Expression<*>): Expression<String> =
    call("string", value, *fallbacks)

  /**
   * Asserts that the input value is a number. If multiple values are provided, each one is
   * evaluated in order until a number is obtained. If none of the inputs are numbers, the
   * expression is an error.
   */
  public fun number(value: Expression<*>, vararg fallbacks: Expression<*>): Expression<Number> =
    call("number", value, *fallbacks)

  /**
   * Asserts that the input value is a boolean. If multiple values are provided, each one is
   * evaluated in order until a boolean is obtained. If none of the inputs are booleans, the
   * expression is an error.
   */
  public fun boolean(value: Expression<*>, vararg fallbacks: Expression<*>): Expression<Boolean> =
    call("boolean", value, *fallbacks)

  /**
   * Asserts that the input value is an object. If multiple values are provided, each one is
   * evaluated in order until an object is obtained. If none of the inputs are objects, the
   * expression is an error.
   */
  public fun `object`(
    value: Expression<*>,
    vararg fallbacks: Expression<*>,
  ): Expression<Map<String, *>> = call("object", value, *fallbacks)

  /**
   * Returns a collator for use in locale-dependent comparison operations. The [caseSensitive] and
   * [diacriticSensitive] options default to false. The [locale] argument specifies the IETF
   * language tag of the locale to use. If none is provided, the default locale is used. If the
   * requested locale is not available, the collator will use a system-defined fallback locale. Use
   * [resolvedLocale] to test the results of locale fallback behavior.
   */
  public fun collator(
    caseSensitive: Expression<Boolean>? = null,
    diacriticSensitive: Expression<Boolean>? = null,
    locale: Expression<String>? = null,
  ): Expression<TCollator> =
    call(
      "collator",
      buildOptions {
        caseSensitive?.let { put("case-sensitive", it) }
        diacriticSensitive?.let { put("diacritic-sensitive", it) }
        locale?.let { put("locale", it) }
      },
    )

  /**
   * Returns a formatted string for displaying mixed-format text in the text-field property. The
   * input may contain a string literal or expression, including an 'image' expression. Strings may
   * be followed by a style override object that supports the following properties:
   */
  public fun format(vararg sections: Pair<Expression<*>, FormatStyle>): Expression<TFormatted> =
    call(
      "format",
      *sections.foldToArgs { (value, style) ->
        add(value)
        add(
          buildOptions {
            style.textFont?.let { put("text-font", it) }
            style.textColor?.let { put("text-color", it) }
            style.fontScale?.let { put("font-scale", it) }
          }
        )
      },
    )

  public data class FormatStyle(
    val textFont: Expression<String>? = null,
    val textColor: Expression<String>? = null,
    val fontScale: Expression<Number>? = null,
  )

  /**
   * Returns an image type for use in [iconImage], *-pattern entries and as a section in the
   * [format] expression. If set, the image argument will check that the requested image exists in
   * the style and will return either the resolved image name or null, depending on whether or not
   * the image is currently in the style. This validation process is synchronous and requires the
   * image to have been added to the style before requesting it in the image argument.
   */
  public fun image(value: Expression<String>): Expression<TResolvedImage> = call("image", value)

  /**
   * Converts the input number into a string representation using the providing formatting rules. If
   * set, the locale argument specifies the locale to use, as a BCP 47 language tag. If set, the
   * currency argument specifies an ISO 4217 code to use for currency-style formatting. If set, the
   * min-fraction-digits and max-fraction-digits arguments specify the minimum and maximum number of
   * fractional digits to include.
   */
  public fun numberFormat(
    number: Expression<Number>,
    locale: Expression<String>? = null,
    currency: Expression<String>? = null,
    minFractionDigits: Expression<Number>? = null,
    maxFractionDigits: Expression<Number>? = null,
  ): Expression<String> =
    call(
      "number-format",
      number,
      buildOptions {
        locale?.let { put("locale", it) }
        currency?.let { put("currency", it) }
        minFractionDigits?.let { put("min-fraction-digits", it) }
        maxFractionDigits?.let { put("max-fraction-digits", it) }
      },
    )

  /**
   * Converts the input value to a string. If the input is null, the result is "". If the input is a
   * boolean, the result is "true" or "false". If the input is a number, it is converted to a string
   * as specified by the "NumberToString" algorithm of the ECMAScript Language Specification. If the
   * input is a color, it is converted to a string of the form "rgba(r,g,b,a)", where r, g, and b
   * are numerals ranging from 0 to 255, and a ranges from 0 to 1. Otherwise, the input is converted
   * to a string in the format specified by the JSON.stringify function of the ECMAScript Language
   * Specification.
   */
  public fun toString(value: Expression<*>): Expression<String> = call("to-string", value)

  /**
   * Converts the input value to a number, if possible. If the input is null or false, the result
   * is 0. If the input is true, the result is 1. If the input is a string, it is converted to a
   * number as specified by the "ToNumber Applied to the String Type" algorithm of the ECMAScript
   * Language Specification. If multiple values are provided, each one is evaluated in order until
   * the first successful conversion is obtained. If none of the inputs can be converted, the
   * expression is an error.
   */
  public fun toNumber(value: Expression<*>, vararg fallbacks: Expression<*>): Expression<Number> =
    call("to-number", value, *fallbacks)

  /**
   * Converts the input value to a boolean. The result is false when then input is an empty string,
   * 0, false, null, or NaN; otherwise it is true.
   */
  public fun toBoolean(value: Expression<*>): Expression<Boolean> = call("to-boolean", value)

  /**
   * Converts the input value to a color. If multiple values are provided, each one is evaluated in
   * order until the first successful conversion is obtained. If none of the inputs can be
   * converted, the expression is an error.
   */
  public fun toColor(value: Expression<*>, vararg fallbacks: Expression<*>): Expression<Color> =
    call("to-color", value, *fallbacks)

  // lookup

  /** Retrieves an item from an array. */
  public fun <T> at(index: Expression<Number>, array: Expression<List<T>>): Expression<T> =
    call("at", index, array)

  /** Determines whether an item exists in an array or a substring exists in a string. */
  public fun `in`(needle: Expression<*>, haystack: Expression<*>): Expression<Boolean> =
    call("in", needle, haystack)

  /**
   * Returns the first position at which an item can be found in an array or a substring can be
   * found in a string, or -1 if the input cannot be found. Accepts an optional index from where to
   * begin the search. In a string, a UTF-16 surrogate pair counts as a single position.
   */
  public fun indexOf(
    value: Expression<*>,
    array: Expression<List<*>>,
    start: Expression<Number>? = null,
  ): Expression<Number> {
    val args = buildList {
      add(value)
      add(array)
      start?.let { add(it) }
    }
    return call("index-of", *args.toTypedArray())
  }

  /**
   * Returns a substring from a string from a specified start index, or between a start index and an
   * end index if set. The return value is inclusive of the start index but not of the end index. A
   * UTF-16 surrogate pair counts as a single position.
   */
  @JvmName("sliceString")
  public fun slice(
    value: Expression<String>,
    start: Expression<Number>,
    length: Expression<Number>? = null,
  ): Expression<String> {
    val args = buildList {
      add(value)
      add(start)
      length?.let { add(it) }
    }
    return call("slice", *args.toTypedArray())
  }

  /**
   * Returns an item from an list from a specified start index, or between a start index and an end
   * index if set. The return value is inclusive of the start index but not of the end index.
   */
  @JvmName("sliceList")
  public fun <T> slice(
    value: Expression<List<T>>,
    start: Expression<Number>,
    length: Expression<Number>? = null,
  ): Expression<T> {
    val args = buildList {
      add(value)
      add(start)
      length?.let { add(it) }
    }
    return call("slice", *args.toTypedArray())
  }

  /**
   * Retrieves a property value from the current feature's properties, or from another object if a
   * second argument is provided. Returns null if the requested property is missing.
   */
  public fun <T> get(
    key: Expression<String>,
    obj: Expression<Map<String, *>>? = null,
  ): Expression<T> {
    val args = obj?.let { listOf(key, it) } ?: listOf(key)
    return call("get", *args.toTypedArray())
  }

  /**
   * Tests for the presence of an property value in the current feature's properties, or from
   * another object if a second argument is provided.
   */
  public fun has(
    key: Expression<String>,
    obj: Expression<Map<String, *>>? = null,
  ): Expression<Boolean> {
    val args = obj?.let { listOf(key, it) } ?: listOf(key)
    return call("has", *args.toTypedArray())
  }

  /**
   * Gets the length of an array or string. In a string, a UTF-16 surrogate pair counts as a single
   * position.
   */
  @JvmName("lengthOfString")
  public fun length(value: Expression<String>): Expression<Number> = call("length", value)

  /**
   * Gets the length of an array or string. In a string, a UTF-16 surrogate pair counts as a single
   * position.
   */
  @JvmName("lengthOfList")
  public fun length(value: Expression<List<*>>): Expression<Number> = call("length", value)

  // decision

  /**
   * Selects the first output whose corresponding test condition evaluates to true, or the fallback
   * value otherwise.
   */
  public fun <T> case(branches: List<CaseBranch<T>>, fallback: Expression<T>): Expression<T> =
    call(
      "case",
      *branches.foldToArgs { (test, output) ->
        add(test)
        add(output)
      },
      fallback,
    )

  public data class CaseBranch<Output>
  internal constructor(
    internal val test: Expression<Boolean>,
    internal val output: Expression<Output>,
  )

  public infix fun <Output> Expression<Boolean>.then(
    output: Expression<Output>
  ): CaseBranch<Output> = CaseBranch(this, output)

  /**
   * Selects the output whose label value matches the input value, or the fallback value if no match
   * is found. The input can be any expression (e.g. ["get", "building_type"]). Each label must be
   * either:
   * - a single string; or
   * - a list of strings. The input matches if any of the values in the array matches, similar to
   *   the "in" operator.
   *
   * Each label must be unique. If the input type does not match the type of the labels, the result
   * will be the fallback value.
   */
  @JvmName("matchStrings")
  public fun <T> match(
    input: Expression<*>,
    fallback: Expression<T>,
    vararg branches: MatchBranch<String, T>,
  ): Expression<T> =
    call(
      "match",
      input,
      *branches.foldToArgs { (label, output) ->
        add(label)
        add(output)
      },
      fallback,
    )

  /**
   * Selects the output whose label value matches the input value, or the fallback value if no match
   * is found. The input can be any expression (e.g. ["get", "building_type"]). Each label must be
   * either:
   * - a single number; or
   * - a list of numbers. The input matches if any of the values in the array matches, similar to
   *   the "in" operator.
   *
   * Each label must be unique. If the input type does not match the type of the labels, the result
   * will be the fallback value.
   */
  @JvmName("matchNumbers")
  public fun <T> match(
    input: Expression<*>,
    fallback: Expression<T>,
    vararg branches: MatchBranch<Number, T>,
  ): Expression<T> =
    call(
      "match",
      input,
      *branches.foldToArgs { (label, output) ->
        add(label)
        add(output)
      },
      fallback,
    )

  @Suppress("unused")
  public data class MatchBranch<Label, Output>
  internal constructor(internal val label: Expression<*>, internal val output: Expression<Output>)

  public infix fun <Output> String.then(output: Expression<Output>): MatchBranch<String, Output> =
    MatchBranch(const(this), output)

  public infix fun <Output> Number.then(output: Expression<Output>): MatchBranch<Number, Output> =
    MatchBranch(const(this), output)

  @JvmName("stringsThen")
  public infix fun <Output> List<String>.then(
    output: Expression<Output>
  ): MatchBranch<String, Output> = MatchBranch(Expression.ofList(this.map(::const)), output)

  @JvmName("numbersThen")
  public infix fun <Output> List<Number>.then(
    output: Expression<Output>
  ): MatchBranch<Number, Output> = MatchBranch(Expression.ofList(this.map(::const)), output)

  /**
   * Evaluates each expression in turn until the first non-null value is obtained, and returns that
   * value.
   */
  public fun <T> coalesce(first: Expression<T?>, vararg values: Expression<T?>): Expression<T> =
    call("coalesce", first, *values)

  // TODO above are in the right order from the docs, below are not

  public fun <Output> interpolate(
    type: Expression<TInterpolationType>,
    input: Expression<Number>,
    vararg stops: Pair<Number, Expression<Output>>,
  ): Expression<Output> =
    call(
      "interpolate",
      type,
      input,
      *stops
        .sortedBy { it.first.toDouble() }
        .foldToArgs {
          add(const(it.first))
          add(it.second)
        },
    )

  public fun linear(): Expression<TInterpolationType> = call("linear")

  public fun exponential(base: Expression<Number>): Expression<TInterpolationType> =
    call("exponential", base)

  public fun cubicBezier(
    x1: Expression<Number>,
    y1: Expression<Number>,
    x2: Expression<Number>,
    y2: Expression<Number>,
  ): Expression<TInterpolationType> = call("cubic-bezier", x1, y1, x2, y2)

  public fun zoom(): Expression<Number> = call("zoom")

  public fun rgba(
    red: Expression<Number>,
    green: Expression<Number>,
    blue: Expression<Number>,
    alpha: Expression<Number>,
  ): Expression<Color> = call("rgba", red, green, blue, alpha)

  public fun rgb(
    red: Expression<Number>,
    green: Expression<Number>,
    blue: Expression<Number>,
  ): Expression<Color> = call("rgb", red, green, blue)

  // utils

  @Suppress("UNCHECKED_CAST")
  private fun <Return> call(function: String, vararg args: Expression<*>) =
    Expression.ofList(listOf(const(function), *args)) as Expression<Return>

  private inline fun buildOptions(block: MutableMap<String, Expression<*>>.() -> Unit) =
    Expression.ofMap(mutableMapOf<String, Expression<*>>().apply(block))

  private fun <T> Array<T>.foldToArgs(block: MutableList<Expression<*>>.(element: T) -> Unit) =
    fold(mutableListOf<Expression<*>>()) { acc, element -> acc.apply { block(element) } }
      .toTypedArray()

  private fun <T> List<T>.foldToArgs(block: MutableList<Expression<*>>.(element: T) -> Unit) =
    fold(mutableListOf<Expression<*>>()) { acc, element -> acc.apply { block(element) } }
      .toTypedArray()
}

public fun <T> useExpressions(block: ExpressionDsl.() -> T): T = block(ExpressionDsl)

// would make this an inline value class, but we lose varargs
// https://youtrack.jetbrains.com/issue/KT-33565/Allow-vararg-parameter-of-inline-class-type
public data class Expression<out T> internal constructor(internal val value: Any?) {
  internal companion object {
    fun ofString(string: String): Expression<String> = Expression(string)

    fun ofNumber(number: Number): Expression<Number> = Expression(number)

    fun ofBoolean(bool: Boolean): Expression<Boolean> = Expression(bool)

    fun ofNull(): Expression<Nothing?> = Expression(null)

    fun ofColor(color: Color): Expression<Color> = Expression(color.toMlnColor())

    fun ofList(list: List<Expression<*>>): Expression<List<*>> = Expression(list.map { it.value })

    fun ofMap(map: Map<String, Expression<*>>): Expression<Map<String, *>> =
      Expression(map.entries.associate { (key, value) -> key to value.value })
  }
}

internal expect fun Color.toMlnColor(): Any

// token types for expression type safety; these should never be instantiated
// based on non primitive types from https://maplibre.org/maplibre-style-spec/types/
// point and padding don't seem to be used in expressions, so I didn't include them

public sealed interface TFormatted

public sealed interface TResolvedImage

// also some extra tokens invented here

public sealed interface TCollator

public sealed interface TInterpolationType

// enum constants
