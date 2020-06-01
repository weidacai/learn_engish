package com.example.learn.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SPHelper {

    private static SPHelper instance;
    private Context context;

    private SPHelper(Context context) {
        this.context = context;
    }

    private SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public synchronized static SPHelper getInstance(Context context) {
        if (instance == null)
            instance = new SPHelper(context);
        return instance;
    }

    private SharedPreferences.Editor getEditor() {
        return getSharedPreferences().edit();
    }

    public boolean saveUserInfo(String username, String password) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putString("username", username);
        editor.putString("password", password);
        return editor.commit();
    }


    public String[] getUserInfo() {
        return new String[]{getSharedPreferences().getString("username", ""), getSharedPreferences().getString("password", "")};
    }

    public boolean setRemember(int remember) {
        android.content.SharedPreferences.Editor editor = getEditor();
        editor.putInt("remember", remember);
        return editor.commit();
    }

    public int getRemember() {
        return getSharedPreferences().getInt("remember", 0);
    }

}
