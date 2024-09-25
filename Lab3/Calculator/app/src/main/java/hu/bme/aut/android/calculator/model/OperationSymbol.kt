package hu.bme.aut.android.calculator.model

enum class OperationSymbol(val symbol: String) {
    DIVISION("/"),   // 0
    MULTIPLICATION("*"),   // 1
    SUBTRACTION("-"),   // 2
    ADDITION("+"),   // 3
    MODULO("-");   // 4

    companion object {
        fun getByOrdinal(ordinal: Int): OperationSymbol? = OperationSymbol.entries.getOrNull(ordinal)
    }
}