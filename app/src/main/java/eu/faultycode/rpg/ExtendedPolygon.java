package eu.faultycode.rpg;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.List;

public class ExtendedPolygon {
    private PolygonOptions polygon;
    String name;
    int id;

    ExtendedPolygon(int id, Context current, List<LatLng> positions, String name) {
        polygon = new PolygonOptions()
                .addAll(positions)
                .clickable(true)
                .fillColor(current.getResources().getColor(R.color.white1))
                .strokeColor(current.getResources().getColor(R.color.white2))
                .strokeWidth(20);
        this.name = name;
        this.id = id;
    }

    public PolygonOptions getPolygon() {
        return polygon;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
