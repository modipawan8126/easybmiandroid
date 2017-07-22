package com.example.has7.easybmi;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button locationButton;

    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationButton = (Button) findViewById(R.id.button3);

        locationButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                fetchLocation(view);
            }
        });
    }

    public void fetchLocation(View view) {
        Log.d(this.getClass().getSimpleName(), "fetchLocation started");

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        if (location == null) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Log.d(this.getClass().getSimpleName(), "Last Location: " + location);
        }

        TextView latlong = (TextView) findViewById(R.id.latlong);
        latlong.setText(location.getLatitude() + " " + location.getLongitude());
    }

    // Define a listener that responds to location updates
    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location loc) {
            // Called when a new location is found by the network location provider.
            //makeUseOfNewLocation(location);
            Log.d(this.getClass().getSimpleName(), "Lat-Log: " + location);
            location = loc;
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {}

        public void onProviderEnabled(String provider) {}

        public void onProviderDisabled(String provider) {}
    };

    public void loadBMILayout(View view) {
        Intent intent = new Intent(this, BMIActivity.class);
        startActivity(intent);
    }
}
