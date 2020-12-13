package com.practicaClases.sendmeal.Mapa;

import android.net.Uri;

import androidx.room.TypeConverter;

import com.google.android.gms.maps.model.LatLng;

public class Converters {

        @TypeConverter
        public static String LatngToString(LatLng value) {
            Double l1=value.latitude;
            Double l2=value.longitude;
            String coordl1 = l1.toString();
            String coordl2 = l2.toString();

            return value == null ? null : new String(coordl1 + "," + coordl2);
        }

        @TypeConverter
        public static LatLng stringToLatng(String valor) {

            String[] parts = valor.split(",");
            String part1 = parts[0];
            String part2 = parts[1];

            LatLng latLng = new LatLng(Double.parseDouble(part1),Double.parseDouble(part2));
            return valor == null ? null : latLng;
        }

        @TypeConverter
        public Uri stringToUri(String valor){
            return  Uri.parse(valor);
        }

        @TypeConverter
        public String uriToString (Uri valor){
            return  valor.toString();
        }


}