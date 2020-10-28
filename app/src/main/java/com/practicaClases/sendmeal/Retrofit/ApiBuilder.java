package com.practicaClases.sendmeal.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiBuilder {

    private static ApiBuilder INSTANCIA;
    private PlatoService platoService;

    private ApiBuilder(){
    }

    public void iniciarRetrofit(){
    Gson gson = new GsonBuilder().setLenient().create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/")
            // En la siguiente linea, le especificamos a Retrofit que tiene que usar Gson para deserializar nuestros objetos
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    PlatoService platoService = retrofit.create(PlatoService.class);
    }

    public PlatoService getPlatoRest() {
        if(platoService == null)
            this.iniciarRetrofit();
        return platoService;
    }


    public static ApiBuilder getInstance() {
        if(INSTANCIA == null){
            INSTANCIA = new ApiBuilder();
        }
        return INSTANCIA;
    }
}
