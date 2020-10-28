package com.practicaClases.sendmeal.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiBuilder {
    Gson gson = new GsonBuilder().setLenient().create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("{urlApiRest}/")
            // En la siguiente linea, le especificamos a Retrofit que tiene que usar Gson para deserializar nuestros objetos
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    PlatoService platoService = retrofit.create(PlatoService.class);


}
