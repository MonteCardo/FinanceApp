package br.com.gabryel.financeapp.investment

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.widget.EditText
import br.com.gabryel.financeapp.R
import br.com.gabryel.financeapp.investment.data.DummyState
import br.com.gabryel.financeapp.investment.data.State
import butterknife.BindView
import butterknife.ButterKnife
import org.joda.money.CurrencyUnit
import org.joda.money.Money

/**
 * Fragment used to represent a component that can add movements
 *
 * Created by gabryel on 02/06/17.
 */
class MovementDialogFragment : DialogFragment() {
	interface MovementDialogListener {
		fun onDialogPositiveClick(state: State)
		fun onDialogNegativeClick()
	}

	private lateinit var mListener: MovementDialogListener

	@BindView(R.id.quantity) lateinit var mValue: EditText
	@BindView(R.id.name) lateinit var mName: EditText

	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		val view = activity.layoutInflater.inflate(R.layout.dialog_movement, null)
		ButterKnife.bind(this, view)

		return AlertDialog.Builder(activity).setView(view)
				.setNegativeButton("Cancelar", { _, _ -> mListener.onDialogNegativeClick() })
				.setPositiveButton("OK", { _, _ ->

					val name = mName.text.toString()
					val value = Money.of(CurrencyUnit.USD, mValue.text.toString().toDouble())

					StateGenerator(name, value)
							.forEach { mListener.onDialogPositiveClick(it) }
				})
				.create()
	}

	override fun onAttach(context: android.content.Context?) {
		super.onAttach(context)

		try {
			mListener = activity as MovementDialogListener
		} catch (e: ClassCastException) {
			throw ClassCastException(activity.toString() + " must implement MovementDialogListener")
		}
	}

	private class StateGenerator(original: String, money: Money) : Iterable<State> {

		val states = listOf(original,
				"You'll never see it coming",
				"You'll see that ",
				"My mind is too fast for eyes",
				"You're done in",
				"By the time it's hit you",
				"Your last surprise"
		).map { DummyState(it, money) }

		override fun iterator() = states.iterator()
	}
}