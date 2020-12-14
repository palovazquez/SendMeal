package com.practicaClases.sendmeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.practicaClases.sendmeal.model.Plato;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.UUID;

public class NewDishActivity extends AppCompatActivity {

    static final int CAMARA_REQUEST = 1;
    static final int GALERIA_REQUEST = 2;
    static final int MY_CAMERA_PERMISSION_CODE = 100;
    static final int MY_GALLERY_PERMISSION_CODE = 200;


    EditText et_name, et_description, et_price, et_calories;
    Button button_save;
    ImageButton button_photo, button_gallery;
    StorageReference storageRef;
    StorageReference platosImagesRef;
    UploadTask uploadTask;

    FirebaseStorage storage;
    byte[] dataa;
    Uri downloadUri;


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


        //FIREBASE
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();


        et_name = findViewById(R.id.dishName_id);
        et_description = findViewById(R.id.dishDescription_id);
        et_price = findViewById(R.id.dishPrice_id);
        et_calories = findViewById(R.id.dishCalories_id);
        button_save = findViewById(R.id.button_saveDish_id);
        button_photo = findViewById(R.id.imageButtonPhoto_id);
        button_gallery = findViewById(R.id.imageButtonPhotoGallery_id);


        //GUARDAR PLATO
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validarCampos()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_incomplete), Toast.LENGTH_LONG).show();
                } else {

                    Plato plato = new Plato(et_name.getText().toString(), et_description.getText().toString(), Double.parseDouble(et_price.getText().toString()),
                            (int) Double.parseDouble(et_calories.getText().toString()), downloadUri);
                    Log.d("FIREBASE", "URI guardada en el plato: " + plato.getUriImagen());
                    if (!ListDishesActivity.getListaPlatos().isEmpty()) {
                        int i = 0;
                        boolean coincide = false;
                        int tamaño = ListDishesActivity.getListaPlatos().size();

                        while (!(tamaño == i) && !coincide) {
                            if (ListDishesActivity.getListaPlatos().get(i).equals(plato))
                                coincide = true;
                            i++;
                        }
                        if (coincide)
                            Toast.makeText(getApplicationContext(), getString(R.string.error_duplicate), Toast.LENGTH_LONG).show();
                        else {
                            esCorrecto(plato);
                        }

                    } else {
                        esCorrecto(plato);
                    }


                }
            }
        });


        button_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Log.d("PERMISOS", "Llama a request Permission BOTON CAMARA");
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Log.d("PERMISOS", "Llama a abrircamara");
                    lanzarCamara();
                }
            }
        });

        button_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Log.d("PERMISOS", "Llama a request Permission BOTON GALERIA");
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Log.d("PERMISOS", "Llama a abrirgaleria");
                    abrirGaleria();

                }
            }
        });


    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, int i) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == MY_CAMERA_PERMISSION_CODE) {
                Toast.makeText(this, "camara ok", Toast.LENGTH_LONG).show();
                lanzarCamara();
            } else if (requestCode == MY_GALLERY_PERMISSION_CODE) {
                Toast.makeText(this, "galeria ok", Toast.LENGTH_LONG).show();
                abrirGaleria();
            }
        } else {
            Toast.makeText(this, "permiso a camara denegado", Toast.LENGTH_LONG).show();
        }
    }


    private void lanzarCamara() {
        Intent camaraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camaraIntent, CAMARA_REQUEST);

    }

    private void abrirGaleria() {
        Log.d("GALERIA", "entro a abrirGaleria");
        Intent galeriaIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galeriaIntent, GALERIA_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == CAMARA_REQUEST || requestCode == GALERIA_REQUEST) && resultCode == RESULT_OK && data != null) {

            // Creamos una referencia a nuestro Storage
            storageRef = storage.getReference();
            // Creamos una referencia a 'images/plato_id.jpg'

            final ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("Cargando imagen...");


            final String randomKey = UUID.randomUUID().toString();

            if (requestCode == CAMARA_REQUEST) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] dataa = baos.toByteArray(); // Imagen en arreglo de bytes
                platosImagesRef = storageRef.child("images/" + randomKey + ".jpg");
                pd.show();
                uploadTask = (UploadTask) platosImagesRef.putBytes(dataa).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progess = (100.00*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                        pd.setMessage("Porcentaje: " + (int) progess);} });

            }
            if (requestCode == GALERIA_REQUEST) {
                //Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
                Uri dataGaleria = data.getData();
                platosImagesRef = storageRef.child("images/" + dataGaleria.getLastPathSegment());
                pd.show();
                assert dataGaleria != null;
                uploadTask = (UploadTask) platosImagesRef.putFile(dataGaleria)
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                double progess = (100.00*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                                pd.setMessage("Porcentaje: " + (int) progess);} });


            }

            // Registramos un listener para saber el resultado de la operación
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continuamos con la tarea para obtener la URL
                    return platosImagesRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        // URL de descarga del archivo
                        downloadUri = task.getResult();
                        pd.dismiss();
                        Log.d("FIREBASE", "URL conseguida from task.getResult(): " + downloadUri);
                    } else {
                        // Fallo
                        pd.dismiss();
                    }
                }
            });
        }
    }




    public void esCorrecto(Plato plato) {
        Toast.makeText(getApplicationContext(), getString(R.string.successful_load), Toast.LENGTH_LONG).show();

        //TODO SACAR LISTAPLATOS DE BDD
        ListDishesActivity.getListaPlatos().add(plato);
        et_name.setText("");
        et_description.setText("");
        et_calories.setText("");
        et_price.setText("");
    }


    //VALIDAR
    public boolean validarCampos() {
        boolean validado = true;

        if (et_name.getText().toString().isEmpty()) {
            et_name.startAnimation(shakeError());
            validado = false;
        }
        if (et_description.getText().toString().isEmpty()) {
            et_description.startAnimation(shakeError());
            validado = false;
        }
        if (et_price.getText().toString().isEmpty()) {
            et_price.startAnimation(shakeError());
            validado = false;
        }
        if (et_calories.getText().toString().isEmpty()) {
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