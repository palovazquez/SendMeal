package com.practicaClases.sendmeal.Retrofit;

import android.util.Log;

import com.practicaClases.sendmeal.model.Plato;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlatoRepositoryService {

    PlatoService platoService;

    public PlatoRepositoryService() {
        platoService = ApiBuilder.getInstance().getPlatoRest();
    }

    public void ListarPlatos(){
        Plato plato = new Plato();
        Call<List<Plato>> callPlatos = platoService.getPlatoList();

        callPlatos.enqueue(
                new Callback<List<Plato>>() {
                    @Override
                    public void onResponse(Call<List<Plato>> call, Response<List<Plato>> response) {
                        if (response.code() == 200) {
                            Log.d("DEBUG", "Returno Exitoso");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Plato>> call, Throwable t) {
                        Log.d("DEBUG", "Returno Fallido");
                    }
                }
        );

    }

    public void CrearPlato(){
        Plato plato = new Plato();
        Call<List<Plato>> callPlatos = platoService.getPlatoList();

        callPlatos.enqueue(
                new Callback<List<Plato>>() {
                    @Override
                    public void onResponse(Call<List<Plato>> call, Response<List<Plato>> response) {
                        if (response.code() == 200) {
                            Log.d("DEBUG", "Returno Exitoso");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Plato>> call, Throwable t) {
                        Log.d("DEBUG", "Returno Fallido");
                    }
                }
        );





    }



}


