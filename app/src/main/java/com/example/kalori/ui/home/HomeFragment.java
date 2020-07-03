package com.example.kalori.ui.home;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.kalori.AppPreference;
import com.example.kalori.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.concurrent.Executor;

import static android.content.Context.SENSOR_SERVICE;

public class HomeFragment extends Fragment {
    private double MagnitudePrevious = 0;
    private Integer stepCount = 0;
    private TextView textViewGoals, txtJarak, txtSteps;
    private String usia, tinggi, berat, jk;
    private double lat_awal=0, long_awal=0, lat_akhir=0, long_akhir=0;
    private double hitung;
    private TextView stop, start;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        textViewGoals = root.findViewById(R.id.txtGoals);
        stop = root.findViewById(R.id.stop);
        start = root.findViewById(R.id.start);
        txtJarak= root.findViewById(R.id.txtJarak);
        txtSteps = root.findViewById(R.id.txtSteps);
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
            textViewGoals.setText(String.format("%.2f", hitung) + " cals");
        }else{
            hitung = 88.362 + (13.397*berat1) + (4.799*tinggi1) - (5.677 * usia1) * 1.375;
            textViewGoals.setText(String.format("%.2f", hitung) + " cals");
        }

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtJarak.setText("0 m");
//                FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(v.getContext());
//                mFusedLocation.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        if (location != null){
//
//                            Toast.makeText(getActivity(), String.valueOf(location.getLatitude()), Toast.LENGTH_SHORT).show();
//                            Toast.makeText(getActivity(), String.valueOf(location.getLongitude()), Toast.LENGTH_LONG).show();
//
//                            AppPreference.removeLatAwal(getActivity());
//                            AppPreference.removeLongAwal(getActivity());
//                            AppPreference.removeLatAkhir(getActivity());
//                            AppPreference.removeLongAkhir(getActivity());
//
//                            AppPreference.saveLatAwal(getActivity(), String.valueOf(location.getLatitude()));
//                            AppPreference.saveLongAwal(getActivity(), String.valueOf(location.getLongitude()));
//
//                            Log.e("lat", AppPreference.getLatAwal(getActivity()));
//                            Log.e("long", AppPreference.getLongAwal(getActivity()));
//
//                            start.setVisibility(View.GONE);
//                            stop.setVisibility(View.VISIBLE);
//
//                            //Toast.makeText(MainActivity.this, String.valueOf(distance(Double.parseDouble(AppPreference.getLatAwal(MainActivity.this)), Double.parseDouble(AppPreference.getLongAwal(MainActivity.this)), -7.939878, 112.637960)), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });


                SensorManager sensorManager = (SensorManager) getContext().getSystemService(SENSOR_SERVICE);
                Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                SensorEventListener stepDetector = new SensorEventListener() {
                    @Override
                    public void onSensorChanged(SensorEvent sensorEvent) {
                        if (sensorEvent!= null){
                            float x_acceleration = sensorEvent.values[0];
                            float y_acceleration = sensorEvent.values[1];
                            float z_acceleration = sensorEvent.values[2];

                            double Magnitude = Math.sqrt(x_acceleration*x_acceleration + y_acceleration*y_acceleration + z_acceleration*z_acceleration);
                            double MagnitudeDelta = Magnitude - MagnitudePrevious;
                            MagnitudePrevious = Magnitude;

                            if (MagnitudeDelta > 5){
                                stepCount++;
                            }
                            txtSteps.setText(stepCount.toString());
                        }
                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int i) {
                    }
                };

                sensorManager.registerListener(stepDetector, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setVisibility(View.VISIBLE);
                stop.setVisibility(View.GONE);
                FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(v.getContext());
                mFusedLocation.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null){
                            Toast.makeText(getActivity(), String.valueOf(location.getLatitude()), Toast.LENGTH_SHORT).show();
                            Toast.makeText(getActivity(), String.valueOf(location.getLongitude()), Toast.LENGTH_LONG).show();

                            AppPreference.saveLatAkhir(getActivity(), String.valueOf(location.getLatitude()));
                            AppPreference.saveLongAkhir(getActivity(), String.valueOf(location.getLongitude()));

                            start.setVisibility(View.VISIBLE);
                            stop.setVisibility(View.GONE);

                            lat_akhir = Double.parseDouble(String.valueOf(AppPreference.getLatAkhir(getActivity())));
                            long_akhir = Double.parseDouble(String.valueOf(AppPreference.getLongAkhir(getActivity())));
                            lat_awal = Double.parseDouble(String.valueOf(AppPreference.getLatAwal(getActivity())));
                            long_awal = Double.parseDouble(String.valueOf(AppPreference.getLongAwal(getActivity())));

                            Log.e("lat1", String.valueOf(lat_awal));
                            Log.e("long1",String.valueOf(long_awal));
                            Log.e("lat2", String.valueOf(lat_akhir));
                            Log.e("long2", String.valueOf(long_akhir));

                            double jarak = distance(lat_awal, long_awal, lat_akhir, long_akhir);
                            txtJarak.setText(String.valueOf(jarak));


                            //Toast.makeText(MainActivity.this, String.valueOf(distance(Double.parseDouble(AppPreference.getLatAwal(MainActivity.this)), Double.parseDouble(AppPreference.getLongAwal(MainActivity.this)), Double.parseDouble(AppPreference.getLatAkhir(MainActivity.this)), Double.parseDouble(AppPreference.getLongAkhir(MainActivity.this)))), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        return root;
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}