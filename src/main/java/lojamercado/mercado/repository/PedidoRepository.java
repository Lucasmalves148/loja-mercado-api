package lojamercado.mercado.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lojamercado.mercado.entity.Pedido;
import lojamercado.mercado.enumerate.Status;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
 
        List<Pedido> findByStatus(Status status);
        List<Pedido> findByClienteId(Long clienteId);

}