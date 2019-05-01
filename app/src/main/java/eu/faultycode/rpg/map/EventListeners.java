package eu.faultycode.rpg.map;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;

import eu.faultycode.rpg.DatabaseHandler;
import eu.faultycode.rpg.R;
import eu.faultycode.rpg.quests.Fight;
import eu.faultycode.rpg.quests.Quest;

public class EventListeners {
    public static void setEventListeners(Activity mapActivity, MarkersAndPolygons markersAndPolygons, ExtendedMap mMap, CurrentLocation currentLocation) {
        setMarkerListeners(mMap, markersAndPolygons, mapActivity, currentLocation);
        setButtonListeners(mMap, markersAndPolygons, mapActivity, currentLocation);

        //TMP DEBBUG
        setDragListeners(mMap, markersAndPolygons, currentLocation);
    }

    private static void setButtonListeners(ExtendedMap mMap, MarkersAndPolygons markersAndPolygons, Activity mapActivity, CurrentLocation currentLocation) {
        ImageView setCamp = mapActivity.findViewById(R.id.setCamp);
        setCamp.setOnClickListener(v -> {
            markersAndPolygons.addCamp(mMap.getMap());
            v.setVisibility(View.INVISIBLE);
        });

        ImageView zoom = mapActivity.findViewById(R.id.zoom);
        zoom.setOnClickListener(v -> {
            mMap.getMap().animateCamera(CameraUpdateFactory
                    .newLatLngZoom(new LatLng(currentLocation.getMyPosition().latitude,
                            currentLocation.getMyPosition().longitude), ExtendedMap.getMinZoom()));
            RelativeLayout markerInfo = mapActivity.findViewById(R.id.markerInfo);
            markerInfo.setVisibility(View.INVISIBLE);

        });

        RelativeLayout mapLegend = mapActivity.findViewById(R.id.legend);
        mapLegend.setOnClickListener(legend -> {
            ConstraintLayout legendView = mapActivity.findViewById(R.id.legendView);
            ImageView closeLegend = mapActivity.findViewById(R.id.closeLegend);
            legendView.setVisibility(View.VISIBLE);
            closeLegend.setOnClickListener(close -> {
                legendView.setVisibility(View.INVISIBLE);
            });
        });
    }

    private static void setMarkerListeners(ExtendedMap mMap, MarkersAndPolygons markersAndPolygons, Activity mapActivity, CurrentLocation currentLocation) {
        DatabaseHandler db = new DatabaseHandler(mapActivity);
        mMap.getMap().setOnMarkerClickListener(marker -> {
            ExtendedMarker clickedMarker = markersAndPolygons.getMarkers().get(Integer.parseInt(marker.getSnippet()));
            if (!clickedMarker.isClickable()) {
                return true;
            }

            RelativeLayout markerInfo = mapActivity.findViewById(R.id.markerInfo);
            TextView name = mapActivity.findViewById(R.id.name);
            TextView route = mapActivity.findViewById(R.id.route);
            TextView activity = mapActivity.findViewById(R.id.activity);
            name.setText(marker.getTitle());

            mMap.getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), ExtendedMap.getMaxZoom()));
            markerInfo.setVisibility(View.VISIBLE);

            switch (clickedMarker.getMarkerType()) {
                case MERCHANT:
                    activity.setText(R.string.SeeItems);
                    break;
                case QUEST:
                    activity.setText(R.string.Talk);
                    if (currentLocation.getMyLocationMarker().isInRange(marker, 20)) {
                        activity.setTextColor(mapActivity.getResources().getColor(R.color.colorPrimaryDark, null));

                        activity.setOnClickListener(v -> {
                            int markerID = Integer.parseInt(marker.getSnippet());
                            Quest thisQuest = db.getQuestsFromDatabase(markerID);
                            thisQuest.showQuest(mapActivity, markersAndPolygons, mMap);
                        });
                    } else {
                        activity.setTextColor(mapActivity.getResources().getColor(R.color.colorPrimaryDarkAlpha, null));
                    }
                    break;
                case MONSTER:
                    activity.setText(R.string.fight);
                    if (currentLocation.getMyLocationMarker().isInRange(marker, 20)) {
                        activity.setTextColor(mapActivity.getResources().getColor(R.color.colorPrimaryDark, null));

                        activity.setOnClickListener(v -> {
                            int markerID = Integer.parseInt(marker.getSnippet());
                            Fight thisQuest = db.getFightFromDatabase(markerID);
                            thisQuest.showFight(mapActivity);
                        });
                    } else {
                        activity.setTextColor(mapActivity.getResources().getColor(R.color.colorPrimaryDarkAlpha, null));
                    }
                    break;
            }

            route.setOnClickListener(view -> {
                View discovery = mapActivity.findViewById(R.id.discovered);
                TextView textView1 = mapActivity.findViewById(R.id.textView1);
                TextView textView2 = mapActivity.findViewById(R.id.textView2);
                CurrentLocation.showDiscovery(discovery, textView1, textView2);
                ExtendedGeoApiContext.createRoute(mapActivity, currentLocation.getMyLocationMarker(), marker, mMap.getMap());
                mMap.getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getMyPosition().latitude,
                        currentLocation.getMyPosition().longitude), ExtendedMap.getMinZoom()));
                mMap.getMap().setOnPolylineClickListener(Polyline::remove);
            });

            return true;
        });
    }



    //TMP DEBBUG
    private static void setDragListeners(ExtendedMap mMap, MarkersAndPolygons markersAndPolygons, CurrentLocation currentLocation) {
        mMap.getMap().setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
            }

            @Override
            public void onMarkerDrag(Marker marker) {
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                currentLocation.getMyLocationMarker().setPosition(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude));
                markersAndPolygons.isCurrentLocationInPolygon(mMap);
                markersAndPolygons.createMarkersAndPolygons(mMap);
            }
        });
    }
}
