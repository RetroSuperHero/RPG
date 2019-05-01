package eu.faultycode.rpg.map;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class ExtendedLocationListener implements LocationListener {
    private CurrentLocation currentLocation;
    private MarkersAndPolygons markersAndPolygons;

    public ExtendedLocationListener(MarkersAndPolygons markersAndPolygons, CurrentLocation currentLocation) {
        this.currentLocation = currentLocation;
        this.markersAndPolygons = markersAndPolygons;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            currentLocation.reloadMap(location, markersAndPolygons);
        }
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
