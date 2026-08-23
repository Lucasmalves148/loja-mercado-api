package lojamercado.mercado.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lojamercado.mercado.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto,Long>{

    
    
}
