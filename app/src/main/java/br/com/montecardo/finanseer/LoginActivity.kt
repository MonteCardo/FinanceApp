package br.com.montecardo.finanseer

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {

    private val TAG = LoginActivity::class.simpleName

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (UserConfiguration.loggedIn) {
            Toast.makeText(this.application, "You are already logged in :D", Toast.LENGTH_SHORT).show()
            startMainActivity()
        }

        setContentView(R.layout.activity_login)
        login_act_pass.setOnEditorActionListener { _, _, _ ->
            attemptLogin()
            true
        }

        login_act_sign_in.setOnClickListener { attemptLogin() }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private fun attemptLogin() {
        login_act_email.error = null
        login_act_pass.error = null

        val mail = login_act_email.text.toString()
        val pass = login_act_pass.text.toString()

        var focusView: View? = null

        if (TextUtils.isEmpty(pass)) {
            login_act_pass.error = getString(R.string.error_field_required)
            focusView = login_act_pass
        }

        if (TextUtils.isEmpty(mail)) {
            login_act_email.error = getString(R.string.error_field_required)
            focusView = login_act_email
        }

        focusView?.let {
            it.requestFocus()
            return
        }

        showProgress(true)

        auth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(this, { task ->
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

        login_act_form.visibility = if (show) View.GONE else View.VISIBLE
        login_act_form.animate()
                .setDuration(shortAnimTime.toLong())
                .alpha(alpha)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        login_act_form.visibility = if (show) View.GONE else View.VISIBLE
                    }
                })

        login_act_progress.visibility = if (show) View.VISIBLE else View.GONE
        login_act_progress.animate()
                .setDuration(shortAnimTime.toLong())
                .alpha(1 - alpha)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        login_act_progress.visibility = if (show) View.VISIBLE else View.GONE
                    }
                })
    }

    private fun startMainActivity() {
        startActivity(Intent(this, InvestmentActivity::class.java))
    }
}

