package com.practicaClases.sendmeal.Repository;

import android.app.Application;
import android.util.Log;

import com.practicaClases.sendmeal.DAO.AppDatabase;
import com.practicaClases.sendmeal.DAO.BuscarPlatos;
import com.practicaClases.sendmeal.DAO.BuscarPlatosById;
import com.practicaClases.sendmeal.DAO.OnPlatoResultCallback;
import com.practicaClases.sendmeal.DAO.PlatoDao;
import com.practicaClases.sendmeal.model.Plato;

import java.util.List;

public class AppRepository implements OnPlatoResultCallback {
    private PlatoDao platoDao;
    private OnResultCallback callback;

    public AppRepository(Application application, OnResultCallback context){
        AppDatabase db = AppDatabase.getInstance(application);
        platoDao = db.platoDao();
        callback = context;
    }

    public void insertar(final Plato plato){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                platoDao.insertar(plato);
            }
        });
    }

    public void borrar(final Plato plato){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                platoDao.borrar(plato);
            }
        });
    }

    public void actualizar(final Plato plato){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                platoDao.actualizar(plato);
            }
        });
    }

    public void buscar(String id) {
        new BuscarPlatosById(platoDao, this, id).execute();
    }

    public void buscarTodos() {
        new BuscarPlatos(platoDao, this).execute();
    }


    public void onResult(List<Plato> platos) {
        Log.d("DEBUG", "Plato found");
        callback.onResult(platos);
    }

    @Override
    public void onResult(Plato plato) {
        Log.d("DEBUG", "Plato found");
        callback.onResult(plato);
    }

    public interface OnResultCallback<T> {
        void onResult(List<T> result);
        void onResult(T result);
    }
    //Es el mismo tipo de interfaz que OnPlatoResultCallback pero mas genérica.

}