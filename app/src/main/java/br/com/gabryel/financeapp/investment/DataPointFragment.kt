package br.com.gabryel.financeapp.investment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.gabryel.financeapp.R
import br.com.gabryel.financeapp.investment.data.DummyState
import br.com.gabryel.financeapp.investment.data.State
import org.joda.money.CurrencyUnit
import org.joda.money.Money

/**
 * A fragment representing a list of DataPoints.
 */
class DataPointFragment : Fragment() {
	companion object DummyContent {
		val ITEMS = listOf(
				DummyState("Poupança",
						investedTotal = Money.of(CurrencyUnit.USD, 32.19),
						netGainTotal = Money.of(CurrencyUnit.USD, 0.19)),
				DummyState("Conta Bancária",
						investedTotal = Money.of(CurrencyUnit.USD, 0.04),
						netGainTotal = Money.of(CurrencyUnit.USD, 0.01)),
				DummyState("CDB",
						investedTotal = Money.of(CurrencyUnit.USD, 0.00),
						netGainTotal = Money.of(CurrencyUnit.USD, 0.00)),
				DummyState("Colchão",
						investedTotal = Money.of(CurrencyUnit.USD, 55.04),
						netGainTotal = Money.of(CurrencyUnit.USD, 5.01)),
				DummyState("Lua",
						investedTotal = Money.of(CurrencyUnit.USD, 15.04),
						netGainTotal = Money.of(CurrencyUnit.USD, 115.19))
		)
	}

	private val mItems = mutableListOf<State>()

	private lateinit var mView: RecyclerView

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
	                          savedInstanceState: Bundle?): android.view.View? {
		mView = inflater.inflate(R.layout.fragment_datapoint_list, container, false) as RecyclerView
		mItems.addAll(DataPointFragment.DummyContent.ITEMS)

		mView.layoutManager = LinearLayoutManager(mView.context)
		mView.adapter = StateViewAdapter(mItems)

		return mView
	}

	fun add(state: State) {
		mItems.add(state)
		mView.adapter.notifyItemInserted(mItems.size - 1)
	}
}