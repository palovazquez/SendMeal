package com.practicaClases.sendmeal.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.practicaClases.sendmeal.ListDishesActivity;
import com.practicaClases.sendmeal.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterOrder extends ArrayAdapter<String> {

    private ArrayList<String> mDataset;
    private Context mContext;


    public AdapterOrder(ArrayList<String> myDataset, Context c) {
        super(c, 0, myDataset);
        mDataset = myDataset;
        mContext = c;
    }



    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null){
            //Inflamos la vista con nuestro propio layout
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fila_pedido,null);
            holder = new ViewHolder();
            // Referenciamos el elemento a modificar y lo rellenamos
            holder.nombreTextView = (TextView) convertView.findViewById(R.id.tv_titulo);
            holder.precioTextView = (TextView) convertView.findViewById(R.id.tv_precio);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        // Valor actual según la posición
        String current = mDataset.get(position);
        Plato plato = ListDishesActivity.getListaPlatos().get(Integer.parseInt(current));
        holder.nombreTextView.setText(plato.getTitulo());
        holder.precioTextView.setText(plato.getPrecio().intValue());

        //Devolvemos la vista inflada
        return convertView;
    }

    static class ViewHolder{
        private TextView nombreTextView;
        private TextView precioTextView;
    }


}
