package lojamercado.mercado.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import lojamercado.mercado.dto.request.ProdutoRequest;
import lojamercado.mercado.dto.response.ProdutoResponse;
import lojamercado.mercado.entity.Produto;
import lojamercado.mercado.enumerate.Categoria;
import lojamercado.mercado.exceptions.ProdutoNotFoundException;
import lojamercado.mercado.map.Mapper;
import lojamercado.mercado.repository.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private Mapper mapper;

    @Test
    @DisplayName("Deve criar um produto com sucesso")
    public void deveCriarProdutoComSucesso() {
        ProdutoRequest request = new ProdutoRequest("Notebook", new BigDecimal("3500.00"), 10, Categoria.ELETRONICO);

        Produto produtoSalvo = new Produto(1L, "Notebook", new BigDecimal("3500.00"), 10, Categoria.ELETRONICO);
        ProdutoResponse responseEsperada = new ProdutoResponse(1L, "Notebook", new BigDecimal("3500.00"), 10, Categoria.ELETRONICO);

        Mockito.when(produtoRepository.save(Mockito.any(Produto.class))).thenReturn(produtoSalvo);
        Mockito.when(mapper.toResponse(Mockito.any(Produto.class))).thenReturn(responseEsperada);

        ProdutoResponse response = produtoService.criarProduto(request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("Notebook", response.getNome());
        Assertions.assertEquals(new BigDecimal("3500.00"), response.getPreco());
        Mockito.verify(produtoRepository).save(Mockito.any(Produto.class));
    }

    @Test
    @DisplayName("Deve retornar um produto quando buscar por ID existente")
    public void deveBuscarProdutoPorIdComSucesso() {
        Produto produto = new Produto(1L, "Notebook", new BigDecimal("3500.00"), 10, Categoria.ELETRONICO);
        ProdutoResponse responseEsperada = new ProdutoResponse(1L, "Notebook", new BigDecimal("3500.00"), 10, Categoria.ELETRONICO);

        Mockito.when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        Mockito.when(mapper.toResponse(produto)).thenReturn(responseEsperada);

        ProdutoResponse response = produtoService.exibirProdutoPorId(1L);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1L, response.getId());
        Mockito.verify(produtoRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar produto com ID inexistente")
    public void deveLancarExcecaoAoBuscarProdutoInexistente() {
        Long idInexistente = 999L;

        Mockito.when(produtoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        Assertions.assertThrows(ProdutoNotFoundException.class,
                () -> produtoService.exibirProdutoPorId(idInexistente));
    }

    @Test
    @DisplayName("Deve retornar uma lista de produtos")
    public void deveListarTodosProdutos() {
        Produto p1 = new Produto(1L, "Notebook", new BigDecimal("3500.00"), 10, Categoria.ELETRONICO);
        Produto p2 = new Produto(2L, "Mouse", new BigDecimal("150.00"), 20, Categoria.ELETRONICO);

        ProdutoResponse r1 = new ProdutoResponse(1L, "Notebook", new BigDecimal("3500.00"), 10, Categoria.ELETRONICO);
        ProdutoResponse r2 = new ProdutoResponse(2L, "Mouse", new BigDecimal("150.00"), 20, Categoria.ELETRONICO);

        Mockito.when(produtoRepository.findAll()).thenReturn(List.of(p1, p2));
        Mockito.when(mapper.toResponse(p1)).thenReturn(r1);
        Mockito.when(mapper.toResponse(p2)).thenReturn(r2);

        List<ProdutoResponse> response = produtoService.exibirTodosProdutos();

        Assertions.assertEquals(2, response.size());
        Assertions.assertEquals("Notebook", response.get(0).getNome());
    }

    @Test
    @DisplayName("Deve deletar um produto existente")
    public void deveDeletarProdutoComSucesso() {
        Long id = 1L;

        Mockito.when(produtoRepository.existsById(id)).thenReturn(true);
        Mockito.doNothing().when(produtoRepository).deleteById(id);

        produtoService.deletarProdutoPorId(id);

        Mockito.verify(produtoRepository).deleteById(id);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar produto inexistente")
    public void deveLancarExcecaoAoDeletarProdutoInexistente() {
        Long idInexistente = 999L;

        Mockito.when(produtoRepository.existsById(idInexistente)).thenReturn(false);

        Assertions.assertThrows(ProdutoNotFoundException.class,
                () -> produtoService.deletarProdutoPorId(idInexistente));
    }

    @Test
    @DisplayName("Deve alterar o preço do produto com sucesso")
    public void deveAlterarPrecoProduto() {
        Long id = 1L;
        BigDecimal novoPreco = new BigDecimal("4000.00");
        Produto produto = new Produto(1L, "Notebook", new BigDecimal("3500.00"), 10, Categoria.ELETRONICO);

        Mockito.when(produtoRepository.findById(id)).thenReturn(Optional.of(produto));
        Mockito.when(produtoRepository.save(Mockito.any(Produto.class))).thenReturn(produto);

        BigDecimal precoAlterado = produtoService.alterarPrecoProduto(id, novoPreco);

        Assertions.assertEquals(novoPreco, precoAlterado);
        Mockito.verify(produtoRepository).save(produto);
    }
}