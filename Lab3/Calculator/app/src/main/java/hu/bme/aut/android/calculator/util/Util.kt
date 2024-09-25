package hu.bme.aut.android.calculator.util

object Util {
    const val COMMA = "."

    private const val numberPattern = "0[.][0-9]+|[1-9][0-9]*[.][0-9]+|[1-9][0-9]*|0|^[\\s]"
    val numberRegex = Regex(numberPattern)

    private const val halfOperation = "[/*%+-]($numberPattern)|^[\\s]"
    val halfOperationRegex = Regex(halfOperation)

    private const val operationSymbolPattern = "[/*%+-]|^[\\s]"
    val operationSymbol = Regex(operationSymbolPattern)
}