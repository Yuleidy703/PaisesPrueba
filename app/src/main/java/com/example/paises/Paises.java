package com.example.paises;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Paises {
    private String Nombre;
    private String Imagen;
    public Paises(JSONObject a) throws JSONException {
        Nombre= a.getString("Name");
        Imagen =  "http://www.geognos.com/api/en/countries/flag/" + a.getString("iso2")+".png" ;

    }
    public Paises(){

    }


    public static ArrayList<Paises> JsonObjectsBuild(JSONArray datos) throws JSONException {
        ArrayList<Paises> articulos = new ArrayList<>();
        for (int i = 0; i < datos.length(); i++) {
            articulos.add(new Paises(datos.getJSONObject(i)));
        }
        return articulos;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = "http://www.geognos.com/api/en/countries/flag/"+imagen+".png";
    }
}
