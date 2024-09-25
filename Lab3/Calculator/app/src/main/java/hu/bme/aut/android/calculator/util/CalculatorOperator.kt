package hu.bme.aut.android.calculator.util

import hu.bme.aut.android.calculator.model.OperationSymbol

object CalculatorOperator {

    var state: CalculatorState = CalculatorState()
        private set

    val history = mutableListOf<CalculatorState>()

    fun loadState(value: String) {
        state = state.copy(
            input = "",
            number1 = value.toDouble(),
            number2 = 0.0,
            result = 0.0,
            operation = OperationSymbol.ADDITION
        )
    }

    private fun addStateToHistory() {
        history.add(state)
    }

    data class CalculatorState(
        val input: String = "",
        val number1: Double = Double.NaN,
        val number2: Double = Double.NaN,
        val operation: OperationSymbol = OperationSymbol.ADDITION,
        val result: Double = Double.NaN
    )

    fun clearHistory() {
        history.clear()
    }

    fun onNumberPressed(number: Int) {
        state = state.copy(
            input = state.input + "$number"
        )
    }

    fun onOperationPressed(operation: Int) {
        val input = state.input
        if (Util.numberRegex.matches(input)) {
            state = state.copy(
                number1 = Util.numberRegex.find(input)!!.value.toDouble(),
                operation = OperationSymbol.getByOrdinal(operation)!!,
                input = OperationSymbol.getByOrdinal(operation)!!.symbol
            )
        } else if (Util.halfOperationRegex.matches(input)) {
            val number2 = Util.numberRegex.find(input)!!.value.toDouble()
            state = state.copy(
                number2 = number2,
                result = countResult(number2),
            )

            addStateToHistory()

            state = state.copy(
                number1 = state.result,
                number2 = Double.NaN,
                operation = OperationSymbol.getByOrdinal(operation)!!,
                input =  OperationSymbol.getByOrdinal(operation)!!.symbol
            )
        } else if (Util.operationSymbol.matches(input)) {
            state = state.copy(
                operation = OperationSymbol.getByOrdinal(operation)!!,
                input = OperationSymbol.getByOrdinal(operation)!!.symbol
            )
        } else {
            state = state.copy(
                operation = OperationSymbol.getByOrdinal(operation)!!,
                input = OperationSymbol.getByOrdinal(operation)!!.symbol
            )
        }
    }

    private fun countResult(number2: Double): Double {
        return when (state.operation) {
            OperationSymbol.DIVISION -> state.number1 / number2
            OperationSymbol.MULTIPLICATION -> state.number1 * number2
            OperationSymbol.MODULO -> state.number1 % number2
            OperationSymbol.ADDITION -> state.number1 + number2
            OperationSymbol.SUBTRACTION -> state.number1 - number2
        }
    }

    fun onSignChange(): Double {
        val input = state.input
        return if (Util.numberRegex.matches(input)) {
            val number1 = -Util.numberRegex.find(input)!!.value.toDouble()
            state = state.copy(
                result = Double.NaN,
                number1 = number1,
                input = ""
            )
            number1
        } else if (Util.halfOperationRegex.matches(input)) {
            val number2 =  -Util.numberRegex.find(input)!!.value.toDouble()
            state = state.copy(
                number2 = number2,
                input = ""
            )
            number2
        } else Double.NaN
    }

    fun addComa() {
        state = state.copy(
            input = state.input + Util.COMMA
        )
    }

    fun onEquivalence(): Double {
        val input = state.input
        return if (Util.halfOperationRegex.matches(input)) {
            val number2 = Util.numberRegex.find(input)!!.value.toDouble()
            val result = countResult(number2)

            state = state.copy(
                number2 = number2,
                result = result
            )

            addStateToHistory()

            state = state.copy(
                input = "",
                number1 = result,
                number2 = Double.NaN,
                operation = OperationSymbol.ADDITION,
                result = Double.NaN
            )
            result
        } else if (!state.number2.isNaN()) {
            val result = countResult(state.number2)
            addStateToHistory()
            state = state.copy(
                input = "",
                number1 = result,
                number2 = Double.NaN,
                operation = OperationSymbol.ADDITION,
                result = Double.NaN
            )
            result
        } else Double.NaN
    }

    fun onDelete() {
        state = state.copy(
            number1 = Double.NaN,
            number2 = Double.NaN,
            result = Double.NaN,
            input = ""
        )
    }

    fun onClear() {
        state = state.copy(
            number2 = Double.NaN,
            input = state.operation.symbol
        )
    }
}