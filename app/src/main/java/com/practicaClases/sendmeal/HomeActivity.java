package com.practicaClases.sendmeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

//TODO faltan algunas dimensiones en main_activity.xml

//TODO faltaria definir un estilo con la fuente, para no tener que ponerla en cada view, con dimensiones etc

// TODO Definir estilo para el menu???

public class HomeActivity extends AppCompatActivity {

    //FIREBASE
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //FIREBASE
        // Inicializar Firebase Auth

        FirebaseApp.initializeApp(HomeActivity.this);
        mAuth = FirebaseAuth.getInstance();
        // Iniciar Session como usuario anónimo
        signInAnonymously();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.d("TOKEN", "Error al conseguir el token");
                            return;
                        }

                        // FCM token
                        String token = task.getResult();

                        // Imprimirlo en un toast y en logs
                        Log.d("TOKEN", token);
                        Toast.makeText(HomeActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarHome_id);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    private void signInAnonymously() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Exito
                            Log.d("FIREBASE", "signInAnonymously:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // Error
                            Log.w("FIREBASE", "signInAnonymously:failure", task.getException());
                            Toast.makeText(HomeActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
                k.putExtra("source", "HomeActivity");
                startActivity(k);
                break;

        }
        return true;
    }



}