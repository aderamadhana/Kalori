package com.example.kalori.ui.home;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.kalori.AppPreference;
import com.example.kalori.R;
import com.example.kalori.SettingActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.content.Context.SENSOR_SERVICE;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private double MagnitudePrevious = 0;
    private Integer stepCount = 0;
    private TextView textViewGoals, txtJarak, txtSteps, txtBakar, txtSetting;
    private String usia, tinggi, berat, jk;
    private double hitung;
    private Button stop, start;
    private double distance;
    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener stepDetector;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        textViewGoals = root.findViewById(R.id.txtGoals);

        stop = root.findViewById(R.id.stop);
        start = root.findViewById(R.id.start);

        txtJarak= root.findViewById(R.id.txtJarak);
        txtSteps = root.findViewById(R.id.txtSteps);
        txtBakar = root.findViewById(R.id.txt_bakar);
        txtSetting = root.findViewById(R.id.txtSetting);

        usia = AppPreference.getUSIA(getContext());
        tinggi = AppPreference.getTINGGI(getContext());
        berat = AppPreference.getBERAT(getContext());
        jk = AppPreference.getJK(getContext());

        sensorManager = (SensorManager) getContext().getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

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

        txtSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), SettingActivity.class);
                startActivity(i);
            }
        });

        start.setOnClickListener(this);

        stop.setOnClickListener(this);

        return root;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(start.isPressed()){
                    getFirstLocation();
                }else if(stop.isPressed()){
                    getLastLocation();
                }
            } else {
                Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:
                if (ContextCompat.checkSelfPermission(
                        getContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_CODE_LOCATION_PERMISSION);
                } else {
                    start.setVisibility(View.GONE);
                    stop.setVisibility(View.VISIBLE);
                    getFirstLocation();
                }
                stepDetector = new SensorEventListener() {
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
                break;
            case R.id.stop:
                sensorManager.unregisterListener(stepDetector);
                AppPreference.saveSteps(getContext(), String.valueOf(stepCount));
                String getstep = AppPreference.getSteps(getContext());
                double hasilBakar = (Double.parseDouble(getstep)/2000)*200;

                txtBakar.setText(String.format("%.2f", hasilBakar)+" cals");

                start.setVisibility(View.VISIBLE);
                stop.setVisibility(View.GONE);

                if (ContextCompat.checkSelfPermission(
                        getContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_CODE_LOCATION_PERMISSION);
                } else {
                    start.setVisibility(View.VISIBLE);
                    stop.setVisibility(View.GONE);
                    getLastLocation();
                }
                break;
            default:
                break;

        }

    }

    public void getFirstLocation() {
        final ProgressDialog dialog = ProgressDialog.show(
                getContext(), "Sedang mendapatkan lokasi", "Silahkan tunggu...", true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(10000);
                    locationRequest.setFastestInterval(3000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(
                                getActivity(),
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                REQUEST_CODE_LOCATION_PERMISSION);
                    } else {
                        LocationServices.getFusedLocationProviderClient(getContext())
                                .requestLocationUpdates(locationRequest, new LocationCallback() {
                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        super.onLocationResult(locationResult);
                                        LocationServices.getFusedLocationProviderClient(getContext())
                                                .removeLocationUpdates(this);
                                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                                            int latestLocationIndex = locationResult.getLocations().size() - 1;
                                            double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                                            double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                                            AppPreference.saveLatAwal(getContext(), String.valueOf(latitude));
                                            AppPreference.saveLongAwal(getContext(), String.valueOf(longitude));

                                        }
                                    }
                                }, Looper.getMainLooper());
                    }
                    Thread.sleep(5000);
                    /*hentikan dialog*/
                    dialog.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void getLastLocation() {
        final ProgressDialog dialog = ProgressDialog.show(
                getContext(), "Sedang kalkulasi data", "Silahkan tunggu...", true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(10000);
                    locationRequest.setFastestInterval(3000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(
                                getActivity(),
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                REQUEST_CODE_LOCATION_PERMISSION);
                    } else {
                        LocationServices.getFusedLocationProviderClient(getContext())
                                .requestLocationUpdates(locationRequest, new LocationCallback() {
                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        super.onLocationResult(locationResult);
                                        LocationServices.getFusedLocationProviderClient(getContext())
                                                .removeLocationUpdates(this);
                                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                                            int latestLocationIndex = locationResult.getLocations().size() - 1;
                                            double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                                            double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();

                                            AppPreference.saveLatAkhir(getContext(), String.valueOf(latitude));
                                            AppPreference.saveLongAkhir(getContext(), String.valueOf(longitude));

                                            double getLatAwal = Double.parseDouble(AppPreference.getLatAwal(getContext()));
                                            double getLongAwal = Double.parseDouble(AppPreference.getLongAwal(getContext()));
                                            double getLatAkhir = Double.parseDouble(AppPreference.getLatAkhir(getContext()));
                                            double getLongAkhir = Double.parseDouble(AppPreference.getLongAkhir(getContext()));
                                            if(getLatAwal == getLatAkhir){
                                                distance = stepCount*0.5;
                                            }else{
                                                distance = distance(getLatAwal, getLongAwal, getLatAkhir, getLongAkhir);
                                            }
                                            txtJarak.setText(String.format("%.2f", distance)+" m");
                                        }
                                    }
                                }, Looper.getMainLooper());
                    }

                    Thread.sleep(5000);
                    dialog.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
        dist = (dist * 60 * 1.1515)*1000;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}