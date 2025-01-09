package br.com.fiap.msproduto.controller;

import br.com.fiap.msproduto.dto.AtualizarEstoqueDTO;
import br.com.fiap.msproduto.model.Produto;
import br.com.fiap.msproduto.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        return ResponseEntity.ok(produtoService.listarProdutos());
    }

    @PostMapping
    public Produto salvarProduto(@RequestBody Produto produto) {
        return produtoService.salvarProduto(produto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarProduto(@PathVariable Long id) {
        Produto produto = produtoService.buscarProduto(id);
        return produto != null ? ResponseEntity.ok(produto) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody Produto produto) {
        Produto produtoAtualizado = produtoService.atualizarProduto(id, produto);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/estoque/{id}")
    public ResponseEntity<Produto> atualizarProdutoEstoque(@PathVariable Long id, @RequestBody AtualizarEstoqueDTO valorEstoque) {
        Produto produtoAtualizado = produtoService.atualizarEstoqueProduto(id, valorEstoque.quantidadeEstoque());
        return ResponseEntity.ok(produtoAtualizado);
    }
}
