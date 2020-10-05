package com.practicaClases.sendmeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.practicaClases.sendmeal.model.AdapterDishes;
import com.practicaClases.sendmeal.model.Plato;

import java.util.ArrayList;
import java.util.List;

public class ListDishesActivity extends AppCompatActivity {


    ImageView botonAgregar;

    private static List<Plato> listaPlatos = new ArrayList<Plato>();;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dishes);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarListDishes_id);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios24);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.reyclerViewListDishes_id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        AdapterDishes adapter = new AdapterDishes(listaPlatos, this);
        recyclerView.setAdapter(adapter);


        botonAgregar = findViewById(R.id.imageButton);
        botonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentResultado = new Intent();
                intentResultado.putExtra("")


            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_listdishes, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent i = new Intent(this, PedidoActivity.class);
        startActivity(i);
        return true;
    }

    public static List<Plato> getListaPlatos() {
        return listaPlatos;
    }
}