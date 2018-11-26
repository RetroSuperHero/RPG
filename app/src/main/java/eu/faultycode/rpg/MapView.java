package eu.faultycode.rpg;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.Polyline;

import java.util.List;
import java.util.ArrayList;

import eu.faultycode.rpg.races.Human;
import eu.faultycode.rpg.races.Player;
import eu.faultycode.rpg.temp.Markers;
import eu.faultycode.rpg.temp.Polygons;

public class MapView extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private static final int MIN_ZOOM = 14;
    private static final int MAX_ZOOM = 18;
    private ExtendedMarker myLocationMarker;
    private ExtendedMarker campLocationMarker;
    private List<Polygon> polygons = new ArrayList<>();
    private List<ExtendedMarker> markers = new ArrayList<>();
    Player myPlayer = new Human();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = MapOptions.setMap(this, googleMap, MIN_ZOOM, MAX_ZOOM, R.raw.style_json);

        //--TEMP-- Create some static polygons and markers
        createMarkersAndPolygons();

        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocationMarker.getPosition()));

        // --TEMP-- Set markers position after drag
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) { }

            @Override
            public void onMarkerDrag(Marker marker) { }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                myLocationMarker.setPosition(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude));

                //--TEMP-- Check location polygon
                TextView areaName = findViewById(R.id.areaName);
                for (Polygon polygon : polygons) {
                    if (myLocationMarker.isInPolygon(polygon)) {
                        areaName.setText(polygon.getTag().toString());
                    }
                    if (polygon.isVisible() && myLocationMarker.isInPolygon(polygon)) {
                        polygon.setVisible(false);
                        View discovery = findViewById(R.id.discovered);
                        TextView textView = findViewById(R.id.textView2);
                        Polygons.showDiscovery(polygon, discovery, textView);
                    }
                }
                MapOptions.putMarkersOnMap(markers, polygons, mMap);
            }
        });

        // --TEMP-- Set comeback for zoom
        ImageView zoom = findViewById(R.id.zoom);
        zoom.setOnClickListener(v -> {
            mMap.animateCamera(CameraUpdateFactory
                    .newLatLngZoom(new LatLng(myLocationMarker.getPosition().latitude, myLocationMarker.getPosition().longitude), MIN_ZOOM));
            RelativeLayout markerInfo = findViewById(R.id.markerInfo);
            markerInfo.setVisibility(View.INVISIBLE);

        });

        //Set custom onClickListeners for markers
        mMap.setOnMarkerClickListener(marker -> {
            RelativeLayout markerInfo = findViewById(R.id.markerInfo);
            TextView name = findViewById(R.id.name);
            TextView route = findViewById(R.id.route);
            TextView talk = findViewById(R.id.talk);
            name.setText(marker.getTitle());

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), MAX_ZOOM));
            markerInfo.setVisibility(View.VISIBLE);

            if (myLocationMarker.isInRange(marker)) {
                talk.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            } else {
                talk.setTextColor(getResources().getColor(R.color.colorAccent));
            }

            route.setOnClickListener(view -> {
                ExtendedGeoApiContext.createRoute(this, myLocationMarker, marker, mMap);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLocationMarker.getPosition().latitude, myLocationMarker.getPosition().longitude), MIN_ZOOM));
                mMap.setOnPolylineClickListener(Polyline::remove);
            });

            return true;
        });

        //Open map legend
        RelativeLayout mapLegend = findViewById(R.id.legend);
        mapLegend.setOnClickListener(legend -> {
            ConstraintLayout legendView = findViewById(R.id.legendView);
            ImageView closeLegend = findViewById(R.id.closeLegend);

            legendView.setVisibility(View.VISIBLE);
            closeLegend.setOnClickListener(close -> {
                legendView.setVisibility(View.INVISIBLE);
            });
        });

        // NAJBRZYDSZY KOD ŻYCIA
        ViewGroup legendElements = findViewById(R.id.desciptionLegend);
        for (int i=0; i<legendElements.getChildCount(); ++i) {
            View child = legendElements.getChildAt(i);
            ViewGroup childAsGroup = (ViewGroup) child;
            for(int j=0; j<childAsGroup.getChildCount(); ++j) {
                View childesChild = childAsGroup.getChildAt(j);
                if (childesChild instanceof ImageView) childesChild.setOnClickListener(imageView -> {
                    if(imageView.getBackgroundTintList() == null) {
                        int colorPrimary = ContextCompat.getColor(this, R.color.colorPrimaryDarkAlpha);
                        imageView.setBackgroundTintList(ColorStateList.valueOf(colorPrimary));
                    } else {
                        imageView.setBackgroundTintList(null);
                    }
                });
            }
        }
    }

    private void createMarkersAndPolygons() {
        Polygons.create(this, mMap, polygons);
        Markers.create(markers, this, mMap, polygons);

        LatLng myLocation = new LatLng(50.074974, 19.923168);
        myLocationMarker = new ExtendedMarker(0, this, myLocation, "x", 150, 150, "Me");
        myLocationMarker.getMarker().draggable(true);
        mMap.addMarker(myLocationMarker.getMarker());

        campLocationMarker = new ExtendedMarker(0, this, new LatLng(0, 0), "backpack", 150, 150, "Obozowisko");
        ImageView setCamp = findViewById(R.id.setCamp);
        setCamp.setOnClickListener(v -> {
            campLocationMarker.getMarker().position(myLocationMarker.getPosition());
            mMap.addMarker(campLocationMarker.getMarker());
            setCamp.setVisibility(View.INVISIBLE);
        });
    }
}
