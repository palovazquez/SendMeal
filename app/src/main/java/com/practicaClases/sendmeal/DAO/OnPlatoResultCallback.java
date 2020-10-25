package com.practicaClases.sendmeal.DAO;

import com.practicaClases.sendmeal.model.Plato;

import java.util.List;

public interface OnPlatoResultCallback {
    void onResult(List<Plato> plato);
    void onResult(Plato plato);
}
