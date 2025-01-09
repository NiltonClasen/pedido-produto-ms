package br.com.fiap.mspedidos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@Document("pedido")
public class Pedido {

    @Id
    private String id;
    private String nomeCliente;
    private List<ItemPedido> itens;
    private double valorTotal;
}
