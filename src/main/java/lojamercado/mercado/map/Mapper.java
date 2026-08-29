package lojamercado.mercado.map;

import org.springframework.stereotype.Component;

import lojamercado.mercado.dto.response.ClienteResponse;
import lojamercado.mercado.dto.response.ItemPedidoResponse;
import lojamercado.mercado.dto.response.PedidoResponse;
import lojamercado.mercado.dto.response.ProdutoResponse;
import lojamercado.mercado.entity.Cliente;
import lojamercado.mercado.entity.ItemPedido;
import lojamercado.mercado.entity.Pedido;
import lojamercado.mercado.entity.Produto;

@Component
public class Mapper {

    public ClienteResponse clienteToResponse(Cliente c) {
        ClienteResponse cResponse = new ClienteResponse();

        cResponse.setId(c.getId());
        cResponse.setEmail(c.getEmail());
        cResponse.setNome(c.getNome());

        return cResponse;
    }

    public ItemPedidoResponse itemPedidoToResponse(ItemPedido i) {
        ItemPedidoResponse itemPedidoResponse = new ItemPedidoResponse();
        itemPedidoResponse.setPrecoUnitario(i.getPrecoUnitario());
        itemPedidoResponse.setProduto(toResponse(i.getProduto()));
        itemPedidoResponse.setQuantidade(i.getQuantidade());

        return itemPedidoResponse;
    }

    public PedidoResponse pedidoToResponse(Pedido p) {
        PedidoResponse pResponse = new PedidoResponse();

        pResponse.setItens(p.getItens()
                .stream()
                .map(this::itemPedidoToResponse)
                .toList());

        pResponse.setData(p.getData());
        pResponse.setId(p.getId());
        pResponse.setStatus(p.getStatus());
        pResponse.setCliente(clienteToResponse(p.getCliente()));
        return pResponse;
    }

    public ProdutoResponse toResponse(Produto produto) {
        ProdutoResponse produtoResponse = new ProdutoResponse();
        produtoResponse.setCategoria(produto.getCategoria());
        produtoResponse.setPreco(produto.getPreco());
        produtoResponse.setEstoque(produto.getEstoque());
        produtoResponse.setNome(produto.getNome());
        produtoResponse.setId(produto.getId());
        return produtoResponse;
    }

}
