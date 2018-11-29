package eu.faultycode.rpg.dbbackup;

import android.content.Context;

import eu.faultycode.rpg.DatabaseHandler;

public class Polygons {
    public static void create(Context current) {
        DatabaseHandler db = new DatabaseHandler(current);
//        List<LatLng> lista = new ArrayList<>();
//        lista.add(new LatLng(50.055318, 19.934833));
//        lista.add(new LatLng(50.061903, 19.931754));
//        lista.add(new LatLng(50.062664, 19.932091));
//        lista.add(new LatLng(50.066173, 19.936067));
//        lista.add(new LatLng(50.066283, 19.939801));
//        lista.add(new LatLng(50.064692, 19.944854));
//        lista.add(new LatLng(50.063270, 19.944950));
//        lista.add(new LatLng(50.061297, 19.944092));
//        lista.add(new LatLng(50.058895, 19.940938));
//        lista.add(new LatLng(50.054790, 19.939887));
//        lista.add(new LatLng(50.054246, 19.939200));
//        lista.add(new LatLng(50.055335, 19.937440));
//        lista.add(new LatLng(50.055507, 19.935874));
//        db.addPolygon("Stare miasto", lista);

//        List<LatLng> lista = new ArrayList<>();
//        lista.add(new LatLng(50.055318, 19.934833));
//        lista.add(new LatLng(50.054825, 19.933331));
//        lista.add(new LatLng(50.054295, 19.932966));
//        lista.add(new LatLng(50.053572, 19.932945));
//        lista.add(new LatLng(50.052959, 19.933374));
//        lista.add(new LatLng(50.052504, 19.934071));
//        lista.add(new LatLng(50.052532, 19.935230));
//        lista.add(new LatLng(50.054246, 19.939200));
//        lista.add(new LatLng(50.055335, 19.937440));
//        lista.add(new LatLng(50.055507, 19.935874));
//        db.addPolygon("Wawel", lista, 0);
//
//        List<LatLng> lista = new ArrayList<>();
//        lista.add(new LatLng(50.070016, 19.904027));
//        lista.add(new LatLng(50.068969, 19.911140));
//        lista.add(new LatLng(50.067633, 19.914498));
//        lista.add(new LatLng(50.067523, 19.915474));
//        lista.add(new LatLng(50.067585, 19.918081));
//        lista.add(new LatLng(50.065905, 19.924325));
//        lista.add(new LatLng(50.063860, 19.923585));
//        lista.add(new LatLng(50.064535, 19.919143));
//        lista.add(new LatLng(50.065885, 19.913328));
//        lista.add(new LatLng(50.067469, 19.903039));
//        db.addPolygon("Akademia", lista, 0);
//
//        lista = new ArrayList<>();
//        lista.add(new LatLng(50.065382, 19.915227));
//        lista.add(new LatLng(50.064507, 19.919154));
//        lista.add(new LatLng(50.059982, 19.919583));
//        lista.add(new LatLng(50.059252, 19.924422));
//        lista.add(new LatLng(50.056903, 19.908211));
//        lista.add(new LatLng(50.056975, 19.906927));
//        lista.add(new LatLng(50.060261, 19.900693));
//        lista.add(new LatLng(50.062610, 19.902463));
//        lista.add(new LatLng(50.061115, 19.912055));
//        lista.add(new LatLng(50.064838, 19.913670));
//        lista.add(new LatLng(50.064648, 19.914812));
//        db.addPolygon("Czarny las", lista, 0);
//
//        lista = new ArrayList<>();
//        lista.add(new LatLng(50.064543, 19.919179));
//        lista.add(new LatLng(50.063854, 19.923610));
//        lista.add(new LatLng(50.062835, 19.923256));
//        lista.add(new LatLng(50.062208, 19.923277));
//        lista.add(new LatLng(50.059301, 19.924832));
//        lista.add(new LatLng(50.059267, 19.924392));
//        lista.add(new LatLng(50.059966, 19.919591));
//        db.addPolygon("Hmmmm Right", lista, 0);
//
//        lista = new ArrayList<>();
//        lista.add(new LatLng(50.065367, 19.915229));
//        lista.add(new LatLng(50.064637, 19.914811));
//        lista.add(new LatLng(50.064837, 19.913663));
//        lista.add(new LatLng(50.061152, 19.912064));
//        lista.add(new LatLng(50.062585, 19.902505));
//        lista.add(new LatLng(50.063150, 19.902709));
//        lista.add(new LatLng(50.067399, 19.903160));
//        lista.add(new LatLng(50.065849, 19.913535));
//        db.addPolygon("Hmmmm Left", lista, 0);
//
//        lista = new ArrayList<>();
//        lista.add(new LatLng(50.059308, 19.924836));
//        lista.add(new LatLng(50.062191, 19.923287));
//        lista.add(new LatLng(50.062852, 19.923255));
//        lista.add(new LatLng(50.066688, 19.924639));
//        lista.add(new LatLng(50.069236, 19.926313));
//        lista.add(new LatLng(50.063442, 19.933010));
//        lista.add(new LatLng(50.062633, 19.932093));
//        lista.add(new LatLng(50.062003, 19.931723));
//        lista.add(new LatLng(50.060501, 19.932356));
//        lista.add(new LatLng(50.059654, 19.926455));
//        lista.add(new LatLng(50.059440, 19.925715));
//        db.addPolygon("Karmel South", lista, 0);
//
//        lista = new ArrayList<>();
//        lista.add(new LatLng(50.06925,19.92631));
//        lista.add(new LatLng(50.07011,19.92687));
//        lista.add(new LatLng(50.07045,19.92719));
//        lista.add(new LatLng(50.07153, 19.92905));
//        lista.add(new LatLng(50.07264, 19.93119));
//        lista.add(new LatLng(50.07141, 19.93362));
//        lista.add(new LatLng(50.07086, 19.93465));
//        lista.add(new LatLng(50.06898, 19.93684));
//        lista.add(new LatLng(50.06688, 19.93812));
//        lista.add(new LatLng(50.06627, 19.9384));
//        lista.add(new LatLng(50.06617, 19.93609));
//        lista.add(new LatLng(50.06345, 19.933));
//        lista.add(new LatLng(50.06925, 19.92631));
//        db.addPolygon("Karmel North", lista, 0);
//
//        lista = new ArrayList<>();
//        lista.add(new LatLng(50.07286, 19.94413));
//        lista.add(new LatLng(50.0739, 19.93657));
//        lista.add(new LatLng(50.0737, 19.93425));
//        lista.add(new LatLng(50.07332, 19.93276));
//        lista.add(new LatLng(50.07264, 19.93119));
//        lista.add(new LatLng(50.07141, 19.93362));
//        lista.add(new LatLng(50.07086, 19.93465));
//        lista.add(new LatLng(50.06898, 19.93684));
//        lista.add(new LatLng(50.06688, 19.93812));
//        lista.add(new LatLng(50.06625, 19.9384));
//        lista.add(new LatLng(50.06627, 19.93995));
//        lista.add(new LatLng(50.06608, 19.94073));
//        lista.add(new LatLng(50.06494, 19.94409));
//        lista.add(new LatLng(50.06464, 19.94502));
//        lista.add(new LatLng(50.06865, 19.94514));
//        lista.add(new LatLng(50.07151, 19.94469));
//        lista.add(new LatLng(50.07267, 19.94446));
//        lista.add(new LatLng(50.07286, 19.94413));
//        db.addPolygon("Kleparz", lista, 0);
//
//        lista = new ArrayList<>();
//        lista.add(new LatLng(50.0593, 19.92483));
//        lista.add(new LatLng(50.05941, 19.92563));
//        lista.add(new LatLng(50.05963, 19.92637));
//        lista.add(new LatLng(50.06051, 19.93235));
//        lista.add(new LatLng(50.05887, 19.93321));
//        lista.add(new LatLng(50.05794, 19.93358));
//        lista.add(new LatLng(50.05531, 19.93487));
//        lista.add(new LatLng(50.05482, 19.93332));
//        lista.add(new LatLng(50.05429, 19.93299));
//        lista.add(new LatLng(50.05356, 19.93295));
//        lista.add(new LatLng(50.05352, 19.93278));
//        lista.add(new LatLng(50.05374, 19.9327));
//        lista.add(new LatLng(50.05407, 19.93268));
//        lista.add(new LatLng(50.05447, 19.93236));
//        lista.add(new LatLng(50.05484, 19.93177));
//        lista.add(new LatLng(50.05499, 19.93105));
//        lista.add(new LatLng(50.05505, 19.93029));
//        lista.add(new LatLng(50.05495, 19.92915));
//        lista.add(new LatLng(50.05462, 19.92794));
//        lista.add(new LatLng(50.0556, 19.9271));
//        lista.add(new LatLng(50.05726, 19.92619));
//        lista.add(new LatLng(50.0593, 19.92483));
//        db.addPolygon("Powiśle", lista, 0);
    }
}
