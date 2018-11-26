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

    public ExtendedMarker(int id, Context current, LatLng position, String icon, int width, int height, String name) {
        this.context = current;
        marker = new MarkerOptions()
                .title(name)
                .position(position)
                .snippet(Integer.toString(id))
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(icon,width,height)));
    }

    public int getId(Marker marker) {
        return Integer.parseInt(marker.getSnippet());
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

    public Boolean isInRange(Marker marker) {
        float radius = 20;
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
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    public boolean isInPolygon(Polygon polygon) {
        return PolyUtil.containsLocation(this.getPosition(), polygon.getPoints(), false);
    }
}