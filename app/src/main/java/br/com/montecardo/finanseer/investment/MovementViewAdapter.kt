package br.com.montecardo.finanseer.investment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.montecardo.finanseer.R
import br.com.montecardo.finanseer.investment.data.Movement
import br.com.montecardo.finanseer.investment.data.State
import kotlinx.android.synthetic.main.fragment_movement.view.*

/**
 * [RecyclerView.Adapter] that can display a [State].
 */
class MovementViewAdapter(values: List<Movement> = emptyList()) :
        RecyclerView.Adapter<MovementViewAdapter.ViewHolder>() {

    private val list = values.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_movement, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun refresh(values: List<Movement> = emptyList()) {
        list.clear()
        list.addAll(values)
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(private val mView: View) : RecyclerView.ViewHolder(mView) {
        private var item: Movement? = null

        fun bind(item: Movement) {
            this.item = item

            mView.mov_frag_date.text = item.date.toString()
            mView.mov_frag_identification.text = item.name
            mView.mov_frag_invested.text = item.money.amount.toPlainString()
        }
    }
}