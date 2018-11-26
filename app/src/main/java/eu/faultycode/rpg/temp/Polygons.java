package eu.faultycode.rpg.temp;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.List;

import eu.faultycode.rpg.R;

public class Polygons {
    public static void create(Context current, GoogleMap googleMap, List<Polygon> polygons) {
        Polygon polygon;

        polygon = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(50.055318, 19.934833),
                        new LatLng(50.061903, 19.931754),
                        new LatLng(50.062664, 19.932091),
                        new LatLng(50.066173, 19.936067),
                        new LatLng(50.066283, 19.939801),
                        new LatLng(50.064692, 19.944854),
                        new LatLng(50.063270, 19.944950),
                        new LatLng(50.061297, 19.944092),
                        new LatLng(50.058895, 19.940938),
                        new LatLng(50.054790, 19.939887),
                        new LatLng(50.054246, 19.939200),
                        new LatLng(50.055335, 19.937440),
                        new LatLng(50.055507, 19.935874)));
        polygon.setFillColor(current.getResources().getColor(R.color.white1));
        polygon.setStrokeColor(current.getResources().getColor(R.color.white2));
        polygon.setStrokeWidth(20);
        polygon.setTag("Stare miasto");
        polygons.add(polygon);

        polygon = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(50.055318, 19.934833),
                        new LatLng(50.054825, 19.933331),
                        new LatLng(50.054295, 19.932966),
                        new LatLng(50.053572, 19.932945),
                        new LatLng(50.052959, 19.933374),
                        new LatLng(50.052504, 19.934071),
                        new LatLng(50.052532, 19.935230),
                        new LatLng(50.054246, 19.939200),
                        new LatLng(50.055335, 19.937440),
                        new LatLng(50.055507, 19.935874)));
        polygon.setFillColor(current.getResources().getColor(R.color.white1));
        polygon.setStrokeColor(current.getResources().getColor(R.color.white2));
        polygon.setStrokeWidth(20);
        polygon.setTag("Wawel");
        polygons.add(polygon);

        polygon = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(50.070016, 19.904027),
                        new LatLng(50.068969, 19.911140),
                        new LatLng(50.067633, 19.914498),
                        new LatLng(50.067523, 19.915474),
                        new LatLng(50.067585, 19.918081),
                        new LatLng(50.065905, 19.924325),
                        new LatLng(50.063860, 19.923585),
                        new LatLng(50.064535, 19.919143),
                        new LatLng(50.065885, 19.913328),
                        new LatLng(50.067469, 19.903039)));
        polygon.setFillColor(current.getResources().getColor(R.color.white1));
        polygon.setStrokeColor(current.getResources().getColor(R.color.white2));
        polygon.setStrokeWidth(20);
        polygon.setTag("Akademia");
        polygons.add(polygon);

        polygon = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(50.065382, 19.915227),
                        new LatLng(50.064507, 19.919154),
                        new LatLng(50.059982, 19.919583),
                        new LatLng(50.059252, 19.924422),
                        new LatLng(50.056903, 19.908211),
                        new LatLng(50.056975, 19.906927),
                        new LatLng(50.060261, 19.900693),
                        new LatLng(50.062610, 19.902463),
                        new LatLng(50.061115, 19.912055),
                        new LatLng(50.064838, 19.913670),
                        new LatLng(50.064648, 19.914812)));
        polygon.setFillColor(current.getResources().getColor(R.color.white1));
        polygon.setStrokeColor(current.getResources().getColor(R.color.white2));
        polygon.setStrokeWidth(20);
        polygon.setTag("Czarny las");
        polygons.add(polygon);

        polygon = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(50.064543, 19.919179),
                        new LatLng(50.063854, 19.923610),
                        new LatLng(50.062835, 19.923256),
                        new LatLng(50.062208, 19.923277),
                        new LatLng(50.059301, 19.924832),
                        new LatLng(50.059267, 19.924392),
                        new LatLng(50.059966, 19.919591)));
        polygon.setFillColor(current.getResources().getColor(R.color.white1));
        polygon.setStrokeColor(current.getResources().getColor(R.color.white2));
        polygon.setStrokeWidth(20);
        polygon.setTag("Hmmmm Right");
        polygons.add(polygon);

        polygon = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(50.065367, 19.915229),
                        new LatLng(50.064637, 19.914811),
                        new LatLng(50.064837, 19.913663),
                        new LatLng(50.061152, 19.912064),
                        new LatLng(50.062585, 19.902505),
                        new LatLng(50.063150, 19.902709),
                        new LatLng(50.067399, 19.903160),
                        new LatLng(50.065849, 19.913535)));
        polygon.setFillColor(current.getResources().getColor(R.color.white1));
        polygon.setStrokeColor(current.getResources().getColor(R.color.white2));
        polygon.setStrokeWidth(20);
        polygon.setTag("Hmmmm Left");
        polygons.add(polygon);

        polygon = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(50.059308, 19.924836),
                        new LatLng(50.062191, 19.923287),
                        new LatLng(50.062852, 19.923255),
                        new LatLng(50.066688, 19.924639),
                        new LatLng(50.069236, 19.926313),
                        new LatLng(50.063442, 19.933010),
                        new LatLng(50.062633, 19.932093),
                        new LatLng(50.062003, 19.931723),
                        new LatLng(50.060501, 19.932356),
                        new LatLng(50.059654, 19.926455),
                        new LatLng(50.059440, 19.925715)));
        polygon.setFillColor(current.getResources().getColor(R.color.white1));
        polygon.setStrokeColor(current.getResources().getColor(R.color.white2));
        polygon.setStrokeWidth(20);
        polygon.setTag("Karmel South");
        polygons.add(polygon);

        polygon = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(50.06925,19.92631),
                        new LatLng(50.07011,19.92687),
                        new LatLng(50.07045,19.92719),
                        new LatLng(50.07153, 19.92905),
                        new LatLng(50.07264, 19.93119),
                        new LatLng(50.07141, 19.93362),
                        new LatLng(50.07086, 19.93465),
                        new LatLng(50.06898, 19.93684),
                        new LatLng(50.06688, 19.93812),
                        new LatLng(50.06627, 19.9384),
                        new LatLng(50.06617, 19.93609),
                        new LatLng(50.06345, 19.933),
                        new LatLng(50.06925, 19.92631)));
        polygon.setFillColor(current.getResources().getColor(R.color.white1));
        polygon.setStrokeColor(current.getResources().getColor(R.color.white2));
        polygon.setStrokeWidth(20);
        polygon.setTag("Karmel North");
        polygons.add(polygon);

        polygon = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(50.07286, 19.94413),
                        new LatLng(50.0739, 19.93657),
                        new LatLng(50.0737, 19.93425),
                        new LatLng(50.07332, 19.93276),
                        new LatLng(50.07264, 19.93119),
                        new LatLng(50.07141, 19.93362),
                        new LatLng(50.07086, 19.93465),
                        new LatLng(50.06898, 19.93684),
                        new LatLng(50.06688, 19.93812),
                        new LatLng(50.06625, 19.9384),
                        new LatLng(50.06627, 19.93995),
                        new LatLng(50.06608, 19.94073),
                        new LatLng(50.06494, 19.94409),
                        new LatLng(50.06464, 19.94502),
                        new LatLng(50.06865, 19.94514),
                        new LatLng(50.07151, 19.94469),
                        new LatLng(50.07267, 19.94446),
                        new LatLng(50.07286, 19.94413)));
        polygon.setFillColor(current.getResources().getColor(R.color.white1));
        polygon.setStrokeColor(current.getResources().getColor(R.color.white2));
        polygon.setStrokeWidth(20);
        polygon.setTag("Kleparz");
        polygons.add(polygon);

        polygon = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(50.0593, 19.92483),
                        new LatLng(50.05941, 19.92563),
                        new LatLng(50.05963, 19.92637),
                        new LatLng(50.06051, 19.93235),
                        new LatLng(50.05887, 19.93321),
                        new LatLng(50.05794, 19.93358),
                        new LatLng(50.05531, 19.93487),
                        new LatLng(50.05482, 19.93332),
                        new LatLng(50.05429, 19.93299),
                        new LatLng(50.05356, 19.93295),
                        new LatLng(50.05352, 19.93278),
                        new LatLng(50.05374, 19.9327),
                        new LatLng(50.05407, 19.93268),
                        new LatLng(50.05447, 19.93236),
                        new LatLng(50.05484, 19.93177),
                        new LatLng(50.05499, 19.93105),
                        new LatLng(50.05505, 19.93029),
                        new LatLng(50.05495, 19.92915),
                        new LatLng(50.05462, 19.92794),
                        new LatLng(50.0556, 19.9271),
                        new LatLng(50.05726, 19.92619),
                        new LatLng(50.0593, 19.92483)));
        polygon.setFillColor(current.getResources().getColor(R.color.white1));
        polygon.setStrokeColor(current.getResources().getColor(R.color.white2));
        polygon.setStrokeWidth(20);
        polygon.setTag("Powiśle");
        polygons.add(polygon);
    }

    public static void showDiscovery(Polygon polygon, View discovery, TextView textView) {
        textView.setText(polygon.getTag().toString());
        discovery.animate().translationY(0).setDuration(750);
        new android.os.Handler().postDelayed(() -> {
                    discovery.animate().translationY(-350).setDuration(750);
                }, 2500);
    }
}
