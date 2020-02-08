package com.example.googlemaps;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

class AdaptadorPaises extends ArrayAdapter<Pais> {

        /*public AdaptadorRevistas(Context context, Anios[] datos) {
            super(context, R.layout.ly_items, datos);
        }*/

    public AdaptadorPaises(Context context, List<Pais> datos) {
        super(context, R.layout.paises, datos);
    }


        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.paises, null);

            TextView lblTitulo = (TextView)item.findViewById(R.id.tvNombrePais);
            lblTitulo.setText(getItem(position).getNombres());


            ImageView imageView = (ImageView) item.findViewById(R.id.imgband);
            imageView.setTag(getItem(position).getImg());

            Glide.with(this.getContext())
                    .load(getItem(position).getImg()).into(imageView);

            return(item);
        }
    }

