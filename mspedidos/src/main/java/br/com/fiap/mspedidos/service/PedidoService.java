package br.com.fiap.mspedidos.service;

import br.com.fiap.mspedidos.model.ItemPedido;
import br.com.fiap.mspedidos.model.Pedido;
import br.com.fiap.mspedidos.repository.PedidoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@Log
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    private static final String URL_PRODUTO = "http://msprodutosapp:8080/api/produtos/{idProduto}";
    private static final String URL_PRODUTO_ESTOQUE = "http://msprodutosapp:8080/api/produtos/estoque/{idProduto}";

    private Map<String, Object> listaProdutos = new HashMap<>();

    public Pedido salvarPedido(Pedido pedido) {

        String isPedidoInvalido = validaPedido(pedido);
        if (isPedidoInvalido != null) {
            listaProdutos = new HashMap<>();
            throw new RuntimeException("Erro ao criar pedido: " + isPedidoInvalido);
        }
        atualizarValorPedido(pedido);
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        atualizarEstoque(pedidoSalvo);
        return pedidoSalvo;

    }

    private String validaPedido(final Pedido pedido) {

        if (pedido == null) {
            return "Pedido nulo";
        }

        if (pedido.getItens().isEmpty()) {
            return "Pedido sem itens";
        }

        for (ItemPedido item : pedido.getItens()) {
            try {
                JsonNode produtoJson = getProdutoInfo(item.getIdProduto());
                int quantidadeEstoque = produtoJson.get("quantidadeEstoque").asInt();
                if (quantidadeEstoque < item.getQuantidade()) {
                    return "Produto sem estoque";
                }

                listaProdutos.put(item.getIdProduto(), produtoJson);

            } catch (Exception e) {
                log.severe("Erro ao buscar produto " + item.getIdProduto()+ " - " + e.getMessage());
                return "Erro ao buscar produto";
            }
        }
        return null;
    }

    private JsonNode getProdutoInfo(final String id) throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.getForEntity(
                URL_PRODUTO,
                String.class,
                id);
        if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
            log.severe("Erro ao buscar produto " + id+ " - " + response.getBody());
            throw new RuntimeException("Produto não encontrado");
        }
        return objectMapper.readTree(response.getBody());
    }

    private void atualizarValorPedido(final Pedido pedido) {

        for (ItemPedido item : pedido.getItens()) {
            JsonNode produtoJson = (JsonNode) listaProdutos.get(item.getIdProduto());
            try {
                double preco = produtoJson.get("preco").asDouble();
                double valorTotal = pedido.getValorTotal();
                valorTotal += preco * item.getQuantidade();
                pedido.setValorTotal(valorTotal);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao buscar produto");
            }
        }
    }

    private void atualizarEstoque(final Pedido pedido) {
        try {
            for(ItemPedido item : pedido.getItens()) {
                Map<String, Long> produtoJson = new HashMap<>();
                produtoJson.put("quantidadeEstoque", item.getQuantidade());
                restTemplate.put(URL_PRODUTO_ESTOQUE, produtoJson, item.getIdProduto());
            }
        } catch (Exception e) {
            log.severe("Erro ao atualizar estoque " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar estoque");
        }
    }
}
