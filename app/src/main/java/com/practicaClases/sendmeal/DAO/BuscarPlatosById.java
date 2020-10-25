package com.practicaClases.sendmeal.DAO;

import android.os.AsyncTask;

import com.practicaClases.sendmeal.model.Plato;

import java.util.List;

public class BuscarPlatosById extends AsyncTask<String, Void, Plato> {

    private PlatoDao dao;
    private OnPlatoResultCallback callback;
    private String id;

    public BuscarPlatosById(PlatoDao dao, OnPlatoResultCallback context, String id) {
        this.dao = dao;
        this.callback = context;
        this.id = id;
        //Contexto desde donde estamos llamando a nuestro Repository
        //el cual implementa la interfaz OnPlatoResultCallback

    }

    @Override
    protected Plato doInBackground(String... strings) {
        Plato platos = dao.buscar(id);
        return platos;
    }

    @Override
    protected void onPostExecute(Plato platos) {
        super.onPostExecute(platos);
        callback.onResult(platos);
    }
}