package com.test.login.facebook

import android.app.Application
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.facebook.AccessToken
import com.facebook.FacebookSdk
import com.facebook.GraphRequest
import com.facebook.Profile
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.test.login.models.UserDataModel


class FacebookLoginData {
    fun isLoggedIn() =
        AccessToken.getCurrentAccessToken() != null && !AccessToken.getCurrentAccessToken()!!.isExpired

    fun loginManager() = LoginManager.getInstance()

    fun startFacebookSDK(application: Application) {
        FacebookSdk.fullyInitialize()
        AppEventsLogger.activateApp(application)
    }

    fun getData(): UserDataModel? {
        val acct = Profile.getCurrentProfile()
        if (acct != null) {
            Log.i(TAG_FACEBOOK_LOGIN, "retrieving data")
            return UserDataModel(
                acct.firstName + " " + acct.middleName + " " + acct.lastName,
                acct.name,
                acct.lastName,
                "www.facebook.com/" + acct.id,
                acct.id,
                Uri.parse("https://graph.facebook.com/" + acct.id + "/picture??type=small")
            )
        }
        return null
    }

    fun getProfilePic(callback: GraphRequest.GraphJSONObjectCallback) {
        val request = GraphRequest.newMeRequest(
            AccessToken.getCurrentAccessToken(), callback
        )
        val parameters = Bundle()
        parameters.putString("fields", "id, picture")
        request.parameters = parameters
        request.executeAsync()
    }

    companion object {
        const val TAG_FACEBOOK_LOGIN = "facebook-login"
    }
}