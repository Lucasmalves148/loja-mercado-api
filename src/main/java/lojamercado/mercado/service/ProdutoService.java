package lojamercado.mercado.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import lojamercado.mercado.dto.request.ProdutoRequest;
import lojamercado.mercado.dto.response.ProdutoResponse;
import lojamercado.mercado.entity.Produto;
import lojamercado.mercado.exceptions.ProdutoNotFoundException;
import lojamercado.mercado.map.Mapper;
import lojamercado.mercado.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final Mapper mapper;

    public ProdutoService(ProdutoRepository produtoRepository, Mapper mapper) {
        this.mapper = mapper;
        this.produtoRepository = produtoRepository;
    }

    public ProdutoResponse criarProduto(ProdutoRequest pRequest) {
        Produto p = new Produto();

        p.setCategoria(pRequest.getCategoria());
        p.setEstoque(pRequest.getEstoque());
        p.setNome(pRequest.getNome());
        p.setPreco(pRequest.getPreco());

        p = produtoRepository.save(p);
        return mapper.toResponse(p);
    }

    public ProdutoResponse exibirProdutoPorId(Long id) {
        return produtoRepository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado."));
    }

    public void deletarProdutoPorId(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ProdutoNotFoundException("Produto não encontado");
        }

        produtoRepository.deleteById(id);
    }

    public List<ProdutoResponse> exibirProdutoPorNome(String nomeProduto) {
        return produtoRepository.findByNomeContainingIgnoreCase(nomeProduto)
                .stream()
                .toList();
    }

    public List<ProdutoResponse> exibirTodosProdutos() {
        return produtoRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public List<ProdutoResponse> exibirProdutosSemEstoque() {

        return produtoRepository.findByEstoque(0)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public BigDecimal alterarPrecoProduto(Long id, BigDecimal preco) {
        Produto p = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado"));

        p.setPreco(preco);
        produtoRepository.save(p);
        return p.getPreco();
    }

    public Integer alterarQuantidade(Long id, Integer estoque) {
        Produto p = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado"));
        p.setEstoque(estoque);

        produtoRepository.save(p);
        return p.getEstoque();
    }
}