package br.com.gabryel.financeapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.gabryel.financeapp.investment.DummyState

import br.com.gabryel.financeapp.investment.State
import org.joda.money.CurrencyUnit
import org.joda.money.Money

/**
 * A fragment representing a list of DataPoints.
 */
class DataPointFragment : Fragment() {
    companion object DummyContent {
        val ITEMS = mutableListOf<State>()

        init {
            ITEMS.add(DummyState("Poupança",
                    investedTotal = Money.of(CurrencyUnit.USD, 32.19),
                    netGainTotal = Money.of(CurrencyUnit.USD, 0.19)))
            ITEMS.add(DummyState("Conta Bancária",
                    investedTotal = Money.of(CurrencyUnit.USD, 0.04),
                    netGainTotal = Money.of(CurrencyUnit.USD, 0.01)))
            ITEMS.add(DummyState("CDB",
                    investedTotal = Money.of(CurrencyUnit.USD, 0.00),
                    netGainTotal = Money.of(CurrencyUnit.USD, 0.00)))
            ITEMS.add(DummyState("Colchão",
                    investedTotal = Money.of(CurrencyUnit.USD, 55.04),
                    netGainTotal = Money.of(CurrencyUnit.USD, 5.01)))
            ITEMS.add(DummyState("Lua",
                    investedTotal = Money.of(CurrencyUnit.USD, 15.04),
                    netGainTotal = Money.of(CurrencyUnit.USD, 115.19)))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_datapoint_list, container, false)

        if (view is RecyclerView) {
            view.layoutManager = LinearLayoutManager(view.getContext())
            view.adapter = StateViewAdapter(DummyContent.ITEMS)
        }

        return view
    }
}