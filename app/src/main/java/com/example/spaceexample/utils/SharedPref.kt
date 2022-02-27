package com.example.spaceexample.utils

import android.content.Context
import kotlin.system.exitProcess

open class SharedPref(contextParam: Context) {
    private var context: Context = contextParam


    private fun setSharedPreference(name: String, key: String?, value: String?) {
        val sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun setUserCreateSpaceShip(value: String) {
        setSharedPreference("sign", "userIsCreateShip", value)

    }

    fun getUserCreateSpaceShip(): String? {
        val sharedPref = context.getSharedPreferences("sign", Context.MODE_PRIVATE)
        return sharedPref.getString("userIsCreateShip", null)
    }

    fun clearUserPreference() {
        val preference = context.getSharedPreferences("sign", Context.MODE_PRIVATE)
        preference.edit().remove("userId").apply()
        preference.edit().remove("username").apply()
        preference.edit().remove("userIsCreateShip").apply()

    }

    fun exitApp() {
        clearUserPreference()
        exitProcess(1)
    }
}