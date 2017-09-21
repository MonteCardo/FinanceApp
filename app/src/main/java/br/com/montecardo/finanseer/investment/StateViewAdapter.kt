package br.com.montecardo.finanseer.investment

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.montecardo.finanseer.R
import br.com.montecardo.finanseer.investment.data.Investment
import br.com.montecardo.finanseer.investment.data.State
import kotlinx.android.synthetic.main.fragment_investment.view.*

/**
 * [RecyclerView.Adapter] that can display a [State].
 */
class StateViewAdapter(private val selectionListener: (inv: Investment) -> Unit,
                       values: List<Investment> = emptyList()) :
        RecyclerView.Adapter<StateViewAdapter.ViewHolder>() {

    private val list = values.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_investment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun add(inv: Investment) {
        list.add(inv)
        notifyItemInserted(itemCount - 1)
    }

    fun refresh(values: List<Investment> = emptyList()) {
        list.clear()
        list.addAll(values)
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private var investment: Investment? = null

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(inv: Investment) {
            investment = inv
            val placeholder = "0.0"

            view.inv_frag_identification.text = inv.name
            view.inv_frag_invested_total.text = placeholder
            view.inv_frag_invested_period.text = placeholder
            view.inv_frag_net_gain_total.text = placeholder
            view.inv_frag_net_gain_period.text = placeholder
            view.inv_frag_available.text = placeholder
        }

        override fun onClick(v: View) {
            with(investment) {
                if (this != null) {
                    selectionListener(this)
                } else {
                    Log.e(javaClass.simpleName, "No investmentId found on clicked item.")
                }
            }
        }
    }
}