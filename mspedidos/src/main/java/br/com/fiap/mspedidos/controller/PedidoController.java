package br.com.fiap.mspedidos.controller;

import br.com.fiap.mspedidos.model.Pedido;
import br.com.fiap.mspedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(@Lazy PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<Pedido> salvarPedido(@RequestBody Pedido pedido) {
        Pedido pedidoSalvo = pedidoService.salvarPedido(pedido);
        return ResponseEntity.ok(pedidoSalvo);
    }
}
