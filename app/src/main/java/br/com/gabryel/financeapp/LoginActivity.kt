package br.com.gabryel.financeapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.google.firebase.auth.FirebaseAuth

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {

	private val TAG = LoginActivity::class.simpleName

	private val mAuth = FirebaseAuth.getInstance()

	@BindView(R.id.email) lateinit var mEmailView: AutoCompleteTextView
	@BindView(R.id.password) lateinit var mPasswordView: EditText

	@BindView(R.id.login_progress) lateinit var mProgressView: View
	@BindView(R.id.login_form) lateinit var mLoginFormView: View

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		if (UserConfiguration.loggedIn) {
			Toast.makeText(this.application, "You are already logged in :D", Toast.LENGTH_SHORT).show()
			startMainActivity()
		}

		setContentView(R.layout.activity_login)

		ButterKnife.bind(this)
		mPasswordView.setOnEditorActionListener {
			_, _, _ ->
			attemptLogin()
			true
		}

		findViewById(R.id.email_sign_in_button).setOnClickListener { attemptLogin() }
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	private fun attemptLogin() {
		mEmailView.error = null
		mPasswordView.error = null

		val email = mEmailView.text.toString()
		val password = mPasswordView.text.toString()

		var focusView: View? = null

		if (TextUtils.isEmpty(password)) {
			mPasswordView.error = getString(R.string.error_field_required)
			focusView = mPasswordView
		}

		if (TextUtils.isEmpty(email)) {
			mEmailView.error = getString(R.string.error_field_required)
			focusView = mEmailView
		}

		focusView?.let {
			it.requestFocus()
			return
		}

		showProgress(true)

		mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, {
			task ->
			Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful)
			showProgress(false)

			if (task.isSuccessful) {
				startMainActivity()
			} else {
				Log.w(TAG, "signInWithEmail:failed", task.exception)
			}
		})
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	private fun showProgress(show: Boolean) {
		val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime)
		val alpha = if (show) 0f else 1f

		mLoginFormView.visibility = if (show) View.GONE else View.VISIBLE
		mLoginFormView.animate()
				.setDuration(shortAnimTime.toLong())
				.alpha(alpha)
				.setListener(object : AnimatorListenerAdapter() {
					override fun onAnimationEnd(animation: Animator) {
						mLoginFormView.visibility = if (show) View.GONE else View.VISIBLE
					}
				})

		mProgressView.visibility = if (show) View.VISIBLE else View.GONE
		mProgressView.animate()
				.setDuration(shortAnimTime.toLong())
				.alpha(1 - alpha)
				.setListener(object : AnimatorListenerAdapter() {
					override fun onAnimationEnd(animation: Animator) {
						mProgressView.visibility = if (show) View.VISIBLE else View.GONE
					}
				})
	}

	private fun startMainActivity() {
		startActivity(Intent(this, MainActivity::class.java))
	}
}

