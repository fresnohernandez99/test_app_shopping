package com.test.login.models

import android.net.Uri

class UserDataModel(
    val personName: String?,
    val personGivenName: String?,
    val personFamilyName: String?,
    val personEmail: String?,
    val personId: String?,
    var photoUrl: Uri?
)