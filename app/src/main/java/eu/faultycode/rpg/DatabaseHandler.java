package eu.faultycode.rpg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import eu.faultycode.rpg.races.Human;
import eu.faultycode.rpg.races.Player;

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context current;
    private static final String DB_PATH = "/data/data/eu.faultycode.rpg/databases/db";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "db";

    private static final String TABLE_PLAYER = "player";
    private static final String KEY_PLAYER_ID = "id";
    private static final String KEY_PLAYER_RACE = "race";
    private static final String KEY_PLAYER_NAME = "name";
    private static final String KEY_PLAYER_LEVEL = "level";
    private static final String KEY_PLAYER_EXP = "exp";
    private static final String KEY_PLAYER_HAS_CAMP = "has_camp";

    private static final String TABLE_MARKERS = "markers";
    private static final String KEY_MARKER_ID = "id";
    private static final String KEY_MARKER_NAME = "name";
    private static final String KEY_MARKER_ICON = "icon";
    private static final String KEY_MARKER_LAT = "lat";
    private static final String KEY_MARKER_LNG = "lng";

    private static final String TABLE_POLYGONS = "polygons";
    private static final String KEY_POLYGON_ID = "id";
    private static final String KEY_POLYGON_NAME = "name";
    private static final String KEY_POLYGON_DISCOVERED = "discovered";

    private static final String TABLE_POSITIONS = "positions";
    private static final String KEY_POSITION_ID = "id";
    private static final String KEY_POSITION_LAT = "lat";
    private static final String KEY_POSITION_LNG = "lng";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        current = context;
    }

    public boolean checkDB() {
        SQLiteDatabase check = null;
        try {
            check = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READONLY);
        } catch (Exception e) {
            Log.d("Database: ", "database doesn't exist, creating new one.");
        }
        if (check!=null) {
            check.close();
        }

        return check != null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) { }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MARKERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POLYGONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSITIONS);
        onCreate(db);
    }

    public void create() {
        try {
            createDatabaseFromFile();
        } catch (IOException e) {
            Log.e("Database: ", "couldn't load database. " + e.getMessage());
        }
    }

    private void createDatabaseFromFile() throws IOException {
        InputStream myInput = current.getAssets().open("staticdb.sql");
        OutputStream myOutput = new FileOutputStream(DB_PATH);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void addMarker(String name, String icon, double lat, double lng) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MARKER_NAME, name);
        values.put(KEY_MARKER_ICON, icon);
        values.put(KEY_MARKER_LAT, lat);
        values.put(KEY_MARKER_LNG, lng);

        db.insert(TABLE_MARKERS, null, values);
        db.close();
    }

    public List<ExtendedMarker> getMarkers(Context current, GoogleMap mMap) {
        List<ExtendedMarker> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MARKERS, null);

        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                int id = cursor.getInt(cursor.getColumnIndex(KEY_MARKER_ID));
                String name = cursor.getString(cursor.getColumnIndex(KEY_MARKER_NAME));
                String icon = cursor.getString(cursor.getColumnIndex(KEY_MARKER_ICON));
                float lat = cursor.getFloat(cursor.getColumnIndex(KEY_MARKER_LAT));
                float lng = cursor.getFloat(cursor.getColumnIndex(KEY_MARKER_LNG));
                LatLng position = new LatLng(lat, lng);

                result.add(new ExtendedMarker(id, current, position, icon, name));
                cursor.moveToNext();
            }
        }

        cursor.close();
        return result;
    }

    public void addPolygon(String name, List<LatLng> positions, int discovered) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_POLYGON_NAME, name);
        values.put(KEY_POLYGON_DISCOVERED, discovered);

        db.insert(TABLE_POLYGONS, null, values);

        int id = getPolygonIdByName(name);

        for(LatLng position : positions) {
            addPosition(db, id, position);
        }

        db.close();
    }

    private void addPosition(SQLiteDatabase db, int id, LatLng position) {
        ContentValues values = new ContentValues();
        values.put(KEY_POSITION_ID, id);
        values.put(KEY_POSITION_LAT, position.latitude);
        values.put(KEY_POSITION_LNG, position.longitude);

        db.insert(TABLE_POSITIONS, null, values);
    }

    public int getPolygonIdByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_POLYGONS, new String[] {
                        KEY_POLYGON_ID, KEY_POLYGON_NAME
                }, KEY_POLYGON_NAME + "=?",
                new String[] { name }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        int id = cursor.getInt(cursor.getColumnIndex(KEY_POLYGON_ID));

        return id;
    }

    private String getPolygonNameById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_POLYGONS, new String[] {
                        KEY_POLYGON_ID, KEY_POLYGON_NAME
                }, KEY_POLYGON_ID + "=?",
                new String[] { Integer.toString(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        String name = cursor.getString(cursor.getColumnIndex(KEY_POLYGON_NAME));

        cursor.close();
        return name;
    }

    public List<ExtendedPolygon> getPolygons(Context current) {
        List<ExtendedPolygon> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_POLYGONS, null);

        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                int id = cursor.getInt(cursor.getColumnIndex(KEY_POLYGON_ID));
                String name = cursor.getString(cursor.getColumnIndex(KEY_POLYGON_NAME));
                List<LatLng> positions = getPositionsById(id);
                result.add(new ExtendedPolygon(id, current, positions, name));

                cursor.moveToNext();
            }
        }

        cursor.close();
        return result;
    }

    private List<LatLng> getPositionsById(int id) {
        List<LatLng> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_POSITIONS, new String[] {
                        KEY_POSITION_ID, KEY_POSITION_LAT, KEY_POSITION_LNG
                }, KEY_POSITION_ID + "=?",
                new String[] { Integer.toString(id) }, null, null, null, null);

        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                float lat = cursor.getFloat(cursor.getColumnIndex(KEY_POSITION_LAT));
                float lng = cursor.getFloat(cursor.getColumnIndex(KEY_POSITION_LNG));

                result.add(new LatLng(lat, lng));
                cursor.moveToNext();
            }
        }

        cursor.close();
        return result;
    }

    public boolean isDiscovered(int id) {
        int result;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_POLYGONS, new String[] {
                        KEY_POLYGON_ID, KEY_POLYGON_NAME, KEY_POLYGON_DISCOVERED
                }, KEY_POLYGON_ID + "=?",
                new String[] { Integer.toString(id) }, null, null, null, null);
        if(cursor.moveToFirst()) {
            result = cursor.getInt(cursor.getColumnIndex(KEY_POLYGON_DISCOVERED));
        } else {
            result = 0;
        }

        cursor.close();
        return result == 1;
    }

    public void setDiscovered(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_POLYGON_ID, id);
        values.put(KEY_POLYGON_NAME, getPolygonNameById(id));
        values.put(KEY_POLYGON_DISCOVERED, 1);

        db.update(TABLE_POLYGONS, values, "id = ?", new String[]{Integer.toString(id)});
        db.close();
    }

    public void savePlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLAYER_RACE, player.getClass().toString());
        values.put(KEY_PLAYER_NAME, player.getName());
        values.put(KEY_PLAYER_LEVEL, player.getLevel());
        values.put(KEY_PLAYER_EXP, player.getExp());
        values.put(KEY_PLAYER_HAS_CAMP, player.hasCamp() ? 1 : 0);

        db.update(TABLE_PLAYER, values, "id = ?", new String[]{Integer.toString(1)});
        db.close();
    }

    public Player getPlayer() {
        Player player;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PLAYER, new String[] {
                        KEY_PLAYER_ID, KEY_PLAYER_RACE, KEY_PLAYER_NAME, KEY_PLAYER_LEVEL, KEY_PLAYER_EXP, KEY_PLAYER_HAS_CAMP
                }, KEY_POSITION_ID + "=?",
                new String[] { Integer.toString(1) }, null, null, null, null);

        if (cursor.moveToFirst()){
            switch(cursor.getString(cursor.getColumnIndex(KEY_PLAYER_RACE))) {
                case("class eu.faultycode.rpg.races.Human") :
                    int level = cursor.getInt(cursor.getColumnIndex(KEY_PLAYER_LEVEL));
                    int exp = cursor.getInt(cursor.getColumnIndex(KEY_PLAYER_EXP));
                    String name = cursor.getString(cursor.getColumnIndex(KEY_PLAYER_NAME));
                    boolean hasCamp = cursor.getInt(cursor.getColumnIndex(KEY_PLAYER_HAS_CAMP)) == 1;
                    player = new Human(name, level, exp, hasCamp);
                    break;
                default:
                    player = new Human("ERROR");
            }
        } else {
            player = new Human("ERROR");
        }

        cursor.close();
        return player;
    }

    //TODO: TMP FOR DEBUGGIN'
    public void dropDatabase() {
        File file = new File(DB_PATH);
        boolean deleted = file.delete();
        if(deleted) {
            Log.d("Database: ", "database has been dropped.");
        } else {
            Log.e("Database: ", "couldn't drop database.");
        }
    }
}
