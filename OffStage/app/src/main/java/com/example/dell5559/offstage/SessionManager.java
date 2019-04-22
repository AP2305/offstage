package com.example.dell5559.offstage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SessionManager {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "offstage";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";


    // User name (make variable public to access from outside)
    public static final String KEY_SID = "student_id";
    public static final String KEY_UNAME = "user_name";
    public static final String KEY_PWD = "password";
    public static final String KEY_ROLE = "role";
    public static final String KEY_EMAIL = "email_id";
    public static final String KEY_CONTACT_NO = "contact_no";
    public static final String KEY_EVENT_ID = "event_id";


    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void createSession(String uid,String uname,String password,String role,String event_id,String email,String contact){
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_SID,uid);
        editor.putString(KEY_UNAME,uname);
        editor.putString(KEY_PWD,password);
        editor.putString(KEY_ROLE,role);
        editor.putString(KEY_EVENT_ID,event_id);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_CONTACT_NO,contact);
        editor.putBoolean(IS_LOGIN,true);

        editor.commit();
    }

    public boolean isLoggedin(){

        return pref.getBoolean(IS_LOGIN, false);

    }

    public String getKeySid() {
        return pref.getString(KEY_SID,null);
    }

    public String getKeyEventId() {
        return pref.getString(KEY_EVENT_ID,null);
    }

    public String getKeyUname() {
        return pref.getString(KEY_UNAME,null);
    }

    public String getKeyPwd() {
        return pref.getString(KEY_PWD,null);
    }

    public String getKeyRole() {
        return pref.getString(KEY_ROLE,null);
    }

    public String getKeyEmail() {
        return pref.getString(KEY_EMAIL,null);
    }

    public String getKeyContactNo() {
        return pref.getString(KEY_CONTACT_NO,null);
    }


    public void logoutUser(){
        // Clearing all data from Shared Preferences

        pref.edit().clear().commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }

}


