package com.example.flaskclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;

public class RegisterActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    EditText namef;
    EditText phoneNof;
    EditText dadf;
    EditText momf;
    EditText emconf;
    EditText guardianf;
    EditText agef;
    Button submitbtn;
    Button addressbtn;
    private ServerConnection serverConnection;
    MapView mapView;
    GoogleMap googleMap;
    Marker marker;
    LatLng posn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        namef = findViewById((R.id.name));
        phoneNof = findViewById((R.id.phone));
        dadf = findViewById((R.id.dad));
        momf = findViewById((R.id.mom));
        emconf = findViewById((R.id.emcon));
        guardianf = findViewById((R.id.guardian));
        agef = findViewById((R.id.age));
        submitbtn = findViewById((R.id.submitbutton));
        mapView =
                (MapView) findViewById(R.id.mapView);
        addressbtn = findViewById(R.id.location_set_button);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        SharedPreferences pref = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        serverConnection = new ServerConnection(this, "http://" + pref.getString("ipv4", "localhost") + ":" + 5000 + "/");

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUser();

            }
        });

        addressbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posn = marker.getPosition();
            }
        });

    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        mapView.onSaveInstanceState(savedInstanceState);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
        super.onLowMemory();
    }

    void setUser() {
        String name, address, phoneNo, dadName, momName, emContact, guardianName, userAge;
        name = namef.getText().toString();
        address = posn.latitude + ", " + posn.longitude;
        System.out.println(address);
        phoneNo = phoneNof.getText().toString();
        dadName = dadf.getText().toString();
        momName = momf.getText().toString();
        emContact = emconf.getText().toString();
        guardianName = guardianf.getText().toString();
        userAge = agef.getText().toString();

        if (name.isEmpty() || address.isEmpty() || phoneNo.isEmpty() || dadName.isEmpty() || momName.isEmpty() || emContact.isEmpty() || guardianName.isEmpty() || userAge.isEmpty()) {
            return;
        }

        User user = new User(name, address, phoneNo, dadName, momName, emContact, guardianName, userAge);

        serverConnection.registerUser(user);

        Intent intent = new Intent(RegisterActivity.this, ChatScreen.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setTiltGesturesEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            return;
        }
        afterPermission();
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    afterPermission();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void afterPermission() {
        LatLng sydney = new LatLng(17.5449, 78.5718);
        marker = googleMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                marker.setPosition(latLng);
            }
        });
    }
}