package com.example.admin.umbrellaapp.view.settingsactivity;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.umbrellaapp.R;
import com.example.admin.umbrellaapp.UmbrellaApplication;
import com.example.admin.umbrellaapp.injection.sharedpreference.MySharedPreferences;
import com.example.admin.umbrellaapp.util.Constant;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {

    @Inject
    MySharedPreferences preferences;
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    @BindView(R.id.tvZip)
    TextView tvZip;
    @BindView(R.id.tvUnits)
    TextView tvUnits;

    Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if(Build.VERSION.SDK_INT >= 21)
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.statusBarGray));
        ((UmbrellaApplication) getApplication()).getSettingsComponent().inject(this);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        tvZip.setText(preferences.getStringData(Constant.ZIP_KEY_SP));
        tvUnits.setText(preferences.getStringData(Constant.UNITS_KEY_SP));
    }


    @OnClick({R.id.llZip, R.id.llUnits})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llZip:
                getZipFromUser();
                break;
            case R.id.llUnits:
                getUnitsFromUser();
                break;
        }
    }

    public void getZipFromUser() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.new_zipcode);
        dialog.setTitle("Enter New Zip code");

        Button btnSaveZip = dialog.findViewById(R.id.btnSaveZip);
        btnSaveZip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etZip = dialog.findViewById(R.id.etZipcode);
                preferences.putStringData(Constant.ZIP_KEY_SP, etZip.getText().toString());
                dialog.dismiss();
                tvZip.setText(etZip.getText().toString());
            }
        });

        dialog.show();
    }

    public void getUnitsFromUser() {
        final Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.new_temp_unit);
        dialog.setTitle("Select a temp");

        final AppCompatRadioButton fahrenheit = dialog.findViewById(R.id.rbFahrenheit);
        final AppCompatRadioButton celsius = dialog.findViewById(R.id.rbCelsius);
        final Button btnSaveZip = dialog.findViewById(R.id.btnSaveUnit);

        fahrenheit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                celsius.setChecked(false);
                fahrenheit.setChecked(true);
            }
        });
        celsius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fahrenheit.setChecked(false);
                celsius.setChecked(true);
            }
        });

        btnSaveZip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fahrenheit.isChecked()){
                    preferences.putStringData(Constant.UNITS_KEY_SP, Constant.FAHRENHEIT);
                    tvUnits.setText(fahrenheit.getText().toString().toLowerCase());

                } else if(celsius.isChecked()){
                    preferences.putStringData(Constant.UNITS_KEY_SP, Constant.CELSIUS);
                    tvUnits.setText(celsius.getText().toString().toLowerCase());

                } else {
                    Toast.makeText(SettingsActivity.this, "Select Unit", Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();

            }
        });

        dialog.show();
    }

}
