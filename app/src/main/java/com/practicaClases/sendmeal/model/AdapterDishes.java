package com.practicaClases.sendmeal.model;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.practicaClases.sendmeal.R;

import java.util.ArrayList;
import java.util.List;


public class AdapterDishes extends RecyclerView.Adapter<AdapterDishes.PlatoViewHolder> {
    private List<Plato> mDataset;
    private AppCompatActivity context;
    private String source;


    public AdapterDishes(List<Plato> myDataset, AppCompatActivity act, String sourceActivity) {
        mDataset = myDataset;
        context = act;
        source = sourceActivity;
    }

    public class PlatoViewHolder extends RecyclerView.ViewHolder {

        CardView card;
        TextView tv_name, tv_price;
        ImageView i_dish;
        ImageButton botonAgregar;
        Long idPlato;


        public PlatoViewHolder(View v) {
            super(v);
            card = v.findViewById(R.id.cardView_id);
            tv_name = v.findViewById(R.id.tv_dishName);
            tv_price = v.findViewById(R.id.tv_dishPrice);
            i_dish = v.findViewById(R.id.imageDish_id);
            botonAgregar = v.findViewById(R.id.imageButton_addDish);



            if(source!=null && source.equals("PedidoActivity") )
                botonAgregar.setVisibility(v.VISIBLE);
            else
                botonAgregar.setVisibility(v.GONE);

            botonAgregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentResultado = new Intent();


                    intentResultado.putExtra("idPlato", idPlato);
                    intentResultado.putExtra("nombrePlato",tv_name.getText().toString());
                    intentResultado.putExtra("precioPlato",Double.parseDouble((tv_price.getText().toString())));
                    context.setResult(Activity.RESULT_OK, intentResultado);
                    context.finish();
                }
            });

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
    public void onBindViewHolder(@NonNull final PlatoViewHolder holder, final int position) {

        final PlatoViewHolder holderPlato = holder;
        Plato plato = mDataset.get(position);

        //ObtenerID para pasarle a PedidoActivity
        holder.idPlato = plato.getId_plato();

        holder.tv_name.setText(plato.getTitulo());
        holder.tv_price.setText(Double.toString(plato.getPrecio()));

        if(mDataset.get(position).getUriImagen()==null){
            holder.i_dish.setImageResource(R.drawable.food2);
        }else{

            // Creamos una referencia al storage con la Uri de la img
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference gsReference = storage.getReferenceFromUrl(mDataset.get(position).getUriImagen().toString());

            final long THREE_MEGABYTE = 3 * 1024 * 1024;
            gsReference.getBytes(THREE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    // Exito
                    Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    DisplayMetrics dm = new DisplayMetrics();
                    ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);

                    holderPlato.i_dish.setMinimumHeight(dm.heightPixels);
                    holderPlato.i_dish.setMinimumWidth(dm.widthPixels);
                    holderPlato.i_dish.setImageBitmap(bm);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    holder.i_dish.setImageResource(R.drawable.food2);
                }
            });
        }


    }





    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
