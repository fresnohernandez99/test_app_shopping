package com.test.login.google

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.test.login.models.UserDataModel


class GoogleLoginData(val context: Context) {
    private lateinit var gso: GoogleSignInOptions

    fun isLoggedIn() =
        GoogleSignIn.getLastSignedInAccount(context) != null && !GoogleSignIn.getLastSignedInAccount(
            context
        )!!.isExpired

    fun build() {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
    }

    fun googleClient() = GoogleSignIn.getClient(context, gso)

    fun getData(): UserDataModel? {
        val acct = GoogleSignIn.getLastSignedInAccount(context)
        if (acct != null) {
            Log.i(TAG_GOOGLE_LOGIN, "retrieving data")
            return UserDataModel(
                acct.displayName,
                acct.givenName,
                acct.familyName,
                acct.email,
                acct.id,
                acct.photoUrl
            )
        }
        return null
    }

    companion object {
        const val TAG_GOOGLE_LOGIN = "google-login"
    }

}