package com.practicaClases.sendmeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.practicaClases.sendmeal.model.Plato;

public class NewDishActivity extends AppCompatActivity {

    EditText et_name, et_description, et_price, et_calories;
    Button button_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dish);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarNewDish_id);
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


        et_name = findViewById(R.id.dishName_id);
        et_description = findViewById(R.id.dishDescription_id);
        et_price = findViewById(R.id.dishPrice_id);
        et_calories = findViewById(R.id.dishCalories_id);
        button_save = findViewById(R.id.button_saveDish_id);

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validarCampos()){
                    Toast.makeText(getApplicationContext(), getString(R.string.error_incomplete), Toast.LENGTH_LONG).show();
                } else {

                    Plato plato = new Plato(et_name.getText().toString(), et_description.getText().toString(), Double.parseDouble(et_price.getText().toString()), (int) Double.parseDouble(et_calories.getText().toString()));

                    if(!ListDishesActivity.getListaPlatos().isEmpty()){
                        int i = 0;
                        boolean coincide = false;
                        int tamaño = ListDishesActivity.getListaPlatos().size();

                        while(!(tamaño==i) && !coincide){
                            if(ListDishesActivity.getListaPlatos().get(i).equals(plato))
                                coincide = true;
                            i++;
                        }
                        if(coincide)
                            Toast.makeText(getApplicationContext(), getString(R.string.error_duplicate), Toast.LENGTH_LONG).show();
                        else{
                            esCorrecto(plato);
                        }

                    }
                    else {
                        esCorrecto(plato);
                    }


                }
            }
        });


    }


    public void esCorrecto(Plato plato){
            Toast.makeText(getApplicationContext(), getString(R.string.successful_load), Toast.LENGTH_LONG).show();

            //ListDishesActivity.getListaPlatos().add(plato);
            et_name.setText("");
            et_description.setText("");
            et_calories.setText("");
            et_price.setText("");
    }


    //VALIDAR
    public boolean validarCampos() {
        boolean validado = true;

        if(et_name.getText().toString().isEmpty()){
            et_name.startAnimation(shakeError());
            validado = false;
        }
        if(et_description.getText().toString().isEmpty()){
            et_description.startAnimation(shakeError());
            validado = false;
        }
        if(et_price.getText().toString().isEmpty()){
            et_price.startAnimation(shakeError());
            validado = false;
        }
        if(et_calories.getText().toString().isEmpty()){
            et_calories.startAnimation(shakeError());
            validado = false;
        }
        return validado;
    }

    public TranslateAnimation shakeError() {
        TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(500);
        shake.setInterpolator(new CycleInterpolator(3));
        return shake;
    }





}