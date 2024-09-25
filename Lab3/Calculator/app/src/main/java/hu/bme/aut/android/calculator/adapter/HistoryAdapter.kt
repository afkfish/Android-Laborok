package hu.bme.aut.android.calculator.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.calculator.R
import hu.bme.aut.android.calculator.databinding.ViewHistoryItemBinding
import hu.bme.aut.android.calculator.util.CalculatorOperator

// JOYAXJ
class HistoryAdapter(
    private val onClickListener: ClickListener,
    private val history: List<CalculatorOperator.CalculatorState>,
    private val context: Context
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewHistoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val operation = history[position]
        holder.operationTextView.text = context.getString(
            R.string.text_operation,
            operation.number1,
            operation.operation.symbol,
            operation.number2,
            operation.result
        )
        holder.loadButton.setOnClickListener {
            if (operation.result % 1.0 == 0.0) {
                onClickListener.onClick(operation.result.toInt().toString())
            } else {
                onClickListener.onClick(String.format("%.10f", operation.result))
            }
        }
    }

    override fun getItemCount(): Int = history.size

    inner class ViewHolder(binding: ViewHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.historyView) {

        val operationTextView: TextView
        val loadButton: Button

        init {
            operationTextView = binding.operationTextView
            loadButton = binding.loadButton
        }
    }

    interface ClickListener {
        fun onClick(loadedData: String)
    }
}