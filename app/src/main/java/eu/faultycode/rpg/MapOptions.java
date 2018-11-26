package eu.faultycode.rpg;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.maps.android.PolyUtil;

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
                if (isMarkerInVisiblePolygon(extendedMarker, polygon)) {
                    foundInPolygon = true;
                }
            }
            if (!foundInPolygon) {
                map.addMarker(extendedMarker.getMarker());
            }
        }
    }

    private static boolean isMarkerInVisiblePolygon(ExtendedMarker marker, Polygon polygon) {
        return polygon.isVisible() && isMarkerInPolygon(marker, polygon);
    }

    private static boolean isMarkerInPolygon(ExtendedMarker marker, Polygon polygon) {
        return PolyUtil.containsLocation(marker.getMarker().getPosition(), polygon.getPoints(), false);
    }
}
