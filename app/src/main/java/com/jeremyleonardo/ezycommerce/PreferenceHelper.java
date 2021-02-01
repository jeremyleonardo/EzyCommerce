package com.jeremyleonardo.ezycommerce;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;

import java.util.ArrayList;

public class PreferenceHelper {

    public static void setDoneInitDatabase(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("init", true);
        editor.apply();
    }

    public static boolean checkDatabaseInit(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), context.MODE_PRIVATE);
        boolean init = sharedPref.getBoolean("init", false);
        return init;
    }

}
