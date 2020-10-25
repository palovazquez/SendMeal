package com.practicaClases.sendmeal.DAO;

import android.os.AsyncTask;

import com.practicaClases.sendmeal.model.Plato;

import java.util.List;

public class BuscarPlatos extends AsyncTask<String, Void, List<Plato>> {

    private PlatoDao dao;
    private OnPlatoResultCallback callback;

    public BuscarPlatos(PlatoDao dao, OnPlatoResultCallback context) {
        this.dao = dao;
        this.callback = context;
        //Contexto desde donde estamos llamando a nuestro Repository
        //el cual implementa la interfaz OnPlatoResultCallback

    }

    @Override
    protected List<Plato> doInBackground(String... strings) {
        List<Plato> platos = dao.buscarTodos();
        return platos;
    }

    @Override
    protected void onPostExecute(List<Plato> platos) {
        super.onPostExecute(platos);
        callback.onResult(platos);
    }
}