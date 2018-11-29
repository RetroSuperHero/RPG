package eu.faultycode.rpg;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

public final class MapOptions {
    private MapOptions() { }

    public static GoogleMap setMap(Context currentMapContext, GoogleMap map, int minZoom, int maxZoom, int mapStyle) {
        map.setMaxZoomPreference(maxZoom);
        map.setMinZoomPreference(minZoom);
        map.getUiSettings().setCompassEnabled(false);

        try {
            MapStyleOptions mapStyleOptions = MapStyleOptions.loadRawResourceStyle(currentMapContext, mapStyle);
            boolean styleLoadedSuccessfully = map.setMapStyle(mapStyleOptions);

            if (!styleLoadedSuccessfully) {
                Log.e("StyleJSON", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("StyleJSON", "Can't find style. Error: ", e);
        }
        return map;
    }

    public static void putMarkersOnMap(List<ExtendedMarker> extendedMarkers, List<Polygon> polygons, GoogleMap map) {
        for (ExtendedMarker extendedMarker : extendedMarkers) {
            boolean foundInPolygon = false;
            for (Polygon polygon : polygons) {
                if (extendedMarker.isMarkerInVisiblePolygon(polygon)) {
                    foundInPolygon = true;
                }
            }
            if (!foundInPolygon) {
                map.addMarker(extendedMarker.getMarker());
            }
        }
    }

    public static List<Polygon> putPolygonsOnMap(List<ExtendedPolygon> extendedPolygons, Context current, GoogleMap googleMap) {
        List<Polygon> polygons = new ArrayList<>();
        for(ExtendedPolygon extendedPolygon : extendedPolygons) {
            Polygon polygon = googleMap.addPolygon(extendedPolygon.getPolygon());
            polygon.setTag(extendedPolygon.getName());
            polygons.add(polygon);
            DatabaseHandler db = new DatabaseHandler(current);
            if(db.isDiscovered(extendedPolygon.getId())) {
                polygon.setVisible(false);
            }
        }
        return polygons;
    }

    public static void showDiscovery(Polygon polygon, View discovery, TextView textView) {
        textView.setText(polygon.getTag().toString());
        discovery.animate().translationY(0).setDuration(750);
        new android.os.Handler().postDelayed(() -> {
            discovery.animate().translationY(-350).setDuration(750);
        }, 2500);
    }
}
