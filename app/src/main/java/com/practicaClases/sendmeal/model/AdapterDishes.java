package com.practicaClases.sendmeal.model;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.practicaClases.sendmeal.R;

import java.util.List;

public class AdapterDishes extends RecyclerView.Adapter<AdapterDishes.PlatoViewHolder> {
    private List<Plato> mDataset;
    private AppCompatActivity context;


    public AdapterDishes(List<Plato> myDataset, AppCompatActivity act) {
        mDataset = myDataset;
        context = act;
    }

    public class PlatoViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        TextView tv_name, tv_price;
        ImageView i_dish;

        public PlatoViewHolder(View v) {
            super(v);
            card = v.findViewById(R.id.cardView_id);
            tv_name = v.findViewById(R.id.tv_dishName);
            tv_price = v.findViewById(R.id.tv_dishPrice);
            i_dish = v.findViewById(R.id.imageDish_id);


        }

    }


    @NonNull
    @Override
    public AdapterDishes.PlatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fila_plato, parent, false);
        return new PlatoViewHolder(view);
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull AdapterDishes.PlatoViewHolder holder, int position) {

        Plato plato = mDataset.get(position);

        holder.tv_name.setText(plato.getTitulo());
        holder.tv_price.setText("$ " + Double.toString(plato.getPrecio()));
        holder.i_dish.setImageResource(R.drawable.food2);


    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
