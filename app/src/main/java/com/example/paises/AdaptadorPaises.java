package com.example.paises;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdaptadorPaises extends ArrayAdapter<Paises> {
    public AdaptadorPaises(Context context, ArrayList<Paises> datos) {
        super(context,  R.layout.ly_paises, datos);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.ly_paises, null);

        TextView lbltitulo = (TextView)item.findViewById(R.id.txtNombre);
        lbltitulo.setText(getItem(position).getNombre());

        ImageView imageView =(ImageView)item.findViewById(R.id.imgPais);
        Glide.with(this.getContext())
                .load(getItem(position).getImagen())
                .into(imageView);

        return item;
    }

}
