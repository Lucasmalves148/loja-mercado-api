package lojamercado.mercado.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lojamercado.mercado.dto.response.ProdutoResponse;
import lojamercado.mercado.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<ProdutoResponse> findByNomeContainingIgnoreCase(String nome);

    List<Produto> findByEstoque(int estoque);
}
