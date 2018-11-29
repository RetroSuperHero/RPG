package eu.faultycode.rpg;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExtendedGeoApiContext {
    private static final int PATTERN_GAP_LENGTH_PX = 10;
    private static final PatternItem DOT = new Dot();
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    private static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DOT);

    public static void createRoute(Context current, ExtendedMarker myLocationMarker, Marker destinationMarker, GoogleMap mMap) {
        DateTime now = new DateTime();

        com.google.maps.model.LatLng origin = new com.google.maps.model.LatLng(myLocationMarker.getPosition().latitude, myLocationMarker.getPosition().longitude);
        com.google.maps.model.LatLng destination = new com.google.maps.model.LatLng(destinationMarker.getPosition().latitude, destinationMarker.getPosition().longitude);

        try {
            DirectionsResult result = DirectionsApi
                    .newRequest(ExtendedGeoApiContext.getGeoContext(current))
                    .mode(TravelMode.WALKING).origin(origin)
                    .destination(destination).departureTime(now)
                    .await();

            addPolyline(current, result, mMap);
        } catch (Exception e) {
            Log.e("Error", "Couldn't find route!");
        }
    }

    private static GeoApiContext getGeoContext(Context current) {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext.setQueryRateLimit(3)
                .setApiKey(current.getString(R.string.google_maps_key))
                .setConnectTimeout(3, TimeUnit.SECONDS)
                .setReadTimeout(1, TimeUnit.SECONDS)
                .setWriteTimeout(1, TimeUnit.SECONDS);
    }

    private static void addPolyline(Context current, DirectionsResult results, GoogleMap mMap) {
        int colorPrimary = ContextCompat.getColor(current, R.color.delicateGreen);
        List<LatLng> decodedPath = PolyUtil.decode(results
                .routes[0]
                .overviewPolyline
                .getEncodedPath());

        mMap.addPolyline(new PolylineOptions()
                .addAll(decodedPath)
                .jointType(2)
                .color(colorPrimary)
                .pattern(PATTERN_POLYGON_ALPHA)
                .clickable(true));
    }
}
