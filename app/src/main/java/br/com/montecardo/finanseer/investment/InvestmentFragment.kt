package br.com.montecardo.finanseer.investment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.montecardo.finanseer.MovementActivity
import br.com.montecardo.finanseer.R
import br.com.montecardo.finanseer.investment.data.Investment
import br.com.montecardo.finanseer.persistence.SingletonDAO

/**
 * A fragment representing a list of DataPoints.
 */
class InvestmentFragment : Fragment() {

    private lateinit var recycler: RecyclerView

    private lateinit var adapter: StateViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): android.view.View {
        adapter = StateViewAdapter(this::onInvestmentSelection, SingletonDAO.getAllInvestments())
        recycler = inflater.inflate(R.layout.investment_list, container, false) as RecyclerView
        recycler.layoutManager = LinearLayoutManager(recycler.context)
        recycler.adapter = adapter

        return recycler
    }

    fun refreshList() {
        adapter.refresh(SingletonDAO.getAllInvestments())
    }

    private fun onInvestmentSelection(inv: Investment) {
        val intent = Intent(context, MovementActivity::class.java)
        intent.putExtra(MovementActivity.INVESTMENT_KEY, inv.id)
        startActivity(intent)
    }
}