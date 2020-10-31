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

    public void CrearPlato(String titulo, String descripcion, Double precio, Integer calorías){
        Plato plato = new Plato(titulo, descripcion, precio, calorías);
        Call<Plato> callPlatos = platoService.createPlato(plato);

        callPlatos.enqueue(
                new Callback<Plato>() {
                    @Override
                    public void onResponse(Call<Plato> call, Response<Plato> response) {
                        if (response.code() == 200) {
                            Log.d("DEBUG", "Returno Exitoso");
                        }
                    }

                    @Override
                    public void onFailure(Call<Plato> call, Throwable t) {
                        Log.d("DEBUG", "Returno Fallido");
                    }
                }
        );
    }

    public void BuscarPlato(Long id){
        Call<Plato> callPlatos = platoService.getPlato(id.toString());

        callPlatos.enqueue(
                new Callback<Plato>() {
                    @Override
                    public void onResponse(Call<Plato> call, Response<Plato> response) {
                        if (response.code() == 200) {
                            Log.d("DEBUG", "Returno Exitoso");
                        }
                    }

                    @Override
                    public void onFailure(Call<Plato> call, Throwable t) {
                        Log.d("DEBUG", "Returno Fallido");
                    }
                }
        );

    }

}