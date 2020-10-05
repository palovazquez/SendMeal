package com.practicaClases.sendmeal.model;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.practicaClases.sendmeal.R;

import java.util.List;

public class AdapterOrder  extends RecyclerView.Adapter<AdapterOrder.PedidoViewHolder>{

    private List<Integer> mDataset;
    private AppCompatActivity context;


    public AdapterOrder(List<Integer> myDataset, AppCompatActivity act) {
        mDataset = myDataset;
        context = act;
    }

    public class PedidoViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        TextView tv_name, tv_price;
        ImageView i_dish;

        public PedidoViewHolderViewHolder(View v) {
            super(v);

        }


}
