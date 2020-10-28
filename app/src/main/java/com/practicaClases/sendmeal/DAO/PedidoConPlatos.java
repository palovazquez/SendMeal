package com.practicaClases.sendmeal.DAO;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.practicaClases.sendmeal.model.Pedido;

import java.util.List;

public class PedidoConPlatos {
    @Embedded public Pedido pedido;

    @Relation(
            parentColumn = "id_pedido",
            entityColumn = "idPedido"
    )
    public List<Long> listaPlatos;

}


