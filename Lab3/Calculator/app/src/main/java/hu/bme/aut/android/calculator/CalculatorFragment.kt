package hu.bme.aut.android.calculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.calculator.databinding.FragmentCalculatorBinding
import hu.bme.aut.android.calculator.util.CalculatorOperator
import hu.bme.aut.android.calculator.util.Util.numberRegex

// JOYAXJ
class CalculatorFragment : Fragment() {
    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!

    private lateinit var numberButtons: Set<Button>
    private lateinit var operationButtons: Set<Button>
    private val calcState get() = CalculatorOperator.state

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButtons()
        setResult(CalculatorOperator.state.number1)

        with(binding.consoleTextView) {
            isClickable = true
            setOnClickListener {
                val action = CalculatorFragmentDirections.actionCalculatorFragmentToHistoryFragment()
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setResult(value: Double) {
        if (value.isNaN()) {
            binding.consoleTextView.text = ""
        } else if (value % 1.0 == 0.0) {
            binding.consoleTextView.text = value.toInt().toString()
        } else {
            binding.consoleTextView.text = String.format("%.2f",value)
        }
    }

    private fun setDeleteButtonText() {
        binding.deleteButton.text = if (calcState.number1.isNaN() or binding.consoleTextView.text.isEmpty()) {
            "C"
        } else {
            "CE"
        }
    }

    private fun initButtons() {
        // Init number and operation button sets
        with(binding) {
            numberButtons = setOf(
                number0Button, number1Button, number2Button,
                number3Button, number4Button, number5Button,
                number6Button, number7Button, number8Button,
                number9Button
            )

            operationButtons = setOf(
                operationDivisionButton,
                operationMultiplicationButton,
                operationSubtractionButton,
                operationAdditionButton,
                moduloButton
            )
        }

        // Init click listeners for number buttons
        numberButtons.forEachIndexed { number, button ->
            button.setOnClickListener {
                CalculatorOperator.onNumberPressed(number)
                binding.consoleTextView.text = numberRegex.find(calcState.input)?.value ?: ""
                setDeleteButtonText()
            }
        }

        // Init click listeners for number buttons
        operationButtons.forEachIndexed { operation, button ->
            button.setOnClickListener {
                CalculatorOperator.onOperationPressed(operation)
                setResult(calcState.result)
            }
        }

        // Init click listener for sign button
        binding.signButton.setOnClickListener {
            setResult(CalculatorOperator.onSignChange())
        }

        // Init click listener for delete button
        binding.deleteButton.setOnClickListener {
            if (binding.consoleTextView.text.isNotEmpty()) {
                CalculatorOperator.onClear()
            } else {
                CalculatorOperator.onDelete()
            }
            setDeleteButtonText()
            binding.consoleTextView.text = ""
        }

        // Init click listener for comma button
        binding.commaButton.setOnClickListener {
            CalculatorOperator.addComa()
            binding.consoleTextView.text = calcState.input
        }

        // Init click listener for equivalence button
        binding.operationEquivalenceButton.setOnClickListener {
            setResult(CalculatorOperator.onEquivalence())
        }
    }
}
// JOYAXJ