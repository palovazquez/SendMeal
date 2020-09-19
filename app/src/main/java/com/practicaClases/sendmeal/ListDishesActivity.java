package com.practicaClases.sendmeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.practicaClases.sendmeal.model.AdapterDishes;
import com.practicaClases.sendmeal.model.Plato;

import java.util.ArrayList;
import java.util.List;

public class ListDishesActivity extends AppCompatActivity {


    private static List<Plato> listaPlatos = new ArrayList<Plato>();;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dishes);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarListDishes_id);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.reyclerView_id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

      ///  listaPlatos = new ArrayList<Plato>();
        AdapterDishes adapter = new AdapterDishes(listaPlatos, this);
        recyclerView.setAdapter(adapter);
    }


    public static List<Plato> getListaPlatos() {
        return listaPlatos;
    }
}