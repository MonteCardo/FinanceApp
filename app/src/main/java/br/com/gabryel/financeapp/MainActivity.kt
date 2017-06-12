package br.com.gabryel.financeapp

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import br.com.gabryel.financeapp.investment.DataPointFragment
import br.com.gabryel.financeapp.investment.MovementDialogFragment
import br.com.gabryel.financeapp.investment.data.State
import butterknife.BindView
import butterknife.ButterKnife

class MainActivity : AppCompatActivity(), MovementDialogFragment.MovementDialogListener {

    @BindView(R.id.add_fab) lateinit var mAddFab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        mAddFab.setOnClickListener { _ ->
            MovementDialogFragment().show(supportFragmentManager, MovementDialogFragment::javaClass.name)
        }
    }

    override fun onDialogPositiveClick(state: State) {
        val investmentList = supportFragmentManager.findFragmentById(R.id.investment_list) as DataPointFragment
        investmentList.add(state)
    }

    override fun onDialogNegativeClick() {
        // TODO FOR TESTING PURPOSES
        UserConfiguration.signOut()
        Toast.makeText(application, "SURPRISE LOGOUT!", Toast.LENGTH_SHORT).show()
        finish()
    }
}
