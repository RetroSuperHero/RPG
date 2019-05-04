package eu.faultycode.rpg.markers;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import eu.faultycode.rpg.DatabaseHandler;
import eu.faultycode.rpg.R;
import eu.faultycode.rpg.map.ExtendedMap;
import eu.faultycode.rpg.map.MarkersAndPolygons;

public class Quest extends MarkerInfo {

    public Quest(int id, int plotID, String title, String content, int rewardExp) {
        super(id, plotID, title, content, rewardExp);
    }

    public void showQuest(Activity mapActivity, MarkersAndPolygons markersAndPolygons, ExtendedMap mMap) {
        DatabaseHandler db = new DatabaseHandler(mapActivity);
        LinearLayout questView = mapActivity.findViewById(R.id.markerActivity);
        questView.setVisibility(View.VISIBLE);

        TextView questTitle = mapActivity.findViewById(R.id.activityTitle);
        TextView questContent = mapActivity.findViewById(R.id.activityContent);
        Button questAccept = mapActivity.findViewById(R.id.accept);

        questTitle.setText(this.getTitle());
        questContent.setText(this.getContent());

        questAccept.setOnClickListener(v -> {
            db.setMarkerActiveInDatabase(6);
            questView.setVisibility(View.INVISIBLE);
            markersAndPolygons.createMarkersAndPolygons(mMap);
        });
    }
}
