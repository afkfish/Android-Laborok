package hu.bme.aut.android.tictactoe

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.android.tictactoe.databinding.ActivityMainBinding
import hu.bme.aut.android.tictactoe.model.TicTacToeModel

// JOYAXJ
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnHighScores.setOnClickListener {
            Toast.makeText(
                this@MainActivity,
                getString(R.string.toast_highscore),
                Toast.LENGTH_LONG
            ).show()
        }

        binding.btnStart.setOnClickListener {
            TicTacToeModel.resetModel()
            startActivity(Intent(this@MainActivity, GameActivity::class.java))
        }

        binding.btnAbout.setOnClickListener {
            startActivity(Intent(this@MainActivity, AboutActivity::class.java))
        }
    }
}