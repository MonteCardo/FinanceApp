package br.com.montecardo.finanseer.investment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import br.com.montecardo.finanseer.R
import br.com.montecardo.finanseer.investment.data.Investment
import br.com.montecardo.finanseer.investment.data.SimpleInvestment
import kotlinx.android.synthetic.main.dialog_investment.view.*

/**
 * Fragment used to represent a component that can add movements
 *
 * Created by gabryel on 02/06/17.
 */
class InvestmentDialogFragment : DialogFragment() {
    interface InvestmentDialogListener {
        fun onDialogPositiveClick(inv: Investment)
        fun onDialogNegativeClick()
    }

    private lateinit var invAddListener: InvestmentDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity.layoutInflater.inflate(R.layout.dialog_investment, null)

        return AlertDialog.Builder(activity).setView(view)
                .setNegativeButton("Cancelar", { _, _ -> invAddListener.onDialogNegativeClick() })
                .setPositiveButton("OK", { _, _ ->
                    val name = view.inv_dlg_name.text.toString()
                    invAddListener.onDialogPositiveClick(SimpleInvestment(name = name))
                })
                .create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        invAddListener = try {
            activity as InvestmentDialogListener
        } catch (ex: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement InvestmentDialogListener")
        }
    }
}