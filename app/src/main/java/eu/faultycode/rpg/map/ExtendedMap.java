package eu.faultycode.rpg.map;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;

import java.util.List;
import java.util.ArrayList;

import eu.faultycode.rpg.DatabaseHandler;

public final class ExtendedMap {
    private static final int MIN_ZOOM = 14;
    private static final int MAX_ZOOM = 18;
    private GoogleMap googleMap;

    public ExtendedMap(Context currentMapContext, GoogleMap googleMap, int mapStyle) {
        this.googleMap = googleMap;
        googleMap.setMaxZoomPreference(MAX_ZOOM);
        googleMap.setMinZoomPreference(MIN_ZOOM);
        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.setLatLngBoundsForCameraTarget(new LatLngBounds(new LatLng(50.044837, 19.901252), new LatLng(50.088705, 19.993166)));

        try {
            MapStyleOptions mapStyleOptions = MapStyleOptions.loadRawResourceStyle(currentMapContext, mapStyle);
            boolean styleLoadedSuccessfully = googleMap.setMapStyle(mapStyleOptions);

            if (!styleLoadedSuccessfully) {
                Log.e("StyleJSON", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("StyleJSON", "Can't find style. Error: ", e);
        }
    }

    public void putMarkersOnMap(List<ExtendedMarker> extendedMarkers, List<Polygon> polygons, GoogleMap map) {
        for (ExtendedMarker extendedMarker : extendedMarkers) {
            boolean foundInPolygon = false;
            for (Polygon polygon : polygons) {
                if (extendedMarker.isMarkerInVisiblePolygon(polygon)) {
                    foundInPolygon = true;
                }
            }
            if (extendedMarker.isAlwaysVisible() && foundInPolygon && extendedMarker.isVisible()) {
                extendedMarker.setClickable(false);
                map.addMarker(extendedMarker.getMarker());
            }

            if (!foundInPolygon && extendedMarker.isVisible()) {
                extendedMarker.setClickable(true);
                map.addMarker(extendedMarker.getMarker());
            }
        }
    }

    public List<Polygon> putPolygonsOnMap(List<ExtendedPolygon> extendedPolygons, Context current, GoogleMap googleMap) {
        List<Polygon> polygons = new ArrayList<>();

        for(ExtendedPolygon extendedPolygon : extendedPolygons) {
            Polygon polygon = googleMap.addPolygon(extendedPolygon.getPolygon());
            polygon.setTag(extendedPolygon.getName());
            polygons.add(polygon);
            DatabaseHandler db = new DatabaseHandler(current);
            if(db.checkIfPolygonIsDiscoveredInDatabase(extendedPolygon.getId())) {
                polygon.setVisible(false);
            }
        }
        return polygons;
    }

    public void showDiscovery(Polygon polygon, View discovery, TextView textView1, TextView textView2) {
        textView1.setText("Odkryłeś");
        textView2.setText(polygon.getTag().toString());
        discovery.animate().translationY(0).setDuration(750);
        new android.os.Handler().postDelayed(() -> {
            discovery.animate().translationY(-350).setDuration(750);
        }, 2500);
    }

    public GoogleMap getMap() {
        return googleMap;
    }

    public void clear() {
        googleMap.clear();
    }

    public void addMarker(MarkerOptions marker) {
        googleMap.addMarker(marker);
    }
    public void moveCamera(CameraUpdate cameraUpdate) {
        googleMap.moveCamera(cameraUpdate);
    }

    public static int getMaxZoom() {
        return MAX_ZOOM;
    }

    public static int getMinZoom() {
        return MIN_ZOOM;
    }
}
