package br.com.fiap.mspedidos.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemPedido {

    private String idProduto;
    private Long quantidade;
}
