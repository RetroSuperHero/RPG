package eu.faultycode.rpg;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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

import eu.faultycode.rpg.races.Player;

public class MapView extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    private static final int MIN_ZOOM = 14;
    private static final int MAX_ZOOM = 18;

    private ExtendedMarker myLocationMarker;
    private ExtendedMarker campLocationMarker;

    private List<Polygon> polygons = new ArrayList<>();
    private List<ExtendedMarker> markers = new ArrayList<>();

    Player myPlayer;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);

        db = new DatabaseHandler(this);
        myPlayer = db.getPlayer();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    @SuppressLint("ResourceAsColor")
    public void onMapReady(GoogleMap googleMap) {
        mMap = MapOptions.setMap(this, googleMap, MIN_ZOOM, MAX_ZOOM, R.raw.style_json);
        createMarkersAndPolygons();
        setEventListeners();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocationMarker.getPosition()));
    }

    private void createMarkersAndPolygons() {
        LatLng myLocation = new LatLng(50.074974, 19.923168);
        myLocationMarker = new ExtendedMarker(0, this, myLocation, "x", myPlayer.getName());
        myLocationMarker.getMarker().draggable(true);
        mMap.addMarker(myLocationMarker.getMarker());

        campLocationMarker = new ExtendedMarker(0, this, new LatLng(0, 0), "backpack", "Obozowisko");
        ImageView setCamp = findViewById(R.id.setCamp);
        if(myPlayer.hasCamp()) {
            setCamp.setVisibility(View.INVISIBLE);
        }
        setCamp.setOnClickListener(v -> {
            db.addMarker("Obozowisko", "backpack", myLocationMarker.getPosition().latitude, myLocationMarker.getPosition().longitude);
            campLocationMarker.getMarker().position(myLocationMarker.getPosition());
            mMap.addMarker(campLocationMarker.getMarker());
            v.setVisibility(View.INVISIBLE);
            myPlayer.setHasCamp(true);
            db.savePlayer(myPlayer);
        });

        markers = db.getMarkers(this, mMap);
        List<ExtendedPolygon> polygonsOptions = db.getPolygons(this);
        polygons = MapOptions.putPolygonsOnMap(polygonsOptions,this, mMap);
        MapOptions.putMarkersOnMap(markers, polygons, mMap);
    }

    private void setEventListeners() {
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
                    if (myLocationMarker.isMarkerInPolygon(polygon)) {
                        areaName.setText(polygon.getTag().toString());
                    }
                    if (polygon.isVisible() && myLocationMarker.isMarkerInPolygon(polygon)) {
                        polygon.setVisible(false);
                        int id = db.getPolygonIdByName(polygon.getTag().toString());
                        db.setDiscovered(id);
                        View discovery = findViewById(R.id.discovered);
                        TextView textView = findViewById(R.id.textView2);
                        MapOptions.showDiscovery(polygon, discovery, textView);
                    }
                }
                MapOptions.putMarkersOnMap(markers, polygons, mMap);
            }
        });

        mMap.setOnMarkerClickListener(marker -> {
            if(marker.getTitle().toString().equals(myPlayer.getName())) {
                Log.d("aaaa", "Działa!");
                return true;
            }
            RelativeLayout markerInfo = findViewById(R.id.markerInfo);
            TextView name = findViewById(R.id.name);
            TextView route = findViewById(R.id.route);
            TextView talk = findViewById(R.id.talk);
            name.setText(marker.getTitle());

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), MAX_ZOOM));
            markerInfo.setVisibility(View.VISIBLE);

            if (myLocationMarker.isInRange(marker, 20)) {
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

        // --TEMP-- Set comeback for zoom
        ImageView zoom = findViewById(R.id.zoom);
        zoom.setOnClickListener(v -> {
            mMap.animateCamera(CameraUpdateFactory
                    .newLatLngZoom(new LatLng(myLocationMarker.getPosition().latitude, myLocationMarker.getPosition().longitude), MIN_ZOOM));
            RelativeLayout markerInfo = findViewById(R.id.markerInfo);
            markerInfo.setVisibility(View.INVISIBLE);

        });

        //Open map legend
        RelativeLayout mapLegend = findViewById(R.id.legend);
        mapLegend.setOnClickListener(legend -> {
            ConstraintLayout legendView = findViewById(R.id.legendView);
            ImageView closeLegend = findViewById(R.id.closeLegend);

            legendView.setVisibility(View.VISIBLE);
            closeLegend.setOnClickListener(close -> {
                legendView.setVisibility(View.INVISIBLE);
                db.dropDatabase();
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
}
