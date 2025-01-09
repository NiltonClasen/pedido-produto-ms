package br.com.fiap.msproduto.service;

import br.com.fiap.msproduto.model.Produto;
import br.com.fiap.msproduto.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public Produto salvarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto buscarProduto(Long id) {
        return produtoRepository.findById(id).orElse(null);
    }

    public Produto atualizarProduto(Long id, Produto produto) {
        Produto produtoAtualizado = produtoRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Produto não encontrado com o id: " + id));

        produtoAtualizado.setNome(produto.getNome());
        produtoAtualizado.setDescricao(produto.getDescricao());
        produtoAtualizado.setPreco(produto.getPreco());
        produtoAtualizado.setQuantidadeEstoque(produto.getQuantidadeEstoque());
        return produtoRepository.save(produtoAtualizado);
    }

    public Produto atualizarEstoqueProduto(Long idProduto, Long quantidadeItens) {
        Produto produto = buscarProduto(idProduto);
        if (produto == null) {
            throw new NoSuchElementException("Produto não encontrado com o id: " + idProduto);
        }
        if (quantidadeItens < 0) {
            throw new IllegalArgumentException("Quantidade de estoque não pode ser negativa");
        }

        if (quantidadeItens > produto.getQuantidadeEstoque()) {
            throw new IllegalArgumentException("Quantidade de estoque não pode ser maior que a quantidade atual");
        }
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidadeItens);
        return produtoRepository.save(produto);
    }

    public void deletarProduto(Long id) {
        produtoRepository.deleteById(id);
    }
}
