package dev.sargunv.kotlincsv

public data class CsvEncoding(
  val quote: Char = '"',
  val delimiter: Char = ',',
  val writeCrlf: Boolean = false,
) {
  // line delimiters prob shouldn't be configurable
  public val newline: Char = '\n'
  public val carriageReturn: Char = '\r'

  init {
    require(quote != delimiter) { "Quote and delimiter must be different" }
  }
}
