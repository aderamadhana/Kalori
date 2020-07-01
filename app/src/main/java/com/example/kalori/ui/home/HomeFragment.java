package com.example.kalori.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.kalori.AppPreference;
import com.example.kalori.R;

public class HomeFragment extends Fragment {
    private TextView textViewGoals;
    private String KEY_USIA = "USIA";
    private String KEY_TINGGI = "TINGGI";
    private String KEY_BERAT = "BERAT";
    private String KEY_JK = "JK";
    private String usia, tinggi, berat, jk;
    private double hitung;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        textViewGoals = root.findViewById(R.id.txtGoals);
        usia = AppPreference.getUSIA(getContext());
        tinggi = AppPreference.getTINGGI(getContext());
        berat = AppPreference.getBERAT(getContext());
        jk = AppPreference.getJK(getContext());

        double usia1 = Double.parseDouble(usia);
        double tinggi1 = Double.parseDouble(tinggi);
        double berat1 = Double.parseDouble(berat);
        //pria  88.362 + (13.397 x berat badan [kg]) + (4.799 x tinggi badan [cm]) – (5.677 x umur)
        //wanita 447.593 + (9.247 x berat badan [kg]) + (3.098 x tinggi badan [cm]) – (4.33 x umur)

        if(jk.equalsIgnoreCase("Perempuan")){
            hitung = (447.593 + (9.247*berat1) + (3.098*tinggi1) - (4.33 * usia1)) * 1.375;
            textViewGoals.setText("Target Kalori: " + String.valueOf(hitung) + " cals");
        }else{
            hitung = 88.362 + (13.397*berat1) + (4.799*tinggi1) - (5.677 * usia1) * 1.375;
            textViewGoals.setText("Target Kalori: " + String.valueOf(hitung) + " cals");
        }

        Log.e("usia", usia);
        Log.e("tinggi", tinggi);
        Log.e("berat", berat);
        Log.e("jk", jk);
        return root;
    }
}