package eu.faultycode.rpg.map;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;

import java.util.ArrayList;
import java.util.List;

import eu.faultycode.rpg.DatabaseHandler;
import eu.faultycode.rpg.R;
import eu.faultycode.rpg.races.Player;

public class MarkersAndPolygons {
    private Player myPlayer;
    private CurrentLocation currentLocation;
    private Context mapContext;
    private DatabaseHandler db;

    private ExtendedMarker campLocationMarker;
    private List<Polygon> polygons = new ArrayList<>();
    private List<ExtendedMarker> markers = new ArrayList<>();

    public MarkersAndPolygons(Context current, Player myPlayer) {
        db = new DatabaseHandler(current);
        this.myPlayer = myPlayer;
        mapContext = current;
        campLocationMarker = new ExtendedMarker(0, mapContext, new LatLng(0, 0), "backpack", "Obozowisko", true, ExtendedMarker.MarkerTypes.PLAYER,true);
    }

    public void createMarkersAndPolygons(ExtendedMap mMap) {
        mMap.clear();
        if(currentLocation.getMyPosition() != null) {
            currentLocation.getMyLocationMarker().getMarker().draggable(true);
            mMap.addMarker(currentLocation.getMyLocationMarker().getMarker());
        }

        if(campLocationMarker != null) {
            mMap.addMarker(campLocationMarker.getMarker());
        }

        markers = db.getMarkersFromDatabase(mapContext, mMap.getMap());
        List<ExtendedPolygon> polygonsOptions = db.getPolygonsFromDatabase(mapContext);
        polygons = mMap.putPolygonsOnMap(polygonsOptions, db);
        mMap.putMarkersOnMap(markers, polygons);
    }

    void setCurrentLocation(CurrentLocation currentLocation) {
        this.currentLocation = currentLocation;
    }

    void addCamp(GoogleMap mMap) {
        db.addMarkerToDatabase("Obozowisko", "backpack", currentLocation.getMyLocationMarker().getMarker().getPosition().latitude, currentLocation.getMyPosition().longitude,1);
        campLocationMarker.getMarker().position(currentLocation.getMyLocationMarker().getMarker().getPosition());
        mMap.addMarker(campLocationMarker.getMarker());
        myPlayer.setHasCamp(true);
        db.savePlayerToDatabase(myPlayer);
    }

    void isCurrentLocationInPolygon(ExtendedMap mMap) {
        Activity mapActivity = (Activity) mapContext;
        TextView areaName = mapActivity.findViewById(R.id.areaName);
        for (Polygon polygon : this.getPolygons()) {
            if (currentLocation.getMyLocationMarker().isMarkerInPolygon(polygon)) {
                try {
                    areaName.setText(polygon.getTag().toString());
                } catch (NullPointerException e) {
                    areaName.setText(R.string.UnknownTerrain);
                }

                if (polygon.isVisible()) {
                    polygon.setVisible(false);
                    int id = db.getPolygonIdByNameFromDatabase((polygon.getTag()).toString());
                    db.setPolygonDiscoveredInDatabase(id);

                    View discovery = mapActivity.findViewById(R.id.discovered);
                    TextView textView1 = mapActivity.findViewById(R.id.textView1);
                    TextView textView2 = mapActivity.findViewById(R.id.textView2);

                    mMap.showDiscovery(polygon, discovery, textView1, textView2);
                }
            }
        }
    }

    List<ExtendedMarker> getMarkers() {
        return markers;
    }

    List<Polygon> getPolygons() {
        return polygons;
    }
}
