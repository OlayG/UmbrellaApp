package com.example.admin.umbrellaapp.view.mainactivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.umbrellaapp.R;
import com.example.admin.umbrellaapp.UmbrellaApplication;
import com.example.admin.umbrellaapp.adapter.WeatherAdapter;
import com.example.admin.umbrellaapp.injection.sharedpreference.MySharedPreferences;
import com.example.admin.umbrellaapp.model.wunderground.WeatherUnderground;
import com.example.admin.umbrellaapp.util.Constant;
import com.example.admin.umbrellaapp.view.settingsactivity.SettingsActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainActivityContract.view, LocationListener {

    private static final int REQUEST_PERMISSION = 10;
    private static final String TAG = "MainActivity";
    FusedLocationProviderClient fusedLocationProviderClient;

    @Inject
    MainActivityPresenter presenter;
    @Inject
    MySharedPreferences preferences;
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    @BindView(R.id.tvCurrentDegree)
    TextView tvCurrentDegree;
    @BindView(R.id.tvCurrentCondition)
    TextView tvCurrentCondition;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.rv)
    RecyclerView rv;

    WeatherAdapter adapter;
    ActionBar actionBar;
    Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        setSupportActionBar(myToolbar);
        actionBar = getSupportActionBar();
        ((UmbrellaApplication) getApplication()).getMainActivityComponent().inject(this);
        presenter.attachView(this);
        presenter.checkDetaultOptions(preferences.getStringData(Constant.ZIP_KEY_SP), preferences.getStringData(Constant.UNITS_KEY_SP));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_my_location:
                getLocation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void showError(String Error) {

    }

    @Override
    public void getZipCodeFromUser() {

        final Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.new_zipcode);
        dialog.setTitle("Enter New Zip code");

        Button btnSaveZip = dialog.findViewById(R.id.btnSaveZip);
        btnSaveZip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etZip = dialog.findViewById(R.id.etZipcode);
                preferences.putStringData(Constant.ZIP_KEY_SP, etZip.getText().toString());
                dialog.dismiss();
                presenter.checkDetaultOptions(preferences.getStringData(Constant.ZIP_KEY_SP), preferences.getStringData(Constant.UNITS_KEY_SP));
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
        btnSaveZip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (fahrenheit.isChecked()) {
                    preferences.putStringData(Constant.UNITS_KEY_SP, Constant.FAHRENHEIT);
                } else if (celsius.isChecked()) {
                    preferences.putStringData(Constant.UNITS_KEY_SP, Constant.CELSIUS);
                } else {
                    Toast.makeText(MainActivity.this, "Select Unit", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
                presenter.checkDetaultOptions(preferences.getStringData(Constant.ZIP_KEY_SP), preferences.getStringData(Constant.UNITS_KEY_SP));
            }
        });

        dialog.show();
    }


    @Override
    public void saveZip(String zip) {
        preferences.putStringData(Constant.ZIP_KEY_SP, zip);
    }

    @Override
    public void showWeather(WeatherUnderground weatherUnderground) {
        String currentDegree;
        String units = preferences.getStringData(Constant.UNITS_KEY_SP);

        if (weatherUnderground.getCurrentObservation() != null) {
            appBar.setVisibility(View.VISIBLE);
            actionBar.setTitle(weatherUnderground.getCurrentObservation().getDisplayLocation().getFull());

            if (units == null) {
                units = Constant.FAHRENHEIT;
            }

            if (units.equalsIgnoreCase(Constant.CELSIUS)) {
                currentDegree = weatherUnderground.getCurrentObservation().getTempC() + getString(R.string.percent_sign);
            } else {
                currentDegree = weatherUnderground.getCurrentObservation().getTempF() + getString(R.string.percent_sign);
            }

            tvCurrentDegree.setText(currentDegree);
            tvCurrentCondition.setText(weatherUnderground.getCurrentObservation().getWeather());
            updateStatusBar(weatherUnderground.getCurrentObservation().getTempF() > 59);

            loadView(weatherUnderground);

        } else {

            Snackbar sb = Snackbar
                    .make(findViewById(R.id.main_content), "Invalid Zipcode", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getZipCodeFromUser();
                        }
                    });
            sb.show();

        }
    }

    private void updateStatusBar(boolean isWarm) {
        if (Build.VERSION.SDK_INT >= 21) {
            if (isWarm) {
                appBar.setBackgroundColor(ContextCompat.getColor(this, R.color.warmOrange));
                window.setStatusBarColor(ContextCompat.getColor(this, R.color.warmOrange));
            } else {
                appBar.setBackgroundColor(ContextCompat.getColor(this, R.color.coolBlue));
                window.setStatusBarColor(ContextCompat.getColor(this, R.color.coolBlue));
            }
        }
    }

    public void loadView(WeatherUnderground weatherUnderground) {

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemViewCacheSize(10);
        rv.setDrawingCacheEnabled(true);
        rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        adapter = new WeatherAdapter(presenter.arrangeHourlyForecast(weatherUnderground.getHourlyForecast()), preferences);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void checkPermissions() {

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                Toast.makeText(this, "Location not on?", Toast.LENGTH_SHORT).show();
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_PERMISSION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            Toast.makeText(this, "Get Location Method", Toast.LENGTH_SHORT).show();
        }
    }

    public void getLocation() {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        //Log.d(T
                        // AG, "onSuccess: " + location.toString());
                        presenter.loadCurrentLocation(location);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }
                });
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 100, this);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

}
