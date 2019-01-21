package eu.faultycode.rpg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import eu.faultycode.rpg.races.Human;
import eu.faultycode.rpg.races.Player;

public class ChooseRaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_race);

        DatabaseHandler db = new DatabaseHandler(this);

        if(!db.checkIfDatabaseExists()) {
            View view = findViewById(R.id.intent);
            EditText nameInput = findViewById(R.id.nameInput);

            view.setOnClickListener(thisView -> {
                Player player = new Human(nameInput.getText().toString());
                db.savePlayerToDatabase(player);
                startActivity(new Intent(ChooseRaceActivity.this, MapView.class));
            });
        } else {
            startActivity(new Intent(ChooseRaceActivity.this, MapView.class));
        }
    }
}
