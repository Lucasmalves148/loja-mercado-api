package lojamercado.mercado.service;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lojamercado.mercado.dto.request.PedidoRequest;
import lojamercado.mercado.dto.response.PedidoResponse;
import lojamercado.mercado.entity.Cliente;
import lojamercado.mercado.entity.ItemPedido;
import lojamercado.mercado.entity.Pedido;
import lojamercado.mercado.entity.Produto;
import lojamercado.mercado.enumerate.Status;
import lojamercado.mercado.exceptions.ClienteNotFoundException;
import lojamercado.mercado.exceptions.EstoqueInsuficienteException;
import lojamercado.mercado.exceptions.PedidoNotFoundException;
import lojamercado.mercado.exceptions.ProdutoNotFoundException;
import lojamercado.mercado.exceptions.StatusException;
import lojamercado.mercado.map.Mapper;
import lojamercado.mercado.repository.ClienteRepository;
import lojamercado.mercado.repository.PedidoRepository;
import lojamercado.mercado.repository.ProdutoRepository;

@Service
public class PedidoService {

    private final ProdutoRepository produtoRepository;
    private final PedidoRepository pedidoRepository;
    private final Mapper mapper;
    private final ClienteRepository clienteRepository;

    public PedidoService(ClienteRepository clienteRepository, PedidoRepository pedidoRepository, Mapper mapper,
            ProdutoRepository produtoRepository) {
        this.mapper = mapper;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional
    public PedidoResponse criarPedido(PedidoRequest request) {
        Pedido p = new Pedido();

        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ClienteNotFoundException(("Cliente não encontrado")));

        List<ItemPedido> itens = request.getItens()
                .stream()
                .map(itemPedidoRequest -> {

                    Produto produto = produtoRepository.findById(
                            itemPedidoRequest.getIdProduto())
                            .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado"));

                    ItemPedido itemPedido = new ItemPedido();

                    if (produto.getEstoque() < itemPedidoRequest.getQuantidade()) {
                        throw new EstoqueInsuficienteException(
                                "Estoque insuficiente para o produto: " + produto.getNome());

                    }

                    produto.setEstoque(produto.getEstoque() - itemPedidoRequest.getQuantidade());
                    itemPedido.setProduto(produto);
                    itemPedido.setQuantidade(itemPedidoRequest.getQuantidade());
                    itemPedido.setPrecoUnitario(produto.getPreco());

                    produtoRepository.save(produto);
                    return itemPedido;
                })
                .toList();

        p.setCliente(cliente);
        p.setItens(itens);
        p.setData(request.getData());
        p.setStatus(Status.PENDENTE);

        pedidoRepository.save(p);

        return mapper.pedidoToResponse(p);
    }

    public void deletarPedido(Long id) {
        pedidoRepository.deleteById(id);
    }

    public PedidoResponse encontrarPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .map(mapper::pedidoToResponse)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido não encontrado"));
    }

    public List<PedidoResponse> listarTodosPedidos() {
        return pedidoRepository.findAll()
                .stream()
                .map(mapper::pedidoToResponse)
                .toList();
    }

    public List<PedidoResponse> exibirPedidosPorStatus(int cod) {
        return pedidoRepository.findAll()
                .stream()
                .filter(p -> p.getStatus().getCodigo() == cod)
                .map(mapper::pedidoToResponse)
                .toList();
    }

    public Long totalDePedidos() {
        return pedidoRepository.count();
    }

    public BigDecimal calcularTotalPedido(Long id) {
        Pedido p = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido não encontrado"));

        return p.getItens()
                .stream()
                .map(item -> item.getPrecoUnitario()
                        .multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public PedidoResponse atualizarStatus(Long id, Status status) {
        Pedido p = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido não encontrado"));

        if (p.getStatus() == status) {
            throw new StatusException("Não é possível alterar para o mesmo status");
        }

        if (p.getStatus() == Status.ENTREGUE) {
            throw new StatusException("Pedidos entregues não podem ser alterados");
        }

        p.setStatus(status);
        pedidoRepository.save(p);
        return mapper.pedidoToResponse(p);
    }

    public List<PedidoResponse> buscarPedidosCliente(Long clienteId) {
        return pedidoRepository.findAll()
                .stream()
                .filter(p -> p.getCliente().getId().equals(clienteId))
                .map(mapper::pedidoToResponse)
                .toList();
    }
}