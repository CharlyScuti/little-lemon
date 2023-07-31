package com.tr3sco.littlelemon.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

private const val PREFERENCES_NAME = "UserData"
private const val FIRST_NAME = "firstName"
private const val LAST_NAME = "lastName"
private const val EMAIL = "email"

data class UserData(
    val firstName: String,
    val lastName: String,
    val email: String
)

private val Context.sharedPreferences: SharedPreferences
    get() = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

val Context.hasUserData: Boolean
    get() = userData != null

val Context.userData: UserData?
    get() {
        val preferences = sharedPreferences
        val firstName = preferences.getString(FIRST_NAME, null) ?: return null
        val lastName = preferences.getString(LAST_NAME, null) ?: return null
        val email = preferences.getString(EMAIL, null) ?: return null
        return UserData(firstName, lastName, email)
    }

fun Context.saveUserData(userData: UserData) {
    sharedPreferences.edit {
        putString(FIRST_NAME, userData.firstName)
        putString(LAST_NAME, userData.lastName)
        putString(EMAIL, userData.email)
    }
}

fun Context.clearUserData() {
    sharedPreferences.edit {
        remove(FIRST_NAME)
        remove(LAST_NAME)
        remove(EMAIL)
    }
}