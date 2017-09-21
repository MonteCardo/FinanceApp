package br.com.montecardo.finanseer.investment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.util.Log
import br.com.montecardo.finanseer.R
import br.com.montecardo.finanseer.investment.data.Movement
import br.com.montecardo.finanseer.persistence.SingletonDAO
import kotlinx.android.synthetic.main.dialog_movement.view.*
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.threeten.bp.LocalDate

/**
 * Fragment used to represent a component that can add movements
 *
 * Created by gabryel on 02/06/17.
 */
class MovementDialogFragment : DialogFragment() {
    interface MovementDialogListener {
        fun onDialogPositiveClick(mov: Movement)
        fun onDialogNegativeClick()
    }

    private lateinit var movAddListener: MovementDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity.layoutInflater.inflate(R.layout.dialog_movement, null)
        val builder = AlertDialog.Builder(activity).setView(view)

        val invId = arguments?.getString(MovementDialogFragment.INVESTMENT_KEY) ?: ""
        val investment = SingletonDAO.getInvestment(invId)

        if (investment != null) {
            builder.setNegativeButton("Cancelar", { _, _ -> movAddListener.onDialogNegativeClick() })
                    .setPositiveButton("OK", { _, _ ->

                        val name = view.mov_dlg_name.text.toString()
                        val value = Money.of(CurrencyUnit.USD, view.mov_dlg_quantity.text.toString().toDouble())

                        val mov = Movement(invId, name = name, date = LocalDate.now(), money = value)
                        movAddListener.onDialogPositiveClick(mov)
                    })
        } else {
            Snackbar.make(view, "Ocorreu um erro carregando o investimento desejado.",
                    Snackbar.LENGTH_SHORT)
            Log.d(javaClass.simpleName, "No investmentId or invalid investmentId passed.")
            activity.onBackPressed()
        }

        return builder.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        movAddListener = try {
            activity as MovementDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement MovementDialogListener")
        }
    }

    companion object {
        private val INVESTMENT_KEY = "investment-id"

        fun getInstance(investmentId: String?): MovementDialogFragment {
            val fragment = MovementDialogFragment()
            fragment.arguments = Bundle()
            fragment.arguments.putString(INVESTMENT_KEY, investmentId)

            return fragment
        }
    }
}