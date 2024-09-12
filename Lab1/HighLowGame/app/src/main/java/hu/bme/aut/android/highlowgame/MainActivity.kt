package hu.bme.aut.android.highlowgame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.android.highlowgame.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    companion object {
        const val KEY_NUM = "KEY_NUM"
    }

    lateinit var binding: ActivityMainBinding

    var generatedNum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null && savedInstanceState!!.containsKey(KEY_NUM)) {
            generatedNum = savedInstanceState.getInt(KEY_NUM)
        } else {
            generateNewNumber()
        }

        binding.btnGuess.setOnClickListener {
            try {

                if (binding.etGuess.text!!.isNotEmpty()) {
                    val myNum = binding.etGuess.text.toString().toInt()

                    if (myNum == generatedNum) {
                        binding.tvResult.text = "${binding.etName.text.toString()}, You have won!"

                    } else if (myNum < generatedNum) {
                        binding.tvResult.text = "The number is higher"
                    } else if (myNum > generatedNum) {
                        binding.tvResult.text = "The number is lower"
                    }
                } else {
                    binding.etGuess.error = "This value is not valid"

                }

            } catch (e: Exception) {
                binding.etGuess.error = e.message
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_NUM, generatedNum)

        super.onSaveInstanceState(outState)
    }


    fun generateNewNumber() {
        val rand = Random(System.currentTimeMillis())
        generatedNum = rand.nextInt(3) // 0..2
    }
}