package br.com.montecardo.finanseer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import br.com.montecardo.finanseer.investment.InvestmentDialogFragment
import br.com.montecardo.finanseer.investment.InvestmentFragment
import br.com.montecardo.finanseer.investment.data.Investment
import br.com.montecardo.finanseer.persistence.SingletonDAO
import kotlinx.android.synthetic.main.activity_investment.*

class InvestmentActivity : AppCompatActivity(), InvestmentDialogFragment.InvestmentDialogListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_investment)

        inv_act_fab_add.setOnClickListener { _ ->
            InvestmentDialogFragment().show(supportFragmentManager, InvestmentDialogFragment::javaClass.name)
        }
    }

    override fun onDialogPositiveClick(investment: Investment) {
        SingletonDAO.persist(investment)

        val investmentList = inv_act_list
        if (investmentList is InvestmentFragment) {
            investmentList.refreshList()
        }
    }

    override fun onDialogNegativeClick() {
        // TODO FOR TESTING PURPOSES
        UserConfiguration.signOut()
        Toast.makeText(application, "SURPRISE LOGOUT!", Toast.LENGTH_SHORT).show()
        finish()
    }
}
