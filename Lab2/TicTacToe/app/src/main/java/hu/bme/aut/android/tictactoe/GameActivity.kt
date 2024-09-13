package hu.bme.aut.android.tictactoe

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.android.tictactoe.databinding.ActivityGameBinding
import hu.bme.aut.android.tictactoe.model.TicTacToeModel

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun gameOver(result: Byte) {
        val message = when (result) {
            TicTacToeModel.CIRCLE -> getString(R.string.circle_won)
            TicTacToeModel.CROSS -> getString(R.string.cross_won)
            TicTacToeModel.DRAW -> getString(R.string.draw)
            else -> ""
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        finish()
    }
}