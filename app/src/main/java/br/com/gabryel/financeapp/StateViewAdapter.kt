package br.com.gabryel.financeapp

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.gabryel.financeapp.investment.State

/**
 * [RecyclerView.Adapter] that can display a [State].
 */
class StateViewAdapter(private val mValues: List<State>) :
        RecyclerView.Adapter<StateViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_datapoint, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mValues[position])
    }

    override fun getItemCount() = mValues.size

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView), View.OnClickListener {

        private val mIdView = mView.findViewById(R.id.identification) as TextView
        private val mAvailableView = mView.findViewById(R.id.available) as TextView

        private val mInvestTotalView = mView.findViewById(R.id.investedTotal) as TextView
        private val mInvestPeriodView = mView.findViewById(R.id.investedPeriod) as TextView

        private val mNetGainTotalView = mView.findViewById(R.id.netGainTotal) as TextView
        private val mNetGainPeriodView = mView.findViewById(R.id.netGainPeriod) as TextView

        var mItem: State? = null

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: State) {
            mItem = item
            mIdView.text = item.identification
            mInvestTotalView.text = item.investedTotal.amount.toPlainString()
            mInvestPeriodView.text = item.investedPeriod.amount.toPlainString()
            mNetGainTotalView.text = item.netGainTotal.amount.toPlainString()
            mNetGainPeriodView.text = item.netGainPeriod.amount.toPlainString()
            mAvailableView.text = item.available.amount.toPlainString()
        }

        override fun onClick(v: View) {
            Log.d(this.javaClass.toString(), "$v clicked")
        }
    }
}