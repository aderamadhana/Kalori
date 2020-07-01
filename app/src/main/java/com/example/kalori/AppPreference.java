package com.example.kalori;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.kalori.ui.home.HomeFragment;

public class AppPreference {
    static final String PREF = "PREF";
    static final String JK_PREF = "JK_PREF";
    static final String USIA_PREF = "USIA_PREF";
    static final String BERAT_PREF = "BERAT_PREF";
    static final String TINGGI_PREF = "TINGGI_PREF";

    public static void saveJK(Context context, String user){
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
                .edit().putString(JK_PREF, user).apply();
    }

    public static String getJK(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        if(pref.contains(JK_PREF)){
            return pref.getString(JK_PREF, null);
        }
        return null;
    }

    public static void removeJK(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        if(pref.contains(JK_PREF)){
            pref.edit().remove(JK_PREF).apply();
        }
    }

    public static void saveUSIA(Context context, String user){
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
                .edit().putString(USIA_PREF, user).apply();
    }

    public static String getUSIA(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        if(pref.contains(USIA_PREF)){
            return pref.getString(USIA_PREF, null);
        }
        return null;
    }

    public static void removeUSIA(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        if(pref.contains(USIA_PREF)){
            pref.edit().remove(USIA_PREF).apply();
        }
    }

    public static void saveBERAT(Context context, String user){
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
                .edit().putString(BERAT_PREF, user).apply();
    }

    public static String getBERAT(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        if(pref.contains(BERAT_PREF)){
            return pref.getString(BERAT_PREF, null);
        }
        return null;
    }

    public static void removeBERAT(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        if(pref.contains(BERAT_PREF)){
            pref.edit().remove(BERAT_PREF).apply();
        }
    }

    public static void saveTINGGI(Context context, String user){
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
                .edit().putString(TINGGI_PREF, user).apply();
    }

    public static String getTINGGI(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        if(pref.contains(TINGGI_PREF)){
            return pref.getString(TINGGI_PREF, null);
        }
        return null;
    }

    public static void removeTINGGI(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        if(pref.contains(TINGGI_PREF)){
            pref.edit().remove(TINGGI_PREF).apply();
        }
    }
}
