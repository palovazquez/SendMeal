package com.practicaClases.sendmeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText et_name, et_surname, et_email, et_password, et_password2, et_card, et_ccv, et_month, et_year, et_cbu, et_aliascbu;
    RadioButton rb_credit, rb_debit;
    CheckBox cb_terms;
    Button button_register;
    SeekBar seekbar_amount;
    Switch switch_load;
    TextView tv_carga, tv_errorcarga, tv_errorFechaVencimiento;
    Spinner s_mes, s_año;

    //Lista de años Spinner
    ArrayList<String> years = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbarMain_id);
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


        et_name = findViewById(R.id.name_id);
        et_surname = findViewById(R.id.surname_id);
        et_email = findViewById(R.id.email_id);
        et_password = findViewById(R.id.password_id);
        et_password2 = findViewById(R.id.password2_id);
        et_card = findViewById(R.id.cardnumber_id);
        et_ccv = findViewById(R.id.ccv_id);
        et_cbu = findViewById(R.id.cbu_id);
        et_aliascbu = findViewById(R.id.alias_cbu_id);
        rb_credit = findViewById(R.id.rb_credit_id);
        rb_debit = findViewById(R.id.rb_debit_id);
        cb_terms = findViewById(R.id.cb_accept_terms);
        button_register = findViewById(R.id.button_register_id);
        seekbar_amount = findViewById(R.id.seekBar_id);
        switch_load = findViewById(R.id.switch_loadmoney_id);
        tv_carga = findViewById(R.id.tv_carga_id);
        tv_errorcarga = findViewById(R.id.tv_amounterror);
        tv_errorFechaVencimiento = findViewById(R.id.tv_spinnererror);
        s_año = findViewById(R.id.spinnerAño_id);
        s_mes = findViewById(R.id.spinnerMes_id);


        //ADAPTER SPINNER-MES
        ArrayAdapter<CharSequence> adapterMes = ArrayAdapter.createFromResource(this,R.array.meses, R.layout.spinner_item);
        s_mes.setAdapter(adapterMes);


        //ADAPTER SPINNER-AÑO
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i <= thisYear+20; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapterAño = new ArrayAdapter<String>(this, R.layout.spinner_item, years);
        s_año.setAdapter(adapterAño);



        //HABILITAR Y DESHABILITAR CCV, MES Y AÑO SEGÚN "NRO DE TARJETA"
        et_card.addTextChangedListener(new TextWatcher() {
           @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String card_string = et_card.getText().toString();

                //Si se había cargado texto en los campos y luego se borra el nro de tarjeta, borra los campos tmb:
                if(!et_ccv.getText().toString().isEmpty() && card_string.isEmpty())
                    et_ccv.setText("");
                /*if(!et_month.getText().toString().isEmpty() && card_string.isEmpty())
                    et_month.setText("");
                if(!et_year.getText().toString().isEmpty() && card_string.isEmpty())
                    et_year.setText("");*/

                //Des/habilitar campos
                et_ccv.setEnabled(!card_string.isEmpty());
                s_año.setEnabled(!card_string.isEmpty());
                s_mes.setEnabled(!card_string.isEmpty());
            }

            @Override public void afterTextChanged(Editable editable) {}
        });

        //HABILITAR "REPETIR CONTRASEÑA" UNA VEZ SE COMPLETÓ "CONTRASEÑA"
        et_password.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password_string = et_password.getText().toString();

                //Si se había cargado la segunda contraseña y luego se actualiza la primera, borra el texto
                if(!et_password2.getText().toString().isEmpty() && password_string.isEmpty())
                    et_password2.setText("");

                //Des/habilitar entrada de segunda contraseña
                et_password2.setEnabled(!password_string.isEmpty());
            }

            @Override public void afterTextChanged(Editable editable) {}
        });

        //VALIDAR CONTRASEÑAS
        et_password2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validarContrasenia();
                }
            }
        });


        //VALIDAR VENCIMIENTO
        s_año.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    validarVencimiento();
                }
            }
        });

        //CHEQUEAR CBU VÁLIDO (22 CARÁCTERES)
        et_cbu.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validarCBU();
                }
            }
        });

        //CHEQUAR EMAIL VÁLIDO
        et_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    validarEmail();
                }
            }
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
                            tv_errorcarga.setVisibility(View.GONE);
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

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        //VALIDACIÓN PARA REGISTRAR
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validar();

            }
        });
    }


    public boolean validarVencimiento() {

        Calendar fecha_minima = Calendar.getInstance();
        fecha_minima.add(Calendar.MONTH, 3);

        Calendar fecha_ingresada = Calendar.getInstance();
        fecha_ingresada.set(Calendar.YEAR, Integer.parseInt(years.get(s_año.getSelectedItemPosition())));
        fecha_ingresada.set(Calendar.MONTH, s_mes.getSelectedItemPosition());
        fecha_ingresada.set(Calendar.DATE, fecha_minima.DATE);

        if (fecha_ingresada.before(fecha_minima)) {
            tv_errorFechaVencimiento.setVisibility(View.VISIBLE);
            return false;
        } else {
            tv_errorFechaVencimiento.setVisibility(View.GONE);
            return true;
        }
    }

    public boolean validarEmail() {
        Pattern pattern_email = Patterns.EMAIL_ADDRESS;
        if(!pattern_email.matcher(et_email.getText().toString()).matches() && !et_email.getText().toString().isEmpty()){
            et_email.setError(getString(R.string.error_email));
            return false;
        }else return true;
    }

    public boolean validarCBU() {
        if(et_cbu.getText().toString().length() != 22  && !et_cbu.getText().toString().isEmpty()) {
            et_cbu.setError(getString(R.string.error_cbu));
            return false;
        }else return true;
    }

    public boolean validarContrasenia() {
        if(!et_password.getText().toString().equals(et_password2.getText().toString())) {
            et_password2.setError(getString(R.string.error_no_coinciden));
            return false;
        } else return true;
    }

    public void Validar(){
        boolean validado = true;

        //VALIDAR CARGA INICIAL
        int cantidad_inicial = seekbar_amount.getProgress();
        if(cantidad_inicial==0 && switch_load.isChecked()){

            tv_errorcarga.setVisibility(View.VISIBLE);
            validado = false;
        }else{
            tv_errorcarga.setVisibility(View.GONE);
        }

        //Validar campos obligatorios
        if(et_email.getText().toString().length()==0){
            et_email.startAnimation(shakeError());
            validado = false;
        }
        if(et_password.getText().toString().isEmpty()){
            et_password.startAnimation(shakeError());
            et_password2.startAnimation(shakeError());
            validado = false;
        }
        if(et_card.getText().toString().isEmpty()) {
            et_card.startAnimation(shakeError());
            validado = false;
        }
        if(!rb_credit.isChecked() && !rb_debit.isChecked()){
            rb_credit.startAnimation(shakeError());
            validado = false;
        }
        if(et_ccv.getText().toString().isEmpty()){
            et_ccv.startAnimation(shakeError());
            validado = false;
        }
        if(validarContrasenia() && validarCBU() && validarEmail() && validarVencimiento() && validado)
            Toast.makeText(this, getString(R.string.exito), Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);


    }

    public TranslateAnimation shakeError() {
        TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(500);
        shake.setInterpolator(new CycleInterpolator(3));
        return shake;
    }

    //lkasdlka
}