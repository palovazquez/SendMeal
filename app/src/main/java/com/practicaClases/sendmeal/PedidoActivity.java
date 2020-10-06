package com.practicaClases.sendmeal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.practicaClases.sendmeal.HomeActivity;
import com.practicaClases.sendmeal.ListDishesActivity;
import com.practicaClases.sendmeal.MainActivity;
import com.practicaClases.sendmeal.R;
import com.practicaClases.sendmeal.model.AdapterOrder;

import java.util.ArrayList;
import java.util.List;

public class PedidoActivity extends AppCompatActivity {

    private static final int CODIGO_BUSCAR_PLATO = 987;
    ListView lvPedidos;
    //List<ArrayList<String>> listaPlatos = new ArrayList<>();
    ArrayList<String> listaPlatos = new ArrayList<>();
    Button addDish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarPedido_id);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios24);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ListDishesActivity.class);
                startActivity(i);

            }
        });

        addDish = findViewById(R.id.button_addDishes_id);

        addDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (PedidoActivity.this, ListDishesActivity.class);
                i.putExtra("source", "PedidoActivity");
                startActivityForResult(i, CODIGO_BUSCAR_PLATO);
            }
        });

        //Adapter
        lvPedidos = findViewById(R.id.lv_dishes);
        AdapterOrder adapter = new AdapterOrder( listaPlatos, this);
        lvPedidos.setAdapter((ListAdapter) adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(resultCode== Activity.RESULT_OK){
                if(requestCode==CODIGO_BUSCAR_PLATO){
                    listaPlatos.add(data.getExtras().getString("listaPlato"));
                }
            }
    }
}