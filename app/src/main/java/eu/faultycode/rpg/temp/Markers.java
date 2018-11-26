package eu.faultycode.rpg.temp;

import android.content.Context;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;

import java.util.List;

import eu.faultycode.rpg.MapOptions;
import eu.faultycode.rpg.ExtendedMarker;

public class Markers {
    private static final int MARKER_SIZE = 150;

    public static void create(List<ExtendedMarker> markers, Context current, GoogleMap mMap, List<Polygon> polygons) {
        LatLng quest = new LatLng(50.054063, 19.935541);
        LatLng agh = new LatLng( 50.064974, 19.923168);
        LatLng sword = new LatLng(50.061103, 19.937051);
        LatLng swordMaster = new LatLng( 50.062267, 19.937652);
        LatLng magic = new LatLng( 50.061640, 19.938972);
        LatLng merchant = new LatLng( 50.062108, 19.936107);

        markers.removeAll(markers);

        markers.add(new ExtendedMarker(0,current, agh,"tome", MARKER_SIZE, MARKER_SIZE, "Rzeźnik Garek"));
        markers.add(new ExtendedMarker(1,current, sword,"sword", MARKER_SIZE, MARKER_SIZE, "Miecznik"));
        markers.add(new ExtendedMarker(2,current, swordMaster,"sword2", MARKER_SIZE, MARKER_SIZE, "Arcymiecznik"));
        markers.add(new ExtendedMarker(3,current, magic,"potion3", MARKER_SIZE, MARKER_SIZE, "Zielarz"));
        markers.add(new ExtendedMarker(4,current, merchant,"coin", MARKER_SIZE, MARKER_SIZE, "Sprzedawca"));
        markers.add(new ExtendedMarker(5,current, quest,"scroll", MARKER_SIZE, MARKER_SIZE, "Lord Chujord"));

        MapOptions.putMarkersOnMap(markers, polygons, mMap);
    }
}
