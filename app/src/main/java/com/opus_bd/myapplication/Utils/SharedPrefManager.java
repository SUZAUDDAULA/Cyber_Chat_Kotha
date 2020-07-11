package com.opus_bd.myapplication.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.opus_bd.myapplication.Model.User.UserModel;

public class SharedPrefManager {
    public static final String LOGGED_IN_PREF = "logged_in_status";
    private static final String SHARED_PREF_NAME = "MessageBOx";
    private static final String KEY_CARTS = "cart";
    private static final String KEY_USERNAME = "UserName";
    private static final String KEY_USERID = "UserID";
    private static final String KEY_USERMODEL = "UserModel";
    private static final String KEY_EMPLOYEEMODEL = "KEY_EMPLOYEE MODEL";
    private static final String KEY_TOKEN = "User Model";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static SharedPrefManager mInstance;
    private Context mCtx;

    public SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void saveUserName(String UserName) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, UserName);
        editor.apply();

    }

    public String getUserName() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public void saveUserModel(UserModel userModel) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userModel);
        editor.putString(KEY_USERMODEL, json);
        editor.apply();
    }

    public String getUserModel() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERMODEL, null);
    }

    public void saveUserId(String userId) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERID, userId);
        editor.apply();
    }

    public String getUserID() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERID, null);
    }

    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

  /*  // Clear everything
    public void clearAll() {

        clearOrder();
    }*/

    // Save TimeStamp
    public void saveTimeStamp(String timestamp) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TIMESTAMP, timestamp);
        editor.apply();
    }

    public String getTimeStamp() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_TIMESTAMP, "");
    }


    public void setLoggedIn(boolean loggedIn) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        editor.apply();
    }

    public void clearLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(LOGGED_IN_PREF).apply();


    }

    /**
     * Get the Login Status
     *
     * @param context
     * @return boolean: login status
     */
    public boolean getLoggedStatus(Context context) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(LOGGED_IN_PREF, false);
    }
}