package com.practicaClases.sendmeal.model;


import android.content.Context;
import android.util.Log;
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
import com.practicaClases.sendmeal.PedidoActivity;
import com.practicaClases.sendmeal.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterOrder extends ArrayAdapter<String> {

    private ArrayList<String> listaNombre;
    private ArrayList<Double> listaPrecio;

    public ArrayList<Double> getListaPrecio() {
        return listaPrecio;
    }

    public AdapterOrder(ArrayList<String> nombre, ArrayList<Double> precio, Context c) {
        super(c,0, nombre);
        listaNombre = nombre;
        listaPrecio = precio;

    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup viewGroup) {
        Log.d(AdapterOrder.class.getName(), "Invoca metodo getView: "+ position);

        LayoutInflater inflador = LayoutInflater.from(this.getContext());
        View vistaAux;

        if (convertView == null){

            vistaAux = inflador.inflate(R.layout.fila_pedido,viewGroup,false);


        }
        else{

            vistaAux = convertView;
        }


        ViewHolder holder = (ViewHolder) vistaAux.getTag();

        if(holder == null){
            holder = new ViewHolder(vistaAux);
            vistaAux.setTag(holder);
         }

        holder.nombreTextView.setTag(position);
        holder.precioTextView.setTag(position);

        holder.nombreTextView.setText(listaNombre.get(position));
        Log.d(PedidoActivity.class.getName(), "Setea en listView nombre Plato: "+ listaNombre.get(position));
        holder.precioTextView.setText(listaPrecio.get(position).toString());

        return vistaAux;
    }

}
    class ViewHolder{
        TextView nombreTextView;
        TextView precioTextView;

        public ViewHolder(View v){
        nombreTextView = (TextView) v.findViewById(R.id.tv_titulo);
        precioTextView = (TextView) v.findViewById(R.id.tv_precio);
        }
    }
