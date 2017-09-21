package br.com.montecardo.finanseer

import com.google.firebase.auth.FirebaseAuth

/**
 * Temporary singleton that takes care of user configuration, trying to make it independent of
 * implementation of auth
 *
 * Created by gabryel on 11/06/17.
 */
object UserConfiguration {

	var loggedIn = FirebaseAuth.getInstance().currentUser != null

	fun signOut() {
		FirebaseAuth.getInstance().signOut()
	}
}