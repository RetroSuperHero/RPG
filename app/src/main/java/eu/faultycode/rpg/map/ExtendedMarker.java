package eu.faultycode.rpg.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.graphics.BitmapFactory;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Polygon;
import com.google.maps.android.PolyUtil;

public class ExtendedMarker {
    public enum MarkerTypes {
        PLAYER,
        MERCHANT,
        QUEST,
        MONSTER
    }

    private boolean visible;
    private MarkerTypes markerType;
    private Context mapContext;
    private MarkerOptions marker;
    private boolean alwaysVisible;
    private boolean clickable = true;

    private static final int MARKER_SIZE = 150;

    public ExtendedMarker(int id, Context current, LatLng position, String icon, String name, boolean isAlwaysVisible, MarkerTypes markerType, boolean visible) {
        this.markerType = markerType;
        this.visible = visible;
        this.mapContext = current;
        this.alwaysVisible = isAlwaysVisible;
        marker = new MarkerOptions()
                .title(name)
                .position(position)
                .snippet(Integer.toString(id))
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(icon)));
    }

    boolean isInRange(Marker marker, float radius) {
        float[] results = new float[5];
        double thisLatitude = marker.getPosition().latitude;
        double thisLongitude = marker.getPosition().longitude;
        double myLatitude = getPosition().latitude;
        double myLongitude = getPosition().longitude;

        Location.distanceBetween(thisLatitude, thisLongitude, myLatitude, myLongitude, results);
        return results[0] <= radius;
    }

    boolean isMarkerInVisiblePolygon(Polygon polygon) {
        return polygon.isVisible() && this.isMarkerInPolygon(polygon);
    }

    boolean isMarkerInPolygon(Polygon polygon) {
        return PolyUtil.containsLocation(this
                .getMarker()
                .getPosition(),
                polygon.getPoints(),
                false);
    }

    MarkerOptions getMarker() {
        return marker;
    }

    LatLng getPosition() {
        return this.getMarker().getPosition();
    }

    MarkerTypes getMarkerType() {
        return markerType;
    }

    boolean isVisible() {
        return visible;
    }

    void setPosition(LatLng newPosition) {
        this.getMarker().position(newPosition);
    }

    boolean isAlwaysVisible() {
        return alwaysVisible;
    }

    boolean isClickable() {
        return clickable;
    }

    void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    private Bitmap resizeMapIcons(String iconName) {
        Bitmap imageBitmap = BitmapFactory
                .decodeResource(mapContext.getResources(), mapContext.getResources().getIdentifier(iconName, "drawable", mapContext.getPackageName()));
        return Bitmap.createScaledBitmap(imageBitmap, ExtendedMarker.MARKER_SIZE, ExtendedMarker.MARKER_SIZE, false);
    }
}