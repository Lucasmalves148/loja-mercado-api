package lojamercado.mercado.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lojamercado.mercado.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    
}