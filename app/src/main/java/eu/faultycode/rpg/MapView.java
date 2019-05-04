package eu.faultycode.rpg;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

import eu.faultycode.rpg.map.CurrentLocation;
import eu.faultycode.rpg.map.EventListeners;
import eu.faultycode.rpg.map.ExtendedMap;
import eu.faultycode.rpg.map.MarkersAndPolygons;
import eu.faultycode.rpg.races.Player;

public class MapView extends FragmentActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        DatabaseHandler db = new DatabaseHandler(this);
        Player myPlayer = db.getPlayerFromDatabase();
        ExtendedMap mMap = new ExtendedMap(this, googleMap, R.raw.style_json);
        MarkersAndPolygons markersAndPolygons = new MarkersAndPolygons(this, myPlayer);
        CurrentLocation currentLocation = new CurrentLocation(this, mMap, markersAndPolygons);
        markersAndPolygons.createMarkersAndPolygons(mMap);

        EventListeners.setEventListeners(this, markersAndPolygons, mMap, currentLocation);
    }
}
