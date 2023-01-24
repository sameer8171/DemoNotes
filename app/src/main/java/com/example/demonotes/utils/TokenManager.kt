package com.example.demonotes.utils

import android.content.Context
import com.example.demonotes.utils.Constants.PREF_TOKEN_FILE
import com.example.demonotes.utils.Constants.TOKEN_KEY
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context : Context){
    private var prefs = context.getSharedPreferences(PREF_TOKEN_FILE,Context.MODE_PRIVATE)

    fun saveToken(token:String){
        val editor = prefs.edit()
        editor.putString(TOKEN_KEY,token)
        editor.apply()
    }

    fun getToken(): String? {
        return prefs.getString(TOKEN_KEY,null)
    }
}