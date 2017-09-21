package br.com.montecardo.finanseer.investment

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.montecardo.finanseer.R
import br.com.montecardo.finanseer.investment.data.Investment
import br.com.montecardo.finanseer.persistence.SingletonDAO

/**
 * A fragment representing a list of DataPoints.
 */
class MovementFragment : Fragment() {

    private lateinit var adapter: MovementViewAdapter

    private lateinit var investment: Investment

    init {
        arguments = Bundle()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): android.view.View? {
        return createView(inflater, container)
    }

    override fun onStart() {
        super.onStart()

        arguments.getString(INVESTMENT_KEY)?.let { invId ->
            SingletonDAO.getInvestment(invId)?.let { inv ->
                investment = inv
                refreshList()
                return
            }
        }

        Snackbar.make(view!!, "Ocorreu um erro carregando o investimento desejado.",
                Snackbar.LENGTH_SHORT)
        Log.d(javaClass.simpleName, "No investmentId or invalid investmentId passed.")
    }

    private fun createView(inflater: LayoutInflater, container: ViewGroup?): RecyclerView {
        val recycler = inflater.inflate(R.layout.movement_list, container, false) as RecyclerView
        recycler.layoutManager = LinearLayoutManager(recycler.context)

        adapter = MovementViewAdapter()
        recycler.adapter = adapter

        return recycler
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(INVESTMENT_KEY, investment.id)
    }

    fun refreshList() {
        adapter.refresh(SingletonDAO.getMovementsByInvestment(invId = investment.id))
    }

    companion object {
        val INVESTMENT_KEY = "investmentId-id"
    }
}