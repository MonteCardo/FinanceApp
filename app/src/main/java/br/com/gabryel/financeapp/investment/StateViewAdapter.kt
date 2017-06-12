package br.com.gabryel.financeapp.investment

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.gabryel.financeapp.R
import br.com.gabryel.financeapp.investment.data.State
import butterknife.BindView
import butterknife.ButterKnife

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

        @BindView(R.id.identification) lateinit var mIdView: TextView
        @BindView(R.id.available) lateinit var mAvailableView: TextView

        @BindView(R.id.investedTotal) lateinit var mInvestTotalView: TextView
        @BindView(R.id.investedPeriod) lateinit var mInvestPeriodView: TextView

        @BindView(R.id.netGainTotal) lateinit var mNetGainTotalView: TextView
        @BindView(R.id.netGainPeriod) lateinit var mNetGainPeriodView: TextView

        var mItem: State? = null

        init {
            ButterKnife.bind(this, mView)
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
            Log.d(javaClass.toString(), "$v clicked")
        }
    }
}