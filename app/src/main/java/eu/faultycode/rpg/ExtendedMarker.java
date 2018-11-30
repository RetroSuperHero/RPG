package eu.faultycode.rpg;

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
    private Context context;
    private MarkerOptions marker;
    private static final int MARKER_SIZE = 150;

    ExtendedMarker(int id, Context current, LatLng position, String icon, String name) {
        this.context = current;
        marker = new MarkerOptions()
                .title(name)
                .position(position)
                .snippet(Integer.toString(id))
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(icon,MARKER_SIZE,MARKER_SIZE)));
    }

    public Boolean isInRange(Marker marker, float radius) {
        float[] results = new float[5];
        double thisLatitude = marker.getPosition().latitude;
        double thisLongitude = marker.getPosition().longitude;
        double myLatitude = this.getMarker().getPosition().latitude;
        double myLongitude = this.getMarker().getPosition().longitude;

        Location.distanceBetween(thisLatitude, thisLongitude, myLatitude, myLongitude, results);
        return results[0] <= radius;
    }

    private Bitmap resizeMapIcons(String iconName,int width, int height){
        Bitmap imageBitmap = BitmapFactory
                .decodeResource(context.getResources(),context.getResources().getIdentifier(iconName, "drawable", context.getPackageName()));
        return Bitmap.createScaledBitmap(imageBitmap, width, height, false);
    }

    public boolean isMarkerInVisiblePolygon(Polygon polygon) {
        return polygon.isVisible() && this.isMarkerInPolygon(polygon);
    }

    public boolean isMarkerInPolygon(Polygon polygon) {
        return PolyUtil.containsLocation(this
                .getMarker()
                .getPosition(),
                polygon.getPoints(),
                false);
    }

    public MarkerOptions getMarker() {
        return marker;
    }

    public LatLng getPosition() {
        return this.getMarker().getPosition();
    }

    public void setPosition(LatLng newPosition) {
        this.getMarker().position(newPosition);
    }
}