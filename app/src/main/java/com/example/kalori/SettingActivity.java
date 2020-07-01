package com.example.kalori;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {
    private Button btn_simpan;
    private EditText editTextUsia, editTextBeratBadan, editTextTinggiBadan;
    private RadioGroup radioGroupnJenisKelamin;
    private RadioButton radioButton;
    private String KEY_USIA = "USIA";
    private String KEY_BERAT = "BERAT";
    private String KEY_TINGGI = "TINGGI";
    private String KEY_JK = "JK";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_setting);
        editTextUsia = findViewById(R.id.editTextUsia);
        editTextBeratBadan = findViewById(R.id.editTextBeratBadan);
        editTextTinggiBadan = findViewById(R.id.editTextTinggiBadan);
        radioGroupnJenisKelamin = findViewById(R.id.jenis_kelamin);

        btn_simpan=findViewById(R.id.btn_simpan);
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupnJenisKelamin.getCheckedRadioButtonId();
                radioButton = findViewById(selectedId);
                String usia = editTextUsia.getText().toString();
                String berat = editTextBeratBadan.getText().toString();
                String tinggi = editTextTinggiBadan.getText().toString();
                String jk = radioButton.getText().toString();
                if(usia.trim().equalsIgnoreCase("")){
                    editTextUsia.setError("Masukkan Usia");
                }if(berat.trim().equalsIgnoreCase("")){
                    editTextBeratBadan.setError("Masukkan Berat Badan");
                }if(tinggi.trim().equalsIgnoreCase("")){
                    editTextTinggiBadan.setError("Masukkan Tinggi Badan");
                }else{
                    Intent i = new Intent(SettingActivity.this, MainActivity.class);
                    i.putExtra(KEY_USIA, usia);
                    i.putExtra(KEY_BERAT, berat);
                    i.putExtra(KEY_TINGGI, tinggi);
                    i.putExtra(KEY_JK, jk);
                    AppPreference.saveUSIA(SettingActivity.this, usia);
                    AppPreference.saveBERAT(SettingActivity.this, berat);
                    AppPreference.saveTINGGI(SettingActivity.this, tinggi);
                    AppPreference.saveJK(SettingActivity.this, jk);
                    startActivity(i);

                    finish();
                }
            }
        });

    }
}
