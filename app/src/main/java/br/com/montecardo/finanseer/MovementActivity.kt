package br.com.montecardo.finanseer

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import br.com.montecardo.finanseer.investment.MovementDialogFragment
import br.com.montecardo.finanseer.investment.MovementFragment
import br.com.montecardo.finanseer.investment.data.Movement
import br.com.montecardo.finanseer.persistence.SingletonDAO
import kotlinx.android.synthetic.main.activity_movement.*

class MovementActivity : AppCompatActivity(), MovementDialogFragment.MovementDialogListener {

    private lateinit var movementFragment: MovementFragment

    private var investmentId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movement)
        investmentId = intent?.extras?.getString(INVESTMENT_KEY)

        mov_act_fab_add.setOnClickListener { _ ->
            MovementDialogFragment.getInstance(investmentId)
                    .show(supportFragmentManager, MovementDialogFragment::class.simpleName)
        }

        manageFragment()
    }

    private fun manageFragment() {
        val tag = MovementFragment::class.simpleName
        val fragment = supportFragmentManager.findFragmentByTag(tag)
        when (fragment) {
            is MovementFragment -> movementFragment = fragment
            null -> {
                movementFragment = MovementFragment()
                supportFragmentManager.beginTransaction()
                        .add(R.id.mov_act_list, movementFragment, tag).commit()
            }
            else -> {
                Snackbar.make(mov_act_root, "Ocorreu um erro carregando carregando a tela.",
                        Snackbar.LENGTH_SHORT)
                Log.d(javaClass.simpleName, "O fragmento falhou em ser carregado.")
                return
            }
        }

        movementFragment.arguments.putString(MovementFragment.INVESTMENT_KEY, investmentId)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        investmentId = savedInstanceState.getString(INVESTMENT_KEY)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(INVESTMENT_KEY, investmentId)
    }

    override fun onDialogPositiveClick(mov: Movement) {
        SingletonDAO.persist(mov)
        movementFragment.refreshList()
    }

    override fun onDialogNegativeClick() {}

    companion object {
        val INVESTMENT_KEY = "investment-key"
    }
}
