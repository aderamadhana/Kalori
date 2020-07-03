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
    static final String LatAwal = "LatAwal";
    static final String LongAwal = "LongAwal";
    static final String LatAkhir = "LatAkhir";
    static final String LongAkhir = "LongAkhir";

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

    public static void saveLatAwal(Context context, String user){
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
                .edit().putString(LatAwal, user).apply();
    }

    public static String getLatAwal(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        if(pref.contains(LatAwal)){
            return pref.getString(LatAwal, null);
        }
        return null;
    }

    public static void removeLatAwal(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        if(pref.contains(LatAwal)){
            pref.edit().remove(LatAwal).apply();
        }
    }

    public static void saveLongAwal(Context context, String user){
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
                .edit().putString(LongAwal, user).apply();
    }

    public static String getLongAwal(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        if(pref.contains(LongAwal)){
            return pref.getString(LongAwal, null);
        }
        return null;
    }

    public static void removeLongAwal(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        if(pref.contains(LongAwal)){
            pref.edit().remove(LongAwal).apply();
        }
    }

    public static void saveLatAkhir(Context context, String user){
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
                .edit().putString(LatAkhir, user).apply();
    }

    public static String getLatAkhir(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        if(pref.contains(LatAkhir)){
            return pref.getString(LatAkhir, null);
        }
        return null;
    }

    public static void removeLatAkhir(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        if(pref.contains(LatAkhir)){
            pref.edit().remove(LatAkhir).apply();
        }
    }

    public static void saveLongAkhir(Context context, String user){
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
                .edit().putString(LongAkhir, user).apply();
    }

    public static String getLongAkhir(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        if(pref.contains(LongAkhir)){
            return pref.getString(LongAkhir, null);
        }
        return null;
    }

    public static void removeLongAkhir(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        if(pref.contains(LongAkhir)){
            pref.edit().remove(LongAkhir).apply();
        }
    }
}
