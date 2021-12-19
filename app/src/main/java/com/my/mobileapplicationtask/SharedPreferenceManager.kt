package com.my.mobileapplicationtask

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceManager(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val User_ID = "user_id"
        const val Email_id = "user@gmail.com"
        const val USER_TOKEN = "user_token"
        const val Member_since = "0000000000"
    }

    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String? = null) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun saveUserID(ID: String? = null) {
        val editor = prefs.edit()
        editor.putString(User_ID, ID)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {

        return prefs.getString(USER_TOKEN, null)
    }

    fun FetchUserID(): String? {
        return prefs.getString(User_ID, null)
    }

    fun saveEmail(email: String? = null) {
        val editor = prefs.edit()
        editor.putString(Email_id, email)
        editor.apply()
    }

    fun fetchEmail(): String? {

        return prefs.getString(Email_id, null)
    }

    /*fun Long.toDateString(memberSince: Int =  DateFormat.MEDIUM): String {
        val df = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
        return df.format(this)
    }*/
    fun saveMember(memberSince: Long) {
        val editor = prefs.edit()
        editor.putLong(Member_since, memberSince)
        editor.apply()
    }

    fun fetchMember(): Long {

        return prefs.getLong(Member_since, 8767887777)
    }
}