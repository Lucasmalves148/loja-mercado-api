package lojamercado.mercado.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
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

import lojamercado.mercado.dto.request.ItemPedidoRequest;
import lojamercado.mercado.dto.request.PedidoRequest;
import lojamercado.mercado.dto.response.PedidoResponse;
import lojamercado.mercado.entity.Cliente;
import lojamercado.mercado.entity.ItemPedido;
import lojamercado.mercado.entity.Pedido;
import lojamercado.mercado.entity.Produto;
import lojamercado.mercado.enumerate.Categoria;
import lojamercado.mercado.enumerate.Status;
import lojamercado.mercado.exceptions.ClienteNotFoundException;
import lojamercado.mercado.exceptions.EstoqueInsuficienteException;
import lojamercado.mercado.exceptions.PedidoNotFoundException;
import lojamercado.mercado.map.Mapper;
import lojamercado.mercado.repository.ClienteRepository;
import lojamercado.mercado.repository.PedidoRepository;
import lojamercado.mercado.repository.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private Mapper mapper;

    @Test
    @DisplayName("Deve retornar somente um pedido")
    public void deveRetornarUmPedido() {
        Cliente c = new Cliente();
        List<ItemPedido> ip = List.of(new ItemPedido(), new ItemPedido());
        Pedido p = new Pedido(1L, c, LocalDate.now(), Status.PENDENTE, ip);

        PedidoResponse responseMock = new PedidoResponse();
        responseMock.setId(p.getId());
        Mockito.when(mapper.pedidoToResponse(Mockito.any(Pedido.class)))
                .thenReturn(responseMock);

        Mockito.when(pedidoRepository.findAll()).thenReturn(Collections.singletonList(p));
        List<PedidoResponse> pedidos = pedidoService.listarTodosPedidos();

        Assertions.assertEquals(1, pedidos.size());
        Assertions.assertEquals(1L, pedidos.get(0).getId());
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar pedido com id não encontrado")
    public void deveDeletarPedidoComIdNaoEncontrado() {
        Long idInexistente = 999L;
        Mockito.when(pedidoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        Assertions.assertThrows(PedidoNotFoundException.class,
                () -> pedidoService.deletarPedido(idInexistente));
    }

    @Test
    @DisplayName("Deve deletar o pedido com id encontrado")
    public void deveDeletarPedidoComIdEncontrado() {
        Cliente c = new Cliente();
        List<ItemPedido> ip = List.of(new ItemPedido(), new ItemPedido());
        Pedido p = new Pedido(1L, c, LocalDate.now(), Status.PENDENTE, ip);

        Mockito.when(pedidoRepository.findById(p.getId())).thenReturn(Optional.of(p));
        Mockito.doNothing().when(pedidoRepository).delete(Mockito.any(Pedido.class));

        pedidoService.deletarPedido(p.getId());
        Mockito.verify(pedidoRepository).delete(Mockito.any(Pedido.class));
    }

    @Test
    @DisplayName("Deve lançar exceção se pedido for criado com cliente inexistente")
    public void deveLancarExcecaoSePedidoNaoHouverCliente() {
        Long idClienteInexistente = 999L;
        List<ItemPedidoRequest> itens = List.of(new ItemPedidoRequest(1L, 1));
        PedidoRequest request = new PedidoRequest(idClienteInexistente, LocalDate.now(), itens);

        Mockito.when(clienteRepository.findById(idClienteInexistente))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ClienteNotFoundException.class,
                () -> pedidoService.criarPedido(request));
    }

    @Test
    @DisplayName("Deve lançar exceção se estoque for insuficiente")
    public void deveLancarExcecaoSeEstoqueInsuficiente() {
        Cliente cliente = new Cliente(1L, "Lucas", "lucas@email.com");

        Produto produto = new Produto(1L, "Notebook", new BigDecimal("3500.00"), 5, Categoria.ELETRONICO);

        List<ItemPedidoRequest> itens = List.of(new ItemPedidoRequest(produto.getId(), 10));
        PedidoRequest request = new PedidoRequest(cliente.getId(), LocalDate.now(), itens);

        Mockito.when(clienteRepository.findById(cliente.getId()))
                .thenReturn(Optional.of(cliente));

        Mockito.when(produtoRepository.findById(produto.getId()))
                .thenReturn(Optional.of(produto));

        Assertions.assertThrows(EstoqueInsuficienteException.class,
                () -> pedidoService.criarPedido(request));
    }

    @Test
    @DisplayName("Deve criar pedido com sucesso")
    public void deveCriarPedidoComSucesso() {
        Cliente cliente = new Cliente(1L, "Lucas", "lucas@email.com");
        Produto produto = new Produto(1L, "Notebook", new BigDecimal("3500.00"), 5, Categoria.ELETRONICO);
        List<ItemPedidoRequest> itens = List.of(new ItemPedidoRequest(produto.getId(), 1));
        PedidoRequest request = new PedidoRequest(cliente.getId(), LocalDate.now(), itens);

        PedidoResponse responseMock = new PedidoResponse();
        responseMock.setId(1L);
        Mockito.when(mapper.pedidoToResponse(Mockito.any(Pedido.class)))
                .thenReturn(responseMock);

        Mockito.when(clienteRepository.findById(cliente.getId()))
                .thenReturn(Optional.of(cliente));

        Mockito.when(produtoRepository.findById(produto.getId()))
                .thenReturn(Optional.of(produto));

        Mockito.when(pedidoRepository.save(Mockito.any(Pedido.class)))
                .thenReturn(new Pedido());
        PedidoResponse response = pedidoService.criarPedido(request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1L, response.getId());
        Mockito.verify(pedidoRepository).save(Mockito.any(Pedido.class));
        Mockito.verify(produtoRepository).save(Mockito.any(Produto.class));
    }
}