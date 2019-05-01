package eu.faultycode.rpg.map;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import eu.faultycode.rpg.R;

public class CurrentLocation {
    private ExtendedMap mMap;
    private Context mapContext;
    private ExtendedMarker myLocationMarker;
    private LocationManager locationManager;
    private LocationListener locationListener;

    private static final int LOCATION_UPDATE_MIN_DISTANCE = 5;
    private static final int LOCATION_UPDATE_MIN_TIME = 2500;

    public CurrentLocation(Context context, ExtendedMap googleMap, MarkersAndPolygons markersAndPolygons) {
        markersAndPolygons.setCurrentLocation(this);
        mMap = googleMap;
        mapContext = context;
        locationListener = new ExtendedLocationListener(markersAndPolygons, this);
        locationManager = (LocationManager) mapContext.getSystemService(Context.LOCATION_SERVICE);
        //TODO
        myLocationMarker = new ExtendedMarker(0, mapContext, new LatLng(0, 0), "x", "Ja", true, ExtendedMarker.MarkerTypes.PLAYER, true);
        setCurrentLocation(markersAndPolygons);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(getMyPosition()));
    }

    static void showDiscovery(View discovery, TextView textView1, TextView textView2) {
        textView1.setText(R.string.Found);
        textView2.setText(R.string.ShortestPath);
        discovery.animate().translationY(0).setDuration(750);
        new android.os.Handler().postDelayed(() -> {
            discovery.animate().translationY(-350).setDuration(750);
        }, 2500);
    }

    private void setCurrentLocation(MarkersAndPolygons markersAndPolygons) {
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location location = null;
        if (!(isGPSEnabled || isNetworkEnabled))
            Log.e("Error: ", "No connection");
        else {
        //TODO
            if (isGPSEnabled) {
                if (isLocationPermissionGranted()) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, locationListener);
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
            }

            if(isNetworkEnabled) {
                if (isLocationPermissionGranted()) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, locationListener);
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            }
        }

        if (location != null) {
            reloadMap(location, markersAndPolygons);
        }
    }

    private boolean isLocationPermissionGranted() {
        return ContextCompat.checkSelfPermission(mapContext, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(mapContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED;
    }

    void reloadMap(Location location, MarkersAndPolygons markersAndPolygons) {
        mMap.clear();
        myLocationMarker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
        markersAndPolygons.createMarkersAndPolygons(mMap);
        markersAndPolygons.isCurrentLocationInPolygon(mMap);
    }

    ExtendedMarker getMyLocationMarker() {
        return myLocationMarker;
    }

    LatLng getMyPosition() {
        return myLocationMarker.getPosition();
    }
}
