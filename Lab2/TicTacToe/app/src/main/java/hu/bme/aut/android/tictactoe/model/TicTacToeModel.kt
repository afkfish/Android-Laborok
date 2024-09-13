package hu.bme.aut.android.tictactoe.model

import kotlin.experimental.and

object TicTacToeModel {

    const val EMPTY: Byte = 0
    const val CIRCLE: Byte = 1
    const val CROSS: Byte = 2
    const val DRAW: Byte = 3

    var nextPlayer: Byte = CIRCLE

    private var model: Array<ByteArray> = arrayOf(
        byteArrayOf(EMPTY, EMPTY, EMPTY),
        byteArrayOf(EMPTY, EMPTY, EMPTY),
        byteArrayOf(EMPTY, EMPTY, EMPTY))

    fun resetModel() {
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                model[i][j] = EMPTY
            }
        }
    }

    fun getFieldContent(x: Int, y: Int): Byte {
        return model[x][y]
    }

    fun changeNextPlayer() {
        nextPlayer = if (nextPlayer == CIRCLE) CROSS else CIRCLE
    }

    fun setFieldContent(x: Int, y: Int, content: Byte): Byte {
        changeNextPlayer()
        model[x][y] = content
        return content
    }

    // JOYAXJ
    fun checkWinner(): Byte {
        // Check rows
        for (i in 0 until 3) {
            if (model[i][0] != EMPTY && model[i][0] == model[i][1] && model[i][1] == model[i][2]) {
                return model[i][0]
            }
        }

        // Check columns
        for (j in 0 until 3) {
            if (model[0][j] != EMPTY && model[0][j] == model[1][j] && model[1][j] == model[2][j]) {
                return model[0][j]
            }
        }

        // Check diagonals
        if (model[0][0] != EMPTY && model[0][0] == model[1][1] && model[1][1] == model[2][2]) {
            return model[0][0]
        }
        if (model[0][2] != EMPTY && model[0][2] == model[1][1] && model[1][1] == model[2][0]) {
            return model[0][2]
        }

        // Check for draw
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (model[i][j] == EMPTY) {
                    return EMPTY // Game is still ongoing
                }
            }
        }

        return DRAW // All cells are filled, but no winner
    }
}