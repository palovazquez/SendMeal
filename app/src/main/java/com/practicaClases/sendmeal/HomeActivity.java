package com.practicaClases.sendmeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class HomeActivity extends AppCompatActivity {
//TODO faltan algunas dimensiones en main_activity.xml ---CARDVIEW YA ESTAN

//TODO faltaria definir un estilo con la fuente, para no tener que ponerla en cada view, con dimensiones etc

//TODO falta poner spinner para mes y año

// TODO Definir estilo para el menu???


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarHome_id);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menuRegister_id:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
            case R.id.menuCreateItem_id:
                Intent j = new Intent(this, NewDishActivity.class);
                startActivity(j);
                break;
            case R.id.menuListItems_id:
                Intent k = new Intent(this, ListDishesActivity.class);
                startActivity(k);
                break;

        }
        return true;
    }



}