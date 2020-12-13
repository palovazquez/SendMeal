package com.practicaClases.sendmeal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.practicaClases.sendmeal.DAO.AppRepository;
import com.practicaClases.sendmeal.Mapa.MapaActivity;
import com.practicaClases.sendmeal.model.AdapterOrder;
import com.practicaClases.sendmeal.model.Pedido;
import com.practicaClases.sendmeal.model.Plato;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PedidoActivity extends AppCompatActivity implements AppRepository.OnResultCallback  {


    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";


    private static final int CODIGO_BUSCAR_PLATO = 987;
    private static final int CODIGO_LATITUD_MAPA = 989;
    ListView lvPedidos;
    ArrayList<String> listaNombre = new ArrayList<>();
    ArrayList<Double> listaPrecio = new ArrayList<>();
    List<Long> listaPlatosId = new ArrayList<Long>();
    List<Plato> listaPlatos = new ArrayList<Plato>();
    EditText et_email, et_adress;
    TextView total, cantidad;
    RadioButton envio;
    Button addDish, confirm, addMapa;
    GuardarPedido tarea;
    LatLng ubicacion = null;



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

        cantidad = findViewById(R.id.amountDishes_id);
        total = findViewById(R.id.totalPrice_id);
        envio = findViewById(R.id.envio_id);

        et_email = findViewById(R.id.email_id);
        et_adress = findViewById(R.id.adress_id);

        et_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    validarEmail();
                }
            }
        });

        et_adress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus && et_adress.getText().toString().isEmpty()) {
                    et_adress.setError(getString(R.string.error));
                }
            }
        });


        //BOTON AGREGAR PLATO
        addDish = findViewById(R.id.button_addDishes_id);
        addDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PedidoActivity.this, ListDishesActivity.class);
                i.putExtra("source", "PedidoActivity");
                startActivityForResult(i, CODIGO_BUSCAR_PLATO);
            }
        });


        // BUSCAR PLATOS EN BDD
        AppRepository repository = new AppRepository(this.getApplication(), this);
        repository.buscarTodos();

        //AGREGAR DIRECCIÓN
        addMapa = findViewById(R.id.button_addMapa_id);
        addMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PedidoActivity.this, MapaActivity.class);
                i.putExtra("mapa", "PedidoActivity");
                startActivityForResult(i, CODIGO_LATITUD_MAPA);
            }
        });


        //BOTON CONFIRMAR PEDIDO
        tarea = new GuardarPedido();
        confirm = findViewById(R.id.button_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_adress.getText().toString().isEmpty() || et_email.getText().toString().isEmpty() || ubicacion==null) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_Final), Toast.LENGTH_LONG).show();
                    Log.d("MAPA", "Esta es la latitud nula:" + ubicacion);
                } else {
                    Log.d("MAPA", "Esta es la latitud correcta:" + ubicacion);
                    Pedido pedido = new Pedido(et_email.getText().toString(), et_adress.getText().toString(), envio.isChecked(), ubicacion);

                   //SETEAR IDPEDIDO A CADA PLATO
                    int i = 0 ;
                    while(i < listaPlatos.size()){
                        int j = 0;
                        while(j < listaPlatosId.size()){
                            if(listaPlatosId.get(j)==listaPlatos.get(i).getId_plato()){
                             listaPlatos.get(i).setIdPedido(listaPlatosId.get(j));
                            }
                            j++;
                        }
                        i++;
                    }
                    //EJECUTAR NOTIFICACIÓN
                    tarea.execute();

                    Intent j = new Intent(PedidoActivity.this, HomeActivity.class);
                    startActivity(j);

                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CODIGO_BUSCAR_PLATO) {

                //GET EXTRAS DEL INTENT
                listaNombre.add(data.getExtras().getString("nombrePlato"));
                listaPrecio.add(data.getDoubleExtra("precioPlato", 0));
                listaPlatosId.add(data.getLongExtra("idPlato", 0));

                //ADAPTER LISTVIEW
                lvPedidos = findViewById(R.id.lv_dishes);
                AdapterOrder adapter = new AdapterOrder(listaNombre, listaPrecio, this);
                lvPedidos.setAdapter(adapter);

                Double totalPrice = 0.0;
                int cant = 0;
                for (Double p : adapter.getListaPrecio()) {
                    totalPrice = totalPrice + p;
                    cant = cant+1;
                }

                total.setText("Total: $"+ String.valueOf(totalPrice));
                cantidad.setText("Cantidad: " + String.valueOf(cant));

            }
            if(requestCode == CODIGO_LATITUD_MAPA) {
                //GET EXTRAS DEL INTENT
                ubicacion = data.getExtras().getParcelable("Ubicacion");


            }
        }



    }


    public boolean validarEmail() {
        Pattern pattern_email = Patterns.EMAIL_ADDRESS;
        if (!pattern_email.matcher(et_email.getText().toString()).matches() && !et_email.getText().toString().isEmpty()) {
            et_email.setError(getString(R.string.error_email));
            return false;
        } else return true;
    }



    //TODO
    @Override
    public void onResult(List result) {
        // Vamos a obtener una Lista de items como resultado cuando finalize
        listaPlatos = result;
    }

    @Override
    public void onResult(Object result) {

    }

    class GuardarPedido extends AsyncTask<Double, Integer, String> {
        //la tarea esperará 5 segundos en el background antes de
        // finalizar y enviar un mensaje de broadcast para crear
        // una notificacion de android

        @Override
        protected String doInBackground(Double... doubles) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String result) {

            Notification notification = getNotification(getString(R.string.notificaciónDescripción), getString(R.string.notificaciónTitulo));
            Intent notificationIntent = new Intent(getApplicationContext(), MyNotificationPublisher.class);
            notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, 1);
            notificationIntent.putExtra(MyNotificationPublisher.PEDIDO_GUARDADO, notification);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            assert alarmManager != null;
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, pendingIntent);


        }


        private Notification getNotification(String content, String tittle) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), default_notification_channel_id);
            builder.setContentTitle(!tittle.isEmpty() ? tittle : "Scheduled Notification");
            builder.setContentText(content);
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
            builder.setAutoCancel(true);
            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            return builder.build();
        }
    }
}