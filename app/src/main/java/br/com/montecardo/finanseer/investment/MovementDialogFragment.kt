package br.com.montecardo.finanseer.investment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.util.Log
import android.view.View
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

        if (investment == null) {
            Snackbar.make(view, "Ocorreu um erro carregando o investimento desejado.",
                    Snackbar.LENGTH_SHORT)
            Log.d(javaClass.simpleName, "No investmentId or invalid investmentId passed.")
            activity.onBackPressed()
            return builder.create()
        }

        return generateButton(builder, view, invId)
    }

    private fun generateButton(builder: AlertDialog.Builder, view: View, invId: String): AlertDialog {
        val dialog = builder.setNegativeButton("Cancelar",
                { _, _ -> movAddListener.onDialogNegativeClick() })
                .setPositiveButton("OK", null)
                .create()

        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener({ _: View ->
            val nameDlg = view.mov_dlg_name
            val qtDlg = view.mov_dlg_quantity
            nameDlg.error = null
            qtDlg.error = null

            val name = nameDlg.text.toString()
            val quantityText = qtDlg.text.toString()
            val quantity = quantityText.toDoubleOrNull()

            val fieldError = getString(R.string.error_field_required)
            var focusView: View? = null
            if (TextUtils.isEmpty(name)) {
                nameDlg.error = fieldError
                focusView = nameDlg
            }

            if (TextUtils.isEmpty(quantityText) || quantity == null) {
                qtDlg.error = fieldError
                focusView = qtDlg
            }

            focusView?.let {
                it.requestFocus()
                return@setOnClickListener
            }

            val value = Money.of(CurrencyUnit.USD, quantity ?: 0.0)
            val mov = Movement(invId, name = name, date = LocalDate.now(), money = value)
            movAddListener.onDialogPositiveClick(mov)
            dialog.dismiss()
        })

        return dialog
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