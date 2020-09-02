package com.practicaClases.sendmeal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText et_name, et_surname, et_email, et_password, et_password2, et_card, et_ccv, et_month, et_year, et_cbu, et_aliascbu;
    RadioButton rb_credit, rb_debit;
    CheckBox cb_terms;
    Button button_register;
    SeekBar seekbar_amount;
    Switch switch_load;
    TextView tv_carga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_name = findViewById(R.id.name_id);
        et_surname = findViewById(R.id.surname_id);
        et_email = findViewById(R.id.email_id);
        et_password = findViewById(R.id.password_id);
        et_password2 = findViewById(R.id.password2_id);
        et_card = findViewById(R.id.cardnumber_id);
        et_ccv = findViewById(R.id.ccv_id);
        et_month = findViewById(R.id.expiration_month_id);
        et_year = findViewById(R.id.expiration_year_id);
        et_cbu = findViewById(R.id.cbu_id);
        et_aliascbu = findViewById(R.id.alias_cbu_id);
        rb_credit = findViewById(R.id.rb_credit_id);
        rb_debit = findViewById(R.id.rb_debit_id);
        cb_terms = findViewById(R.id.cb_accept_terms);
        button_register = findViewById(R.id.button_register_id);
        seekbar_amount = findViewById(R.id.seekBar_id);
        switch_load = findViewById(R.id.switch_loadmoney_id);
        tv_carga = findViewById(R.id.tv_carga_id);

        et_card.addTextChangedListener(new TextWatcher() {
           @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String card_string = et_card.getText().toString();

                //Si se había cargado texto en los campos y luego se borra el nro de tarjeta, borra los campos tmb:
                if(!et_ccv.getText().toString().isEmpty() && card_string.isEmpty())
                    et_ccv.setText("");
                if(!et_month.getText().toString().isEmpty() && card_string.isEmpty())
                    et_month.setText("");
                if(!et_year.getText().toString().isEmpty() && card_string.isEmpty())
                    et_year.setText("");
                //Des/habilitar campos
                et_ccv.setEnabled(!card_string.isEmpty());
                et_month.setEnabled(!card_string.isEmpty());
                et_year.setEnabled(!card_string.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password_string = et_password.getText().toString();

                //Si se había cargado la segunda contraseña y luego se actualiza la primera, borra el texto
                if(!et_password2.getText().toString().isEmpty() && password_string.isEmpty())
                    et_password2.setText("");

                //Des/habilitar entrada de segunda contraseña
                et_password2.setEnabled(!password_string.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        et_password2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!et_password.getText().toString().equals(et_password2.getText().toString())){
                    et_password2.setError(getString(R.string.no_coinciden)); //TODO ver por qué no anda sin el "getString" :(
                    et_password2.requestFocus(); //TODO ver si sacamos esto, que _pareciera_ que no cambia nada (?)
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        //LISTENER "ACEPTAR TÉRMINOS Y CONDICIONES" + "REALIZAR CARGA"
        CompoundButton.OnCheckedChangeListener listenerCB = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (buttonView.getId()){
                    case R.id.cb_accept_terms:
                        button_register.setEnabled(isChecked);
                        break;

                    case R.id.switch_loadmoney_id:
                        if(!isChecked){
                            seekbar_amount.setVisibility(View.GONE);
                            tv_carga.setVisibility(View.GONE);
                        } else {
                            seekbar_amount.setVisibility(View.VISIBLE);
                            tv_carga.setVisibility(View.VISIBLE);
                        }
                        break;
                 }
             }
        };
        cb_terms.setOnCheckedChangeListener(listenerCB);
        switch_load.setOnCheckedChangeListener(listenerCB);

        //SEEKBAR Y CANTIDAD A CARGAR
        seekbar_amount.setProgress(0);
        seekbar_amount.setMax(1500);
        seekbar_amount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int cantidad, boolean b) {
                tv_carga.setText("$ " + String.valueOf(cantidad));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        //VALIDACIÓN PARA REGISTRAR
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //(MainActivity.this).
                Validar();
            }
        });

    }

    public void Validar(){
        //Validar campos obligatorios
        if(et_email.getText().toString().length()==0)
            Toast.makeText(this, getString(R.string.complete)+" la dirección de correo electrónico", Toast.LENGTH_LONG).show();
        if(et_password.getText().toString().isEmpty())
            Toast.makeText(this, getString(R.string.complete)+" la contraseña", Toast.LENGTH_LONG).show();
        if(et_card.getText().toString().isEmpty())
            Toast.makeText(this, getString(R.string.complete)+" el número de tarjeta", Toast.LENGTH_LONG).show();
        if(!rb_credit.isChecked() && !rb_debit.isChecked())
            Toast.makeText(this, getString(R.string.complete)+" el tipo de tarjeta", Toast.LENGTH_LONG).show();
        if(et_ccv.getText().toString().isEmpty())
            Toast.makeText(this, getString(R.string.complete)+" el CCV", Toast.LENGTH_LONG).show();
        if(et_month.getText().toString().isEmpty())
            Toast.makeText(this, getString(R.string.complete)+" la fecha de vencimiento (mes)", Toast.LENGTH_LONG).show();
        if(et_year.getText().toString().isEmpty())
            Toast.makeText(this, getString(R.string.complete)+" la fecha de vencimiento (año)", Toast.LENGTH_LONG).show();
    }

}