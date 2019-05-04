package eu.faultycode.rpg.markers;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import eu.faultycode.rpg.R;

public class Fight extends MarkerInfo {

    public Fight(int id, int plotID, String title, String content, int rewardExp) {
        super(id, plotID, title, content, rewardExp);
    }

    public void showFight(Activity mapActivity) {
        LinearLayout questView = mapActivity.findViewById(R.id.activity);
        questView.setVisibility(View.VISIBLE);

        TextView fightTitle = mapActivity.findViewById(R.id.activityTitle);
        TextView fightContent = mapActivity.findViewById(R.id.activityContent);

        fightTitle.setText(this.getTitle());
        fightContent.setText(this.getContent());
    }
}
